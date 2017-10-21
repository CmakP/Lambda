package methodreferences;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.lang.Iterable;

public class RosterTest {

    interface CheckPerson {
        boolean test(Person p);
    }

    // Approach 1: Create Methods that Search for Persons that Match One
    // Characteristic

    public static void printPersonsOlderThan(List<Person> roster, int age) {
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

    // Approach 2: Create More Generalized Search Methods

    public static void printPersonsWithinAgeRange(
        List<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printPerson();
            }
        }
    }

    // Approach 3: Specify Search Criteria Code in a Local Class
    // Approach 4: Specify Search Criteria Code in an Anonymous Class
    // Approach 5: Specify Search Criteria Code with a Lambda Expression

    public static void printPersons(List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }
    
/*
 * Very simple interfaces such as functional interfaces which contain only one abstract 
 * method, might not be worth it to define one in your application.  
 * However, the JDK defines several standard functional interfaces, which you can find 
 * in the package java.util.function
 * 
 * For example, you can use the Predicate<T> interface in place of CheckPerson. 
 
interface CheckPerson {
    boolean test(Person p);
}

 * This interface contains the method boolean test(T t):

interface Predicate<T> {
    boolean test(T t);
}
*/

    // Approach 6: Use Standard Functional Interfaces with Lambda Expressions

    public static void printPersonsWithPredicate(
        List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }
    
/*
 * Suppose you want a lambda expression similar to printPerson, one that takes one 
 * argument (an object of type Person) and returns void. Remember, to use a lambda 
 * expression, you need to implement a functional interface. In this case, you need 
 * a functional interface that contains an abstract method that can take one argument 
 * of type Person and returns void. The Consumer<T> interface contains the method 
 * void accept(T t), which has these characteristics.    
 */

    // Approach 7: Use Lambda Expressions Throughout Your Application

    public static void processPersons(List<Person> roster, Predicate<Person> tester,
        Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

/*
 * What if you want to do more with your members' profiles than printing them out. 
 * Suppose that you want to validate the members' profiles or retrieve their contact 
 * information? In this case, you need a functional interface that contains an abstract 
 * method that returns a value. The Function<T,R> interface contains the 
 * method R apply(T t). The following method retrieves the data specified by the 
 * parameter mapper, and then performs an action on it specified by the parameter block:    
 */
    // Approach 7, second example

    public static void processPersonsWithFunction(
        List<Person> roster,
        Predicate<Person> tester,
        Function<Person, String> mapper,
        Consumer<String> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }
 /*
  * This method invocation performs the following actions:
  
1.Obtains a source of objects from the collection source. In this example, it obtains 
  a source of Person objects from the collection roster. Notice that the collection roster,
  which is a collection of type List, is also an object of type Iterable.
2.Filters objects that match the Predicate object tester. In this example, the Predicate
  object is a lambda expression that specifies which members would be eligible for 
  Selective Service.
3.Maps each filtered object to a value as specified by the Function object mapper. 
  In this example, the Function object is a lambda expression that returns the e-mail 
  address of a member.
4.Performs an action on each mapped object as specified by the Consumer object block. 
  In this example, the Consumer object is a lambda expression that prints a string, 
  which is the e-mail address returned by the Function object.
  */
    
    // Approach 8: Use Generics More Extensively

    public static <X, Y> void processElements(
        Iterable<X> source,
        Predicate<X> tester,
        Function<X, Y> mapper,
        Consumer<Y> block) {
            for (X p : source) {
                if (tester.test(p)) {
                    Y data = mapper.apply(p);
                    block.accept(data);
                }
            }
    }

    public static void main(String... args) {

        List<Person> roster = Person.createRoster();

        for (Person p : roster) {
            p.printPerson();
        }

        // Approach 1: Create Methods that Search for Persons that Match One
        // Characteristic

        System.out.println("\nPersons older than 20:");
        printPersonsOlderThan(roster, 20);
     
        // Approach 2: Create More Generalized Search Methods

        System.out.println("\nPersons between the ages of 14 and 30:");
        printPersonsWithinAgeRange(roster, 14, 30);
      
        // Approach 3: Specify Search Criteria Code in a Local Class

        System.out.println("\nPersons who are eligible for Selective Service:");

        class CheckPersonEligibleForSelectiveService implements CheckPerson {
            @Override
        	public boolean test(Person p) {
                return p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25;
            }
        }

        printPersons(roster, new CheckPersonEligibleForSelectiveService());

        // Approach 4: Specify Search Criteria Code in an Anonymous Class

        System.out.println("\nPersons who are eligible for Selective Service " +
            "(anonymous class):");

        printPersons(
            roster,
            new CheckPerson() {
            	@Override
                public boolean test(Person p) {
                    return p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25;
                }
            }
        );

        // Approach 5: Specify Search Criteria Code with a Lambda Expression

        System.out.println("\nPersons who are eligible for Selective Service (lambda expression):");
        
        printPersons(roster,
            (Person p) -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25
        );

        // Approach 6: Use Standard Functional Interfaces with Lambda Expressions

        System.out.println("\nPersons who are eligible for Selective Service (with Predicate parameter):");

        printPersonsWithPredicate(roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25
        );

         // Approach 7: Use Lamba Expressions Throughout Your Application

        System.out.println("\nPersons who are eligible for Selective Service " +
            "(with Predicate and Consumer parameters):");

        processPersons(roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.printPerson()
        );

        // Approach 7, second example

        System.out.println("\nPersons who are eligible for Selective Service " +
            "(with Predicate, Function, and Consumer parameters):");

        processPersonsWithFunction(roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );

        // Approach 8: Use Generics More Extensively

        System.out.println("\nPersons who are eligible for Selective Service (generic version):");

        processElements(roster,
            p -> p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25,
            p -> p.getEmailAddress(),
            email -> System.out.println(email)
        );

/*
 * The operations filter, map, and forEach are aggregate operations. Aggregate 
 * operations process elements from a stream, not directly from a collection 
 * (which is the reason why the first method invoked in this example is stream). 
 * A stream is a sequence of elements. Unlike a collection, it is not a data structure 
 * that stores elements. Instead, a stream carries values from a source, such as 
 * collection, through a pipeline. A pipeline is a sequence of stream operations, 
 * which in this example is filter- map-forEach. In addition, aggregate operations 
 * typically accept lambda expressions as parameters, enabling you to customize how 
 * they behave.
 
The following table maps each of the operations the method processElements 
performs with the corresponding aggregate operation:

			processElements Action					Aggregate Operation
---------------------------------------------------------------------------------------
Obtain a source of objects							Stream<E> stream()

Filter objects that match a 
Predicate object						Stream<T> filter(Predicate<? super T> predicate)

Map objects to another value as specified 
by a Function object			<R> Stream<R> map(Function<? super T,? extends R> mapper)

Perform an action as specified 
by a Consumer object						void forEach(Consumer<? super T> action)

 */
// Approach 9: Use Aggregate (Bulk Data) Operations That Accept Lambda Expressions as Parameters
    
        System.out.println("\nPersons who are eligible for Selective Service " +
            "(with bulk data operations):");

        roster
            .stream()
            .filter(
                p -> p.getGender() == Person.Sex.MALE
                    && p.getAge() >= 18
                    && p.getAge() <= 25)
            .map(p -> p.getEmailAddress())
            .forEach(email -> System.out.println(email));
     }
}