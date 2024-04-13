#include <iostream>

struct Point
{
    int x;
    int y;
};

class Shape
{
public:
    virtual void draw() = 0;
    virtual void move(int x, int y) = 0;

protected:
    struct Point center_;
};

class Square : public Shape
{
public:
    virtual void draw()
    {
        std::cerr << "in drawSquare\n";
    }

    virtual void move(int x, int y)
    {
        this->center_.x += x;
        this->center_.y += y;
        printf("Move square from (%d, %d) to (%d, %d)\n", this->center_.x - x, this->center_.y - y, this->center_.x, this->center_.y);
    }

private:
    double side_;
};

class Circle : public Shape
{
public:
    virtual void draw()
    {
        std::cerr << "in drawCircle\n";
    }

    virtual void move(int x, int y)
    {
        this->center_.x += x;
        this->center_.y += y;
        printf("Move circle from (%d, %d) to (%d, %d)\n", this->center_.x - x, this->center_.y - y, this->center_.x, this->center_.y);
    }

private:
    double radius_;
};

class Rhomb : public Shape
{
public:
    virtual void draw()
    {
        std::cerr << "in drawRhomb\n";
    }

    virtual void move(int x, int y)
    {
        this->center_.x += x;
        this->center_.y += y;
        printf("Move rhomb from (%d, %d) to (%d, %d)\n", this->center_.x - x, this->center_.y - y, this->center_.x, this->center_.y);
    }

private:
    double side_;
};

void drawShapes(Shape **shapes, int n)
{
    for (int i = 0; i < n; i++)
    {
        struct Shape *s = shapes[i];
        s->draw();
    }
}

void moveShapes(Shape **shapes, int n, int x, int y)
{
    for (int i = 0; i < n; i++)
    {
        struct Shape *s = shapes[i];
        s->move(x, y);
    }
}

int main()
{
    Shape *shapes[5];
    shapes[0] = new Circle();
    shapes[1] = new Square();
    shapes[2] = new Square();
    shapes[3] = new Circle();
    shapes[4] = new Rhomb();

    drawShapes(shapes, 5);
    moveShapes(shapes, 5, 2, 4);

    return 0;
}