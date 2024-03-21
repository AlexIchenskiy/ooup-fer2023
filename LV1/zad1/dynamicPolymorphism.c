#include <stdio.h>
#include <stdlib.h>

#include "dog.h"
#include "cat.h"

typedef char const *(*PTRFUN)();

typedef struct Animal
{
    char const *name;
    PTRFUN *pfun;
} Animal;

/* --------------- DOG --------------- */

PTRFUN dogFunctions[2] = {dogGreet, dogMenu};

void constructDog(Animal *animal, char const *name)
{
    animal->name = name;
    animal->pfun = dogFunctions;
}

Animal *createDog(char const *name)
{
    Animal *dog = (Animal *)malloc(sizeof(Animal));
    dog->name = name;
    dog->pfun = dogFunctions;
    return dog;
}

char const *dogGreet(void)
{
    return "vau!";
}

char const *dogMenu(void)
{
    return "kuhanu govedinu";
}

Animal *createNDogs(int n)
{
    Animal *dogs = (Animal *)malloc(n * sizeof(Animal));

    for (int i = 0; i < n; i++)
    {
        constructDog(&dogs[i], "Dog");
    }

    return dogs;
}

/* --------------- CAT --------------- */

PTRFUN catFunctions[2] = {catGreet, catMenu};

void constructCat(Animal *animal, char const *name)
{
    animal->name = name;
    animal->pfun = catFunctions;
}

Animal *createCat(char const *name)
{
    Animal *cat = (Animal *)malloc(sizeof(Animal));
    constructCat(cat, name);
    return cat;
}

char const *catGreet(void)
{
    return "mijau!";
}

char const *catMenu(void)
{
    return "konzerviranu tunjevinu";
}

Animal *createNCats(int n)
{
    Animal *cats = (Animal *)malloc(n * sizeof(Animal));

    for (int i = 0; i < n; i++)
    {
        constructCat(&cats[i], "Original cat");
    }

    return cats;
}

/* ------------- GENERAL ------------- */

void animalPrintGreeting(Animal *animal)
{
    printf("%s pozdravlja: %s\n", animal->name, animal->pfun[0]());
}

void animalPrintMenu(Animal *animal)
{
    printf("%s voli: %s\n", animal->name, animal->pfun[1]());
}

/* -------------- OTHER -------------- */

void testAnimals(void)
{
    struct Animal *p1 = createDog("Hamlet");
    struct Animal *p2 = createCat("Ofelija");
    struct Animal *p3 = createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1);
    free(p2);
    free(p3);
}

int main()
{
    /* testAnimals uses createDog/createCat which uses malloc -> heap allocation */
    testAnimals();

    printf("\n");

    /* local variable -> stack allocation */

    Animal cat;
    constructCat(&cat, "Tymofiy");

    animalPrintGreeting(&cat);
    animalPrintMenu(&cat);

    printf("\n");

    /* test n animals */

    int numberOfAnimals = 3;

    Animal *cats = createNCats(numberOfAnimals);

    for (int i = 0; i < numberOfAnimals; i += 1)
    {
        printf("%d. cat: \n - %s \n - ",
               i + 1, cats[i].name);
        animalPrintGreeting(&cats[i]);
    }

    return 0;
}