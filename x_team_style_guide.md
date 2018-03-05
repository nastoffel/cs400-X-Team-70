# X-Team 70 Style Guide

Our team plans to be very considerate of consistant style. We heavily value the importance of common standards, as they functions as an integral component of communication, readability, and efficient cooperation. During scheduled group check-ins, we will review each member's code and discusses how conventions can be maintained.

## Naming conventions

When deciding names, team members should try to name things as specifically as possible to increase smooth readability. Abbreviations, especially one-letter names or acronyms, should be avoided. That said, efficiency in naming is important as to avoid bogging down the code's readability with long names. The folowing example shows good naming convention fo

### Examples
* Interfaces
    - NewInterface
* Classes
    - DescriptiveClass
* Exception types
    - FancyNewException
* Fields
    - niceCamel
* Methods
    - toCamelCase
* Parameters
    - superNiceParam
* Local variables
    - muchWinning
* Constants
    - REAL_GOOD_MULTIPLIER
* Some items in a BST
    * rootSuccessor
    * rootPredecessor
    * parentOfSuccessor 
    * leftChildOfRoot

## Commenting style for public and private members of a class or interface:

In-line comments should be used to describe high-level algorithm choices and steps. Public methods, classes, and interfaces should include completed javadocs, which contain descriptions of parameters and return variables. Private methods should have header comments. Fields (member variables) should have inl-line comments. 

### Examples

* Classes
	* Note: Javadoc comments should be written in complete sentences, while other types of commenting may be written in fragments

```
/**
 * This class tracks activity in Gordons, including student inflow
 * and outflow, available seating, occupation of event rooms, and
 * usage of the available food stations on a per customer basis. It
 * It also tracks income from food sales.
 *
 * @author CS400-X-Team-70
 * @param T
 */
public class GordonAvenueMarket<T> {
	(contents of class)
}
```
* Fields
```
private Student[] students[10]; // This array of Student items will 
				// keep track of the students that are
				// currently in the market.

private double revenue; // keeps track of how much revenue is produced 
		       // from food sales
```
* constructors
```
public GordonAvenueMarket(Student[] students, double revenue) {
	this.students = students;
	this.revenue = revenue;
}
```
* methods
  * Accessors
    - Name of accessor method for field **X** should be **getX()**
```
public double getRevenue() {
	return this.revenue;
}
```
  * Mutators
    - Name of mutator method for field **Y** should be **setY**
```
public void setRevenue(double revenue) {
	this.revenue = revenue;
}
```
  * Public methods
```
/** 
 * Removes student from GordonAvenueMarket
 * @param studentName name of student
 * @return true if student is found in the market and removed, false otherwise
 */
public boolean removeStudent(String studentName) {
	return this.remove(studentName);
}
```
  * Private helper methods
```
//removes Student with name studentName from this.students
private boolean remove(String studentName) {
	for (Student s : this.students) {
		if (studentName.equals(s.getName())) {
			s = null;
			return true;
		}
	}
	return false;
}
```
* coding style (brackets, horizontal, and vertical spacing) for:
  * if statements
    - Include a space on either side of a comparison operator (=, <, ect.)
    - When there is only one line of content, it should be put on the same line as the condition. i.e.
```
if(youAre != trash) singleNodeTree.height = 1;
```
  When there is an else statement, do not use in-line code. Put the word else on the line of the closing bracket. i.e.
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
    - Cases should be at same level of indentation as the switch statement
    - Every case should either have a break statement or a `/* falls through */` comment
    - The `break` in the default case is redundant, but good to have just in "case"
```
switch(i) {
case 0:
	doThis();
	/* falls through */
case 1:
	doThat();
	break;
case 2:
	doThis();
	doThat();
	break;
default:
	justDoIt();
	break;
}
```
  * while loops
    - Empty while statement
```
while (isTrue);
```
    - While loop
```
while (isTilted) {
	runItDownMid();
}
```
  * for loops
    - Empty for statement
```
for (int i = 0; i < max; i++);
```
    - For loop
```
for (int i = 0; i < max; i++) {
	doTheThing();
}
```
  * enhanced for loops
    - Empty enhanced for statement
```
for (Object o : objects);
```
    - Enhanced for loop
```
for (MenuItem item : McDonaldsMenu) {
	order(item);
}
```
