package methodRefrencing;

import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class DemoMethodReferencingTest {

	public static void main(String[] args) {
		//Basically I provided the method definition using a static class
		// This can help in collection of same type of methods, to be segregated in a static class
		//So we can create multiple implementations of the function in functional interface, like, I can have many methods
		//In DateUtilStatic class that can return date, but internals of computing date is different.
		//Like I did.
		testFuncDate date = DemoMethodReferencing::getCurrentDate;
		testFunString str = DemoMethodReferencing::getName;
		testFunSqr sqr = DemoMethodReferencing::square;
		System.out.println(date.use());
		System.out.println(str.use());
		System.out.println(sqr.use(20));
		
		//Setting new implementation
		date = DemoMethodReferencing::getOldDate;
		System.out.println(date.use());
		
		
		//Constructor referencing
		// Default constructor
		Supplier<DemoMethodReferencing> c1 = DemoMethodReferencing::new;
		
		DemoMethodReferencing object = c1.get();
		System.out.println(object.notStatic());
		
		//Parameterized constructor
		Function<Integer, DemoMethodReferencing> c2 = DemoMethodReferencing::new;
		object = c2.apply(10);
		System.out.println(object.getInstanceVariable());
		
		//Double parameters
		BiFunction<Integer, Integer, DemoMethodReferencing> c3 = DemoMethodReferencing::new;
		object = c3.apply(18, 17);
		System.out.println(object.getSum());
		
		testMultipleArgumentConst<Integer, Integer, String, String, DemoMethodReferencing> c4 = DemoMethodReferencing::new;
		object = c4.apply(34, 23, "Multiple", "yeh its working");
		
		System.out.println(object.getAll());
	}

}

interface testFuncDate {
	Date use();
}

interface testFunString {
	String use();
}

interface testFunSqr {
	int use(int n);
}

interface testMultipleArgumentConst<T, U, V, A,  R>{
	R apply(T t, U u, A a, V v);
}
