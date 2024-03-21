#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Unary_Function Unary_Function;

typedef double (*PTRFUN)(struct Unary_Function *, double);

/* --------------- UNARY FUNCTION --------------- */

struct Unary_Function
{
    PTRFUN *pfun;
    int _lower_bound;
    int _upper_bound;
};

void tabulate(Unary_Function *fun)
{
    for (int x = fun->_lower_bound; x <= fun->_upper_bound; x++)
    {
        printf("f(%d)=%lf\n", x, fun->pfun[0](fun, x));
    }
}

double value_at(Unary_Function *fun, double x)
{
    return 0;
}

double negative_value_at(Unary_Function *fun, double x)
{
    return -fun->pfun[0](fun, x);
}

void *createUnaryFunction(Unary_Function *unary_function, PTRFUN *pfun, int lower_bound, int upper_bound)
{
    unary_function->pfun = pfun;
    unary_function->_lower_bound = lower_bound;
    unary_function->_upper_bound = upper_bound;
}

bool same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance)
{
    if (f1->_lower_bound != f2->_lower_bound)
        return false;
    if (f1->_upper_bound != f2->_upper_bound)
        return false;
    for (int x = f1->_lower_bound; x <= f1->_upper_bound; x++)
    {
        double delta = f1->pfun[0](f1, x) - f2->pfun[0](f2, x);
        if (delta < 0)
            delta = -delta;
        if (delta > tolerance)
            return false;
    }
    return true;
};

/* -------------- SQUARE FUNCTION --------------- */

typedef struct Square
{
    Unary_Function unary_function;
} Square;

double value_at_square(Unary_Function *fun, double x)
{
    return x * x;
}

PTRFUN squareFunctions[2] = {value_at_square, negative_value_at};

void *constructSquareFunction(Unary_Function *square, int lower_bound, int upper_bound)
{
    createUnaryFunction(square, squareFunctions, lower_bound, upper_bound);
}

Unary_Function *createSquareFunction(int lower_bound, int upper_bound)
{
    Square *square = (Square *)malloc(sizeof(Square));
    constructSquareFunction((Unary_Function *)square, lower_bound, upper_bound);
    return (Unary_Function *)square;
}

/* -------------- LINEAR FUNCTION --------------- */

typedef struct Linear
{
    Unary_Function unary_function;
    double a;
    double b;
} Linear;

double value_at_linear(Unary_Function *fun, double x)
{
    Linear *linear = (Linear *)fun;
    return linear->a * x + linear->b;
}

PTRFUN linearFunctions[2] = {value_at_linear, negative_value_at};

void *constructLinearFunction(Linear *linear, int lower_bound, int upper_bound, double a, double b)
{
    createUnaryFunction((Unary_Function *)linear, linearFunctions, lower_bound, upper_bound);
    linear->a = a;
    linear->b = b;
}

Unary_Function *createLinearFunction(int lower_bound, int upper_bound, double a, double b)
{
    Linear *linear = (Linear *)malloc(sizeof(Linear));
    constructLinearFunction(linear, lower_bound, upper_bound, a, b);
    return (Unary_Function *)linear;
}

/* ------------------- OTHER -------------------- */

int main()
{
    Unary_Function *f1 = createSquareFunction(-2, 2);
    tabulate(f1);
    Unary_Function *f2 = createLinearFunction(-2, 2, 5, -2);
    tabulate(f2);
    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", f2->pfun[1](f2, 1.0));
    free(f1);
    free(f2);
    return 0;
}