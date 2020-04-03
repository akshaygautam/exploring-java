package methodRefrencing;

import java.util.Date;

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
