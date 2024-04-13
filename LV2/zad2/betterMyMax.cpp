#include <iostream>
#include <string.h>
#include <vector>
#include <set>

using namespace std;

template <typename Iterator, typename Predicate>
Iterator mymax(
    Iterator first, Iterator last, Predicate pred)
{
    Iterator max = first;

    while (first != last)
    {
        if (pred(*first, *max) == 1)
        {
            max = first;
        }
        first++;
    }

    return max;
}

int gt_int(const int first, const int second)
{
    return (first > second) ? 1 : 0;
}

int gt_char(const char first, const char second)
{
    return (first > second) ? 1 : 0;
}

int gt_str(const char *first, const char *second)
{
    return strcmp(first, second) > 0 ? 1 : 0;
}

int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
char arr_char[] = "Suncana strana ulice";
const char *arr_str[] = {
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"};

vector<int> intvec{2, 4, 6, 8, 5, 7, 10, 3, 1};
vector<char> charvec{'V', 'u', 'k', 'o', 'v', 'i', ' ', 'u', ' ', 's', 'u', 'm', 'i'};
vector<const char *> strvec{"Hello", "world", "!!"};

set<int> intset{3, 5, 7, 9, 6, 8, 11, 4, 2};
set<char> charset{'n', 'o', 't', ' ', 'U', 'T', 'F', '8'};
set<const char *> strset{"Nema", "lijepe", "funkcije", "za", "ispis", ":("};

int main()
{
    const int *maxint = mymax(&arr_int[0],
                              &arr_int[sizeof(arr_int) / sizeof(*arr_int)], gt_int);
    const char *maxchar = mymax(&arr_char[0], &arr_char[sizeof(arr_char) / sizeof(*arr_char)], gt_char);
    const char **maxstr = mymax(&arr_str[0], &arr_str[sizeof(arr_str) / sizeof(*arr_str)], gt_str);

    auto maxintvec = mymax(intvec.begin(), intvec.end(), gt_int);
    auto maxcharvec = mymax(charvec.begin(), charvec.end(), gt_char);
    auto maxstrvec = mymax(strvec.begin(), strvec.end(), gt_str);

    auto maxintset = mymax(intset.begin(), intset.end(), gt_int);
    auto maxcharset = mymax(charset.begin(), charset.end(), gt_char);
    auto maxstrset = mymax(strset.begin(), strset.end(), gt_str);

    printf("Lists: \n\n");

    printf("Max of int list [ ");
    for (int i = 0; i < 9; i++)
    {
        printf("%d ", arr_int[i]);
    }
    printf("] is:\n %d\n", *maxint);

    printf("Max of char list [ ");
    for (int i = 0; i < 20; i++)
    {
        printf("%c ", arr_char[i]);
    }
    printf("] is:\n %c\n", *maxchar);

    printf("Max of str list [ ");
    for (int i = 0; i < 11; i++)
    {
        printf("%s ", arr_str[i]);
    }
    printf("] is:\n %s\n", *maxstr);

    printf("\nVectors: \n\n");

    printf("Max of int vector [ ");
    for (int i = 0; i < intvec.size(); i++)
    {
        printf("%d ", intvec.at(i));
    }
    printf("] is:\n %d\n", *maxintvec);

    printf("Max of char vector [ ");
    for (int i = 0; i < charvec.size(); i++)
    {
        printf("%c ", charvec.at(i));
    }
    printf("] is:\n %c\n", *maxcharvec);

    printf("Max of str vector [ ");
    for (int i = 0; i < strvec.size(); i++)
    {
        printf("%s ", strvec.at(i));
    }
    printf("] is:\n %s\n", *maxstrvec);

    printf("\nSets: \n\n");

    printf("Max of int set [ ");
    for (set<int>::iterator it = intset.begin(); it != intset.end(); it++)
    {
        printf("%d ", *it);
    }
    printf("] is:\n %d\n", *maxintset);

    printf("Max of char vector [ ");
    for (set<char>::iterator it = charset.begin(); it != charset.end(); it++)
    {
        printf("%c ", *it);
    }
    printf("] is:\n %c\n", *maxcharset);

    printf("Max of str vector [ ");
    for (set<const char *>::iterator it = strset.begin(); it != strset.end(); it++)
    {
        printf("%s ", *it);
    }
    printf("] is:\n %s\n", *maxstrset);

    return 0;
}