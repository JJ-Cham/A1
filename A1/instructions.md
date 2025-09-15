# Assignment 1: Dynamic Array

For this assignment, you will write a new class called `DynamicArray` that implements the key features of a dynamic array, as described in lecture.
In other words, you are building your own simplified version of Java’s `ArrayList`.
We will not try to match every single method from `ArrayList`, but the methods you implement should behave consistently with how `ArrayList` acts.

Your `DynamicArray` should be designed in an object-oriented style: whenever the instructions refer to “the current `DynamicArray`”, this means the particular instance (object) of the class on which a method is called.

You are expected to test your class thoroughly to ensure that all methods work correctly, including proper behavior when invalid indices are given (e.g., throwing exceptions).

Next week, you will then use your `DynamicArray` class to build a simple file-editing program that can replace text in a file.
(This is inspired by the Unix command sed, which performs stream editing. Your program will be a simplified version.)

## DynamicArrayADT

For this assignment we are providing a text version of the ADT.  Your first task is to translate the descriptions into a Java interface that works for a generic type (much like in A0).
Your interface should include the operations listed below:

### Group 1 — Basic Array Behavior

The methods in Group 1 are the same ones you described in your Interface for A0. They each have an equivalent method in class `ArrayList`.

* `set` should update the value stored at the given index and return the previous value (which will be `null` if it was previously unset). 
It should throw `IndexOutOfBoundsException` for invalid indices.

* `get` should return the element stored at an index. It should also throw `IndexOutOfBoundsException` for invalid indices.

* `size` should return the number of elements stored in the `DynamicArray`.

### Group 2 — Adding and Removing Elements

The methods in this group modify the array size by one element at a time.  These are mutating methods because they change the state of the object they are called upon.   They each have an equivalent method in class `ArrayList`.

* `add` should insert an element at a provided index and shift any subsequent elements to the right.
It should throw `IndexOutOfBoundsException` for invalid indices.
Valid indices will are between 0 and size() -- adding at index `size` is the same as appending.
You may want to overload this method for append (special case), where only the value to be added is passed, as is done in class `ArrayList`.

* `remove` removes and returns the element at an index, then shifts the subsequent elements to the left.
It should throw `IndexOutOfBoundsException` for invalid indices (which are different from `add`).

### Group 3 — Whole-Array Operations

The methods in this group are for combining and splitting whole arrays.  As described here, they should all use the functional style, meaning that they make no changes to the object they are called upon, nor to the other arguments.  Some of these differ from their equivalent methods in `ArrayList`, which may have different names and/or not be functional in design.

* `append` concatenates a passed `DynamicArray` to the end of the current `DynamicArray` and returns the result as a new `DynamicArray` object.

* `insert` inserts all the elements of a passed `DynamicArray` at the specified index, returning the result as a new `DynamicArray`.

* `sublist` returns a view (i.e., copy) of the current `DynamicArray` in the range [fromIndex, toIndex).

* `delete` removes the elements spanning from the first index up to just before the other, i.e., [fromIndex, toIndex), in the current array. It then returns the resulting `DynamicArray`.

* `extract` returns a new `DynamicArray` containing the elements from one index up to just before another, [fromIndex, toIndex).

### Group Four: Kudos Additions

Our `DynamicArray` can exceed the standard array in another respect:  we can make it allow for negative indices and indexing that does not start from 0.  To do this, you can add the methods below.  This portion is for kudos only, if you are looking to learn a little bit more.

* `lowIndex` returns the lowest valid index for this collection.  For traditional Java arrays this will always be 0, but our deluxe array might have a different range of valid values (see below).

* `highIndex` returns the highest valid index for this collection.  For traditional Java arrays this will always be the same as `size - 1`, but our deluxe array might have a different range of valid values (see below).

* `size` returns the number of elements between `lowIndex` and `highIndex`, inclusive.

* `indexInRange` returns a boolean value indicating whether a particular index is valid for purposes of the `get` method.

## Implementation and Testing

Once you have the ADT (upper duck), the next step is to make it real by producing an implementation (lower duck).  You should also develop a robust set of tests hand in hand with the implementation itself.

### Implementation

Once you've written an interface, the next step is to implement it as a Java class, `DynamicArray`.
Your class will have a field that stores a native Java array -- this is the actual storage that your methods are built on top of.
You will need at least one constructor for your class, whose job is to allocate the initial array storage space.
It should take an integer length as its argument.
It is also a good idea to write a copy constructor that takes a `DynamicArray` as its input and makes a deep copy of it, allocating new array storage and looping to copy all the values.

There is a quirk in the way Java implements generic classes that makes it impossible to allocate array storage for the generic.
To get around this, you should include the private utility method below in your class, and call it when you want to allocate new array space.

~~~
    /**
     * Private utility to do array allocation
     */
    @SuppressWarnings("unchecked")
    private T[] allocate(int len) {
        return (T[]) new Object[len];
    }
~~~

Tips:
* To implement the methods in Group 3 from the ADT, think through what we discussed in class about operations on arrays -- what must happen when your modification changes the size of the array?
* Do not import any packages: your implementations should demonstrate all steps required to implement each method.
* If you get stuck, reviewing the slides is likely to help.
* Also, feel free to discuss with classmates how these implementations should work. 
* If you implement the kudos extension, you need a few more fields in your class to keep track of the upper and lower index bounds and the index offset.  Make sure that the mutator methods *add* and *remove* correctly update those fields to reflect any changes they make.

### Testing

Good testing makes the implementation much easier, because it helps you to identify and solve bugs in your code.
As you write your code (or even before!), you should create a set of tests to check that your methods work properly.
To keep this assignment at a reasonable length, this week we are only requiring formal tests for four methods:  *get*, *add*, *append*, and *extract*.  (Note that our tests will still check the implementations of all of your methods.)
We have provided you with example tests for *append*.

To simplify grading, we request that you write a class called `DynamicArrayTest` that contains JUnit tests for each of your methods.  We have provided a starter file, which you should flesh out further.
(Note: the exception to the "no packages" rule is that you should import `Junit` for testing.)
We will evaluate your code based on running your tests, plus some of our own.

A reminder on how to write tests:  every constructor and method should be tested.  Besides covering the expected use cases, try to think about edge cases -- the exceptions to the normal assumptions that are nevertheless still valid usage.  Additionally, if a method is supposed to throw an exception in certain circumstances, you should write a test to verify that it does.

We’ve provided a wrapper class called RunTests that makes JUnit output easier to read.
To compile and run your tests from the command line:

MacOS / Linux:
```
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar *.java
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar RunTests
```

Windows (PowerShell or CMD):
```
javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar *.java
java  -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar RunTests
```

#### Example: Append

You'll see we provided you with tests for the `append` method.
Walking through the logic: to test the *append* operation, we begin with a few tests of its basic functionalities, varying the number of elements, using singletons, etc.
For each call, we want to check: 
* that the result is correct, and 
* that the original arrays have not been modified by the operation.

Next, we want to think of the edge cases:
* How about if you try to append an array to itself?  Since we are implementing a functional style, this should give a valid result!
* What if one or both arrays is empty (but not `null`)?
* Note: You don't need to worry about the case where either array is `null`, because that will throw a `NullPointerException` -- the appropriate response.

#### Example: Extract

What about the *extract* method?  
Besides standard cases, you should probably have a test that considers each of the following possibilities:
* extracting the entire array
* extracting zero elements
* extracting from an empty array
* low index is negative (ArrayIndexOutOfBoundsException)
* high index is greater than array length (ArrayIndexOutOfBoundsException)
* low index is greater than array length (ArrayIndexOutOfBoundsException)
* high index is negative (ArrayIndexOutOfBoundsException)
* high index is less than low

## Submitting

For this assignment, you should submit your work via Gradescope.  We will be setting up an autograder there that can tell you immediately whether your implementation passes our suite of tests.  More details will be posted when this is available.