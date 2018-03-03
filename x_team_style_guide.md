# X-Team 70 Style Guide

Our team plans to be very considerate of consistant style. We heavily value the importance of common standards, as they functions as an integral component of communication, readability, and efficient cooperation. During scheduled group check-ins, we will review each member's code and discusses how conventions can be maintained.

## Naming conventions

When deciding names, team members should try to name things as specifically as possible to increase smooth readability. Abbreviations, especially one-letter names or acronyms, should be avoided. That said, efficiency in naming is important as to avoid bogging down the code's readability with long names. The folowing example shows good naming convention fo

### Examples
* interfaces
    - NewInterface
* classes
    - DescriptiveClass
* exception types
    - FancyNewException
* fields
    - niceCamel
* methods
    - toCamelCase
* parameters
    - superNiceParam
* local variables
    - muchWinning
* constants
    - REAL_GOOD_MULTIPLIER
* some items in a BST
    * nodes to the left and right of the root
         - rootSuccessor
	 - rootPredecessor
    * related ...
         - parentOfSuccessor
	 - leftChildOfRoot

## Commenting style for public and private members of a class or interface:

In-line comments should be used to describe high-level algorithm choices and steps. Public methods, classes, and interfaces should include completed javadocs, which contain descriptions of parameters and return variables. Private methods should have header comments. Fields (member variables) should have inl-line comments. 

### Examples

* classes
* fields
* constructors
* methods
* coding style (brackets, horizontal, and vertical spacing) for:
  * if statements
    - Include a space on either side of a comparison operator (=, <, ect.)
    - When there is only one line of content, it should be put on the same line as the condition. i.e.
```
if(youAre != trash) singleNodeTree.height = 1;
```
    - When there is an else statement, do not use in-line code. Put the word else on the line of the closing bracket. i.e.
```
if(temperature < coldnessThreshold(wisconsin)) {
	putOnCoat();
} else {
	beHappy();
}
```
    - When content is multiple lines, place opening bracket on the same line as the condition. i.e.
```
if(event.equals(mifflin)) {
	dontGoToThat(event);
	beHealthy();
}
```
  * switch statement
  * while loops
  * for loops
  * enhanced for loops
