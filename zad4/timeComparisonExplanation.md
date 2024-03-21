1. U priloženom asemblerskom kodu (`timeComparison.s`, dobije se naredbom `g++ -O0 -S -masm=intel timeComparison.cpp,`) alociranje memorije za `poc` se ne navodi eksplicitno što znači da se taj objekt nalazi na stogu (stack).
S druge strane, alociranje memorije za `*pb` se nalazi na liniji 151:
```
call	_Znwm@PLT                               ; _Znwm = new, PLT = procedure linkage table, struktura za dinamičko povezivanje -> zauzimanje memorije na heap-u
```

2. Razlika između alokacije `poc` i `*pb` se temelji na tome da se `poc` alocira na stack-u dok se za `*pb` zauzima memorija na heap-u

3. Konstruktor za `poc` ne postoji jer se memorija dodjeljuje odmah na stack-u 

4. Pozivanje konstruktora za `*pb` nalazi na liniji 154: `call	_ZN9CoolClassC1Ev`, a ta metoda (linije 106-127) izgleda ovako:
```
_ZN9CoolClassC2Ev:                              ; naziv se razlikuje zbog .set na 131. liniji
.LFB9:                                          ; oznaka koja označava početak funkcije
	.cfi_startproc                              ; početak procedure
	endbr64                                     ; END Branch 64 - sigurnost
	push	rbp                                 ; spremanje registra rbp na stog
	.cfi_def_cfa_offset 16                      ; CFA (Canonic Frame Address) = referenca na dno stoga + offset 16
	.cfi_offset 6, -16                          ; offset za rbp na stogu = -16
	mov	rbp, rsp                                ; spremi rsp u rbp
	.cfi_def_cfa_register 6                     ; definira da je rbp primarni za adresiranje stoga
	sub	rsp, 16                                 ; rezervira 16 bajtova za lokalne varijable funkcije
	mov	QWORD PTR -8[rbp], rdi                  ; spremi rdi na mjesto pokazivača na objekt tipa `CoolClass`
	mov	rax, QWORD PTR -8[rbp]                  ; spremi adresu objekta tipa `CoolClass` u rax
	mov	rdi, rax                                ; spremi rax u rdi
	call	_ZN4BaseC2Ev                        ; poziv konstruktora `Base` roditeljske klase
	lea	rdx, _ZTV9CoolClass[rip+16]             ; spremi pokazivač na VTable od `CoolClass` u rdx
	mov	rax, QWORD PTR -8[rbp]                  ; spremi pokazivač na `CoolClass` u rax
	mov	QWORD PTR [rax], rdx                    ; spremi pokazivač na VTable od `CoolClase` iz rdx na početak klase `CoolClass` u rax
	nop                                         ; završne naredbe
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```

5. Prevoditelj je izveo poziv `poc.set(42)` na linijama 157-159:
```
    mov	esi, 42                                 ; spremi 42 u esi
	mov	rdi, rax
	call	_ZN13PlainOldClass3setEi            ; poziv metode `set(int x)` od `PlainOldClass`
```
dok se poziv `pb->set(42)` izveo na linijama 160-166:
```
    mov	rax, QWORD PTR -32[rbp]                 ; spremi adresu na `CoolClass` u rax
	mov	rax, QWORD PTR [rax]                    ; spremi adresu na početak rax (VTable od `CoolClass`) u rax
	mov	rdx, QWORD PTR [rax]                    ; spremi adresu na početak rax (metoda set(int x) od `CoolClass`) u rdx
	mov	rax, QWORD PTR -32[rbp]                 ; ponovno spremi adresu na `CoolClass` u rax
	mov	esi, 42                                 ; spremi 42 u esi
	mov	rdi, rax                                ; spremi adresu na `CoolClass` u rdi
	call	rdx                                 ; pozovi metodu set(int x) od `CoolClass` preko adrese
```

Poziv `poc.set(42)` ima manje instrukcija te koristi izravan poziv metode set, dok `pb->set(42)` zbog polimorfnosti koristi poziv funkcije preko adrese te funkcije za što je potrebno više instrukcija kako bi se ta adresa dobila. Očigledno, inline poziv funkcije bi se mogao umetnuti u poziv `poc.set(42)` zbog izravnog poziva te metode pomoću naredbe `CALL`.

6. Kod za definiciju tablice virtualnih funkcija razreda `CoolClass` se nalazi na linijama 186-195:
```
_ZTV9CoolClass:
	.quad	0                                   ; base-class pointer
	.quad	_ZTI9CoolClass                      ; typeinfo (pokazivač na tip informacija) za CoolClass
	.quad	_ZN9CoolClass3setEi                 ; pokazivač na virtualnu funkciju set(int x)
	.quad	_ZN9CoolClass3getEv                 ; pokazivač na virtualnu funkciju get()
	.weak	_ZTV4Base
	.section	.data.rel.ro._ZTV4Base,"awG",@progbits,_ZTV4Base,comdat
	.align 8                                    ; nadopunjavanje/poravnavanje na 8 bajtova
	.type	_ZTV4Base, @object
	.size	_ZTV4Base, 32                       ; veličina tablice virtualnih funkcija = 32 bajta
```
dok se inicijalizacija nalazi na linijama 120-122:
```
    lea	rdx, _ZTV9CoolClass[rip+16]             ; lea = load effective address (mov, ali za adrese) -> pokazivač na VTable od CoolClass spremi u rdx
	mov	rax, QWORD PTR -8[rbp]                  ; pokazivač (PTR, QWORD = quad-word, 8 bajtova, jedna riječ je 2 bajta) na objekt spremi u rdx 
	mov	QWORD PTR [rax], rdx                    ; spremi pokazivač na VTable (iz rdx) na adresu gdje se nalazi objekt (rax) 
```