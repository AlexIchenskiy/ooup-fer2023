#include <stdlib.h>
#include <stdio.h>
#include <string.h>

const void *mymax(
    const void *base, size_t nmemb, size_t size,
    int (*compar)(const void *, const void *))
{
    const unsigned long *max = base;

    for (size_t i = 0, offset = size; i < nmemb - 1; i++, offset += size)
    {
        const unsigned long *temp = base + offset;

        if (compar(temp, max) == 1)
        {
            max = temp;
        }
    }

    return max;
}

int gt_int(const void *first, const void *second)
{
    return (*(int *)first > *(int *)second) ? 1 : 0;
}

int gt_char(const void *first, const void *second)
{
    return (*(char *)first > *(char *)second) ? 1 : 0;
}

int gt_str(const void *first, const void *second)
{
    return strcmp(*(const char **)first, *(const char **)second) > 0 ? 1 : 0;
}

int main()
{
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    char arr_char[] = "Suncana strana ulice";
    const char *arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};

    printf("Max of int list [ ");
    for (int i = 0; i < 9; i++)
    {
        printf("%d ", arr_int[i]);
    }
    printf("] is:\n %d\n", *(int *)mymax(arr_int, 9, sizeof(int), gt_int));

    printf("Max of char list [ ");
    for (int i = 0; i < 20; i++)
    {
        printf("%c ", arr_char[i]);
    }
    printf("] is:\n %c\n", *(const char *)mymax(arr_char, 20, sizeof(char), gt_char));

    printf("Max of str list [ ");
    for (int i = 0; i < 11; i++)
    {
        printf("%s ", arr_str[i]);
    }
    printf("] is:\n %s\n", *(char **)mymax(arr_str, 11, sizeof(char *), gt_str));

    return 0;
}