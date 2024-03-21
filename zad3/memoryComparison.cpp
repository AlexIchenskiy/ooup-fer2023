#include <stdio.h>

class CoolClass
{
public:
    virtual void set(int x) { x_ = x; };
    virtual int get() { return x_; };

private:
    int x_;
};
class PlainOldClass
{
public:
    void set(int x) { x_ = x; };
    int get() { return x_; };

private:
    int x_;
};

int main()
{
    printf("Size of CoolClass: %lu\n", sizeof(CoolClass));
    printf("Size of PlainOldClass: %lu\n", sizeof(PlainOldClass));

    return 0;
}