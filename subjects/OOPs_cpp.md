# Object Oriented Programming in C++

Object Oriented Programming is a programming paradigm where we design a program using classes and objects (similar to real life). This simplifies overall software development and maintainance.

## Class
Class is basically a template of object which defines the structure of object, it takes no space, since its just a blueprint of object not the actual object;

eg : 
```cpp
class Vehicle{
public:
    string brand_name;
    string model_name;
};
```

Above is a class Vehicle that defines that `Vehicle` object will have two public properties `brand_name` & `model_name`. 

## Object
Objects are the actual datastructure that is formed using a class. 
You can think of it as map of a building is class while actual building is Object.

eg :
```cpp
class Vehicle{
public:
    string brand_name;
    string model_name;
};

int main(){
    // Actual Object
    Vehicle Car;

    // Here Car takes a constant space of O(1) since it is only initialized
    // and no value has been inserted to it.
return 0;
}
```

## Inheritance
Inheritance is basically inherting the properties or behaviour of parent. In OOPs we use inheritance for code reusability.
It is also used in runtime polymorphism.

eg : 
```cpp
class Vehicle{
public:
    string brand_name = "Buggati";
    string model_name = "Chiron";
};

// class Car inherits the Vehicle, the public signifies
// that all the public properties of Vehicle can be
// accessed through Object of Car.
class Car : public Vehicle{
public:
    string owner = "MrHB";
};

int main(){

    Car Buggati;
    // here we are able to access the model_name
    // which is property of Vehicle not Car
    // but since we inherited it we can use it here
    cout << Buggati.model_name << endl;
     

return 0;
}
```

## Encapsulation
Binding (or wrapping) code and data together into a single unit are known as encapsulation. Its basically keeping all one-kind-code in single place.

When we create a class `Vehicle` with some properties and methods we basically encapsulate all code related to Vehicle inside this class block and is completly independent of other code. This helps a lot in code redability, maintainence etc.

## Polymorphism
If one task is performed in different ways, it is known as polymorphism. we use method overloading and method overriding to achieve polymorphism. 

### Method Overloading
Method overloading occurs when a class has multiple methods with the same name but different parameter lists (number or types of parameters).
```cpp
class Calculator {
public:
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
};
```

> Overloaded methods are distinguished by their parameter lists during compile-time. The appropriate method is selected based on the arguments provided.

### Method Overriding
Method overriding occurs when a subclass provides a specific implementation for a method that is already defined in its superclass. The method in the subclass has the same signature (name, return type, and parameters) as the one in the superclass.

```cpp
class Animal {
public:
    virtual void makeSound() {
        std::cout << "Some generic sound\n";
    }
};

class Dog : public Animal {
public:
    void makeSound() override {
        std::cout << "Woof!\n";
    }
};
```
> Overridden methods are chosen dynamically at runtime based on the actual type of the object. This is known as dynamic or late binding.


## Abstraction
Abstraction is a fundamental concept in object-oriented programming (OOP) that involves simplifying complex systems by modeling classes based on the essential properties and behaviors they share. Abstraction allows developers to focus on relevant details while hiding unnecessary complexities. 

Abstract classes are meant to serve as a blueprint for other classes, and they often contain one or more abstract methods that must be implemented by concrete (i.e., non-abstract) subclasses.

This basically means we create a base template for a template, In simple terms we define a abstract class which defines all the neccesary properties and behaviours that should be present inside a class, now when a class inherits this abstract class it should define all those properties and behaviours. This really helps in keeping a code standard and also removes unneccesary complexities revolving aroud it.

It's important to note that the ability to instantiate an abstract class directly is often discouraged because abstract classes are meant to be subclassed, and the instantiation of abstract classes can lead to incomplete or undefined behavior due to the lack of implementations for abstract methods. Instead, the focus is on creating instances of concrete subclasses that provide implementations for all abstract methods.

eg : 
```cpp
// Abstract class representing a Shape
class Shape {
public:
    // Pure virtual function to calculate the area
    virtual double calculateArea() const = 0;

    // Abstract class may have non-virtual functions as well
    void printInfo() const {
        std::cout << "This is a shape.\n";
    }
};

// Concrete class Circle inheriting from Shape
class Circle : public Shape {
private:
    double radius;

public:
    // Constructor
    Circle(double r) : radius(r) {}

    // Implementation of the pure virtual function for area calculation
    double calculateArea() const override {
        return 3.14 * radius * radius;
    }
};
```
## Aggregation
Aggregation is a type of association in object-oriented programming (OOP) where one class refers to another class as a part of its own implementation.

It is a "has-a" relationship, indicating that one class contains an object of another class. Unlike composition, aggregation suggests a weaker relationship, and the objects involved can exist independently.
