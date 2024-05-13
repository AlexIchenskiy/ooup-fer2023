#include <stdlib.h>

typedef char const *(*PTRFUN)();

typedef struct Parrot
{
    PTRFUN *vtable;
    char const *name;
} Parrot;

char const *parrotName(Parrot *p)
{
    return p->name;
}

char const *parrotGreet(void)
{
    return "Sto mu gromova!";
}

char const *parrotMenu(void)
{
    return "brazilske orahe";
}

PTRFUN parrotVtable[3] = {parrotName, parrotGreet, parrotMenu};

void *create(char const *name)
{
    Parrot *p = (Parrot *)malloc(sizeof(Parrot));
    p->name = name;
    p->vtable = parrotVtable;
    return p;
}

void *construct(char const *name)
{
    Parrot *p = (Parrot *)alloca(sizeof(Parrot));
    p->name = name;
    p->vtable = parrotVtable;
    return p;
}

size_t sizeOf(Parrot p)
{
    return sizeof(Parrot);
}