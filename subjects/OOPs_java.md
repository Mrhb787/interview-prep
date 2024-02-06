# Object Oriented Programming in Java

Object Oriented Programming is a programming paradigm where we design a program
using classes and objects (similar to real life). This simplifies overall software development and maintainance.

The core concept of the object-oriented approach is to break complex problems into smaller objects.

## Index
|No. |Topic|
|:--|:--|
|1.|What is Java|
|2.|Class|
|3.|[Object](#object)|
|4.|Java Virtual Machine (JVM)|
|5.|[Access Modifiers](#access-modifiers)|
|6.|Constructors|
|7.|Methods|
|8.|Key principles of OOPs|
|9.|Interfaces|

## What is Java
Java is a general-purpose, class-based, object-oriented programming language, which works on
different operating systems such as Windows, Mac, and Linux.
Java is used in Desktop applications, Web applications, Mobile applications (especially Android apps), Web Servers etc.
Java is case-sensitive.

## Class
```java
public class Car {
  // properties
  // methods
} 
```

## Object
```java
Car A = new Car();
```

## Java Virtual Machine
It's a software-based virtual computer that runs Java bytecode.
**Java Code Compilation:** When you write a program in Java, you create a source code file with a `.java` extension. This code needs to be translated into a format that a computer can understand and execute.

**Compilation to Bytecode:** Java code is compiled into an intermediate form called bytecode. Bytecode is a set of instructions that is not specific to any particular computer architecture. It's platform-independent.

**JVM Execution:** Instead of running the Java bytecode directly on the hardware, it runs on the Java Virtual Machine. The JVM interprets or compiles the bytecode into machine code that is specific to the underlying hardware, allowing the Java program to run on different platforms without modification.

*The JVM also takes care of memory management, including allocating and freeing up memory, as well as garbage collection, which automatically identifies and removes unused objects to free up resources.*

## Access Modifiers
Access modifiers are keywords that set the accessibility of classes, methods, and other members. 
These keywords determine whether a field or method in a class can be used or invoked by another method in another class or sub-class.

We have four types of access modifiers, which are:

### Default
The default access modifier is also called package-private. You use it to make all members within the same package visible, but they can be accessed only within the same package.

```java
class Animal {
    void speak() { 
           System.out.println("Woof!"); 
       }
} 
class Main
{ 
    public static void main(String args[]) 
       {   
          Animal dog = new Animal(); 
          dog.speak();  
       } 
}
```
### Public
The default access modifier is also called package-private. You use it to make all members within the same package visible, but they can be accessed only within the same package.
```java
public class Car {
    // public variable
    public str model;

    // public method
    public void showModel() {
        System.out.println("My Model is " + model);
    }
}

public class Main {
    public static void main( String[] args ) {
        // accessing the public class
        Car A = new Car();

        // accessing the public variable
        car.model = "Audi R8";
        // accessing the public method
        car.showModel();
    }
}
```
### Private
The private access modifier is an access modifier that has the lowest accessibility level. This means that the methods and fields declared as private are not accessible outside the class. They are accessible only within the class which has these private entities as its members.
```java
class A {
    private String name;
}

public class Main {
    public static void main(String[] main) {
        A classA = new A();
        classA.name = "John"; 
    }
}
```

**ERROR Arises**
```java
Main.java:49: error: name has private access in A
        A.name = "John";
```
This is because we are trying to access the private variable and field from another class.
5. Protected

## Key Concepts of OOPS
1. Encapsulation
2. Inheritance
3. Abstraction
4. Polymorphism

## Polymorphism
Polymorphism is one of the four fundamental principles of object-oriented programming (OOP), and Java supports both compile-time (static) polymorphism and runtime (dynamic) polymorphism. In simple terms, polymorphism allows objects to be treated as instances of their parent class rather than their actual class, promoting flexibility and extensibility in your code.

### Compile-Time Polymorphism (Method Overloading)
Method overloading allows a class to have multiple methods with the same name but different parameters (either different types or a different number of parameters). The compiler determines which method to call based on the method signature during compile time.
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }
}
```

### Runtime Polymorphism (Method Overriding)
Method overriding occurs when a subclass provides a specific implementation for a method that is already defined in its superclass. The decision on which method to call is made during runtime based on the actual object type.
```java
class Animal {
    public void makeSound() {
        System.out.println("Some generic sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }
}
```
## Inheritance
```java
class Animal {
  String name;
  public void makeSound() {
    System.out.println("I can woof!!");
  }
}

class Dog extends Animal {
  public void getName() {
    System.out.println("My name is " + name);
  }
}
```
## Interfaces
An interface is a collection of abstract methods. In other words, an interface is a completely
"abstract class" used to group related methods with empty bodies.
An interface specifies what a class can do but not how it can do it.

```java
// create an interface
interface Person {
  string name;
  void getName(String name);
}

// class implements interface
class Male implements Person {

  // implementation of abstract method
  public void getName(String name) {
    System.out.println("My name is: " + name);
  }
}

class Main {
  public static void main(String[] args) {
    Male A = new Male();
    A.getName("Java");
  }
}
```
