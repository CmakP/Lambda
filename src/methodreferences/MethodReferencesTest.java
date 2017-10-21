package methodreferences;

import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.Set;
import java.util.HashSet;

/*
 * Kinds of Method References

There are four kinds of method references:

			Kind												Example
-----------------------------------------------------------------------------------------
Reference to a static method						ContainingClass::staticMethodName

Reference to an instance method of 	
a particular object									ContainingObject::instanceMethodName

Reference to an instance method of 
an arbitrary object of a particular type			ContainingType::methodName

Reference to a constructor							ClassName::new
 */
public class MethodReferencesTest {
        
    public static void main(String... args) {

        List<Person> roster = Person.createRoster();        
        System.out.println("Printing rosterList: ");
        for (Person p : roster) p.printPerson();

        
        class PersonAgeComparator implements Comparator<Person> {
        	@Override
        	public int compare(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }
        
        Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);
        // Without method reference
        Arrays.sort(rosterAsArray, new PersonAgeComparator());
        System.out.println("\n\nPrinting sorted rosterAsArray with method reference(comparator): ");
        for (Person p : rosterAsArray) p.printPerson();
        
        // With lambda expression
        Arrays.sort(rosterAsArray, (Person a, Person b) -> {
        	return a.getBirthday().compareTo(b.getBirthday());
        });
        // or
        Arrays.sort(rosterAsArray, (a, b) -> a.getBirthday().compareTo(b.getBirthday()));
        System.out.println("\n\nPrinting sorted rosterAsArray with lambda: ");
        for (Person p : rosterAsArray) p.printPerson();
         
        // Reference to a static method of a Class 
        Arrays.sort(rosterAsArray, Person::compareByAge);
        System.out.println("\n\nPrinting sorted rosterAsArray with method reference: ");
        for (Person p : rosterAsArray) p.printPerson();
        
        
        // Reference to an instance method of a particular object
        class ComparisonProvider {
            public int compareByName(Person a, Person b) {
                return a.getName().compareTo(b.getName());
            }
        
            @SuppressWarnings("unused")
			public int compareByAge(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }
        
//        ComparisonProvider myComparisonProvider = new ComparisonProvider();
//        Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
        // or
        Arrays.sort(rosterAsArray, new ComparisonProvider()::compareByName);
        System.out.println("\n\nPrinting sortedByName rosterAsArray with Reference to an instance method of a particular object:");
        for (Person p : rosterAsArray) p.printPerson();
           
        // Reference to an instance method of an arbitrary object of a particular type   
        String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        System.out.println("\n\nPrinting sortedIgnoreCase stringArray:\n" + Arrays.toString(stringArray));
     
        @SuppressWarnings("unused")
		Set<String> rosterSetClassic = new HashSet<>(Arrays.asList(stringArray));
        @SuppressWarnings("unused")
		Set<Person> rosterSetLambda =
            transferElements(roster, () -> { return new HashSet<>(); });
        // or
        Set<Person> rosterSet = transferElements(roster, HashSet::new);
        System.out.println("\n\nPrinting rosterSet:");
        rosterSet.stream().forEach(p -> p.printPerson());
    }
    
    // The method transferElements copies elements from one collection to another
    public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST 
    	transferElements(SOURCE sourceCollection, Supplier<DEST> collectionFactory) {
        
            DEST result = collectionFactory.get();
            for (T t : sourceCollection) {
                result.add(t);
            }
            return result;
    }  
}