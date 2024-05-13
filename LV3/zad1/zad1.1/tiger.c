#include <stdlib.h>

typedef char const *(*PTRFUN)();

typedef struct Tiger
{
    PTRFUN *vtable;
    char const *name;
} Tiger;

char const *tigerName(Tiger *t)
{
    return t->name;
}

char const *tigerGreet(void)
{
    return "Mijau!";
}

char const *tigerMenu(void)
{
    return "mlako mlijeko";
}

PTRFUN tigerVtable[3] = {tigerName, tigerGreet, tigerMenu};

void *create(char const *name)
{
    Tiger *t = (Tiger *)malloc(sizeof(Tiger));
    t->name = name;
    t->vtable = tigerVtable;
    return t;
}

void construct(Tiger *t, char const *name)
{
    t->name = name;
    t->vtable = tigerVtable;
}