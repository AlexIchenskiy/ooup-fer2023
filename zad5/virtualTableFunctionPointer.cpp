#include <stdio.h>

class B
{
public:
    virtual int prva() = 0;
    virtual int druga(int) = 0;
};

class D : public B
{
public:
    virtual int prva() { return 42; }
    virtual int druga(int x) { return prva() + x; }
};

typedef int (*pfunB1)(B *);
typedef int (*pfunB2)(B *, int);

void methodOutput(B *pb)
{
    long *b = *(long **)pb;

    pfunB1 b1 = (pfunB1)(b[0]);
    pfunB2 b2 = (pfunB2)(b[1]);

    printf("prva() output: %d\n", b1(pb));
    printf("druga() output: %d\n", b2(pb, 5));
}

int main()
{
    B *d = new D();
    methodOutput(d);
    delete d;

    return 0;
}