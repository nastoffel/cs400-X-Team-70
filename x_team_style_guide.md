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

In-line comments should be used to describe high-level algorithm choices and steps. Public methods, classes, and interfaces should include completed javadocs, which contain descriptions of parameters and return variables. Private methods should have header comments. Fields (member variables) should have in-line comments. 

### Examples

  *Classes*  
  
* Note: Javadoc comments should be written in complete sentences, while other types of commenting may be written in fragments
  

```javascript
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
*Fields*

* Note: In general, in-line comments should maintain their column position between lines
```javascript
private Student[] students[10]; // This array of Student items will 
				// keep track of the students that are
				// currently in the market.

private double revenue; // keeps track of how much revenue is produced 
		        // from food sales
```
*Constructors*
```javascript
public GordonAvenueMarket(Student[] students, double revenue) {
	this.students = students;
	this.revenue = revenue;
}
```
*Methods*
    
* Accessors: Name of accessor method for field **X** should be **getX**
```javascript
public double getRevenue() {
	return this.revenue;
}
```
* Mutators: Name of mutator method for field **Y** should be **setY**
```javascript
public void setRevenue(double revenue) {
	this.revenue = revenue;
}
```
*Public methods*
```javascript
/** 
 * Removes student from GordonAvenueMarket
 
 * @param student - Name name of student
 * @return true if student is found in the market and removed; 
 * false if student was not found
 */
public boolean removeStudent(String studentName) {
	return this.remove(studentName);
}
```
*Private helper methods*
```javascript
/**
 * Sets the specified student in the student[] array to null.
 *
 * @param studentName - student being removed from the array
 * @return true if student was removed; false if student was
 * not found
 */
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
## Coding style (brackets, horizontal, and vertical spacing):
*Conditionals*

* A blank line should be inserted before and after conditionals
* Include a space on either side of a comparison operator (=, <, ect.)
* When there is only one line of content, it should be put on the same line as the condition. i.e.
```javascript
if(youAre != trash) singleNodeTree.height = 1;
```
* Do not use in-line code for else statements. Put the word else on the line of the closing bracket. i.e.
```javascript
if(temperature < coldnessThreshold(wisconsin)) {
	putOnCoat();
} else {
	beHappy();
}
```
* When content is multiple lines, place opening bracket on the same line as the condition. i.e.
```
if(event.equals(mifflin)) {
	dontGoToThat(event);
	beHealthy();
}
```
*Switch statement*

* Cases should be at same level of indentation as the switch statement
* Every case should either have a break statement or a `/* falls through */` comment
* The `break` in the default case is redundant, but good to have just in "case"
```javascript
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
*While loops*

* If there is only a single statement in the while loop, it should be put on the same line as the condition
* A blank line should be inserted before and after while loops
```javascript
while (isTilted) runItDownMid();
```
* Otherwise, place statements in brackets
```javascript
while (!outOfDonuts) {
	dipDonutInCoffee();
	eatDonut();
	sipCoffee();
}
```	
*For loops*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Same styling as while loops
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Example of a single statement for loop
```javascript
for (int i = 0; i < max; i++) doTheThing();
```
* Example of a multi-statement for loop
```javascript
for (int i = max; i > 0; i--) {
	eatGreenEggs();
	eatGreenHam();
}
```
*Enhanced for loops*
* Same styling as while loops
* Example of a single statement enhanced for loop
```javascript
for (MenuItem item : McDonaldsMenu) order(item);
```
* Example of a multi-statement while enhanced for loop
```javascript
for (Month m : year) {
	payRent();
	buyFood();
	getBeer();
}
```
