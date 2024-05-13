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

void construct(Parrot *p, char const *name)
{
    p->name = name;
    p->vtable = parrotVtable;
}