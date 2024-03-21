# Polimorfizam tijekom konstruiranja objekata

Ispis danog programa je kako slijedi:
```
Metoda kaze: ja sam bazna implementacija!
Metoda kaze: ja sam izvedena implementacija!
Metoda kaze: ja sam izvedena implementacija!
```

Prvo se zbog operatora `new` i činjenice da se `Derived` izvodi iz `Base`, pozove konstruktor od `Base` sa odgovarajućim ispisom o baznoj implementaciji. Nakon toga se pozove metoda iz konstruktora `Derived` te se u kodu pozove `pd->metoda();` što uzrokuje dvostruku poruku o izvedenoj implementaciji.

Prvi se ispis desi tijekom konstruiranja objekta `Derived` na temelju konstruktora klase `Base`. Slično, drugi ispis se također desi tijekom konstruiranja objekta `Derived`, ali zbog njegovog konstruktora. Treći pak ispis se desi nakon završetka konstruiranja objekta `Derived` običnim pozivom funkcije.

## Analiza asemblerskog koda

Ovdje su navedeni isječci asemblerskog koda sa dodatnim komentarima koji su povezani sa prethodnim pojašnjenjem.

- Konstruktor `Base` klase:
```
_ZN4BaseC2Ev:
.LFB1:
	.cfi_startproc
	endbr64
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi                  ; spremi rdi na mjesto pokazivača na objekt tipa `Base`
	lea	rdx, _ZTV4Base[rip+16]                  ; lea = Load Effective Address, učitavanje adrese VTable od `Base` u rdx
	mov	rax, QWORD PTR -8[rbp]                  ; spremi pokazivač na `Base` u rax
	mov	QWORD PTR [rax], rdx                    ; spremi VTable od `Base` na početak od `Base`
	mov	rax, QWORD PTR -8[rbp]                  ; spremi pokazivač na `Base` u rax
	mov	rdi, rax                                ; spremi rax u rdi
	call	_ZN4Base6metodaEv                   ; poziv metode `void metoda()`
	nop                                         ; završne naredbe
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```

- Konstruktor `Derived` klase:
```
_ZN7DerivedC2Ev:
.LFB6:
	.cfi_startproc
	endbr64
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi                  ; metode za konstrukciju `Derived` klase poput gore opisanih za klasu `Base`
	mov	rax, QWORD PTR -8[rbp]
	mov	rdi, rax
	call	_ZN4BaseC2Ev
	lea	rdx, _ZTV7Derived[rip+16]
	mov	rax, QWORD PTR -8[rbp]
	mov	QWORD PTR [rax], rdx
	mov	rax, QWORD PTR -8[rbp]
	mov	rdi, rax
	call	_ZN4Base6metodaEv                   ; poziv metode `void metoda()` klase `Base`
	nop                                         ; završne naredbe
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```

- Metoda `void metoda()` klase `Base`:
```
_ZN4Base6metodaEv:
.LFB4:
	.cfi_startproc
	endbr64
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi
	lea	rdi, .LC1[rip]
	mov	eax, 0
	call	printf@PLT
	mov	rax, QWORD PTR -8[rbp]                  ; učitavanje adrese virtualne metode preko VTable klase `Base`
	mov	rax, QWORD PTR [rax]
	mov	rdx, QWORD PTR [rax]
	mov	rax, QWORD PTR -8[rbp]
	mov	rdi, rax
	call	rdx                                 ; poziv funkcije `virtual void virtualnaMetoda()` preko adrese
	nop                                         ; završne naredbe
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```

- Metoda `virtual void virtualnaMetoda()` klase `Base` (samo `printf`, ništa zanimljivo):
```
_ZN4Base15virtualnaMetodaEv:
.LFB3:
	.cfi_startproc
	endbr64
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi
	lea	rdi, .LC0[rip]
	call	puts@PLT
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```

- Metoda `virtual void virtualnaMetoda()` klase `Derived` (samo `printf`, ništa zanimljivo):
```
_ZN7Derived15virtualnaMetodaEv:
.LFB8:
	.cfi_startproc
	endbr64
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi
	lea	rdi, .LC2[rip]
	call	puts@PLT
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
```