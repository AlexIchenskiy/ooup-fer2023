#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>
#include <string.h>
#include <stdbool.h>

typedef void *(*PTRFUN)(char const *);

void *myfactory(char const *libname, char const *ctorarg, bool onStack)
{
    char *full_libname = (char *)malloc(strlen(libname) + 5);
    if (!full_libname)
    {
        return NULL;
    }
    strcpy(full_libname, "./");
    strcat(full_libname, libname);
    strcat(full_libname, ".so");

    void *handle = dlopen(full_libname, RTLD_LAZY);
    PTRFUN pfun = (PTRFUN)dlsym(handle, onStack ? "construct" : "create");

    if (!pfun)
    {
        return NULL;
    }

    free(full_libname);
    return pfun(ctorarg);
}