package examples;

import java.util.List;

import methodreferences.Person;

public class AggregateOperations {

	public static void main(String[] args) {
		
	     List<Person> roster = Person.createRoster();        
	     System.out.print("Printing rosterList with forEach aggregate operation:");
	     roster.forEach(p -> p.printPerson());
/*
 *  A pipeline is a sequence of aggregate operations & contains the following components:

A source: This could be a collection, an array, a generator function, or an I/O channel. 
In this example, the source is the collection roster.

Zero or more intermediate operations. An intermediate operation, such as filter, produces 
a new stream.
A stream is a sequence of elements. Unlike a collection, it is not a data structure that 
stores elements. Instead, a stream carries values from a source through a pipeline. This 
example creates a stream from the collection roster by invoking the method stream.

The filter operation returns a new stream that contains elements that match its predicate 
(this operation's parameter). In this example, the predicate is the lambda expression 
e -> e.getGender() == Person.Sex.MALE. It returns the boolean value true if the gender 
field of object e has the value Person.Sex.MALE. Consequently, the filter operation in 
this example returns a stream that contains all male members in the collection roster.

A terminal operation. A terminal operation, such as forEach, produces a non-stream result,
such as a primitive value (like a double value), a collection, or in the case of forEach, 
no value at all. In this example, the parameter of the forEach operation is the lambda 
expression e -> System.out.println(e.getName()), which invokes the method getName on the 
object e. (The Java runtime and compiler infer that the type of the object e is Person.)	     
 */
	     
// The following example prints the male members contained in the collection roster 
// with a pipeline that consists of the aggregate operations filter and forEach:
	     
	     System.out.print("\nUsing bulk-data operations for a more complex task:");
	     roster.stream().filter(p -> p.getGender() == Person.Sex.MALE)
	     		.forEach(e -> System.out.print(e.getName() + "; "));
	     
/*
 * The mapToInt operation returns a new stream of type IntStream (which is a stream that 
 * contains only integer values). The operation applies the function specified in its 
 * parameter to each element in a particular stream. In this example, the function is 
 * Person::getAge, which is a method reference that returns the age of the member. 
 * (Alternatively, you could use the lambda expression e -> e.getAge().) Consequently, 
 * the mapToInt operation in this example returns a stream that contains the ages of all 
 * male members in the collection roster.
 * The average operation calculates the average value of the elements contained in a 
 * stream of type IntStream. It returns an object of type OptionalDouble. If the stream 
 * contains no elements, then the average operation returns an empty instance of 
 * OptionalDouble, and invoking the method getAsDouble throws a NoSuchElementException. 
 * The JDK contains many terminal operations such as average that return one value by 
 * combining the contents of a stream. These operations are called reduction operations; 
 * see the section Reduction for more information.
 */
	     
// The following example calculates the average age of all male members contained 
// in the collection roster with a pipeline that consists of the aggregate operations 
// filter, mapToInt, and average:

	     double average = roster
	    		    .stream()
	    		    .filter(p -> p.getGender() == Person.Sex.MALE)
	    		    .mapToInt(Person::getAge)
	    		    .average()
	    		    .getAsDouble();
	     System.out.println("\nAverage is: " + average);
	    
	}
}
