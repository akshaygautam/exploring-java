package java8InAction.chapter3.methodRefrencing;


import java.util.Date;

class DemoMethodReferencing {
	int a, b;
	String c, d;
	
	public DemoMethodReferencing() {
		// TODO Auto-generated constructor stub
	}
	
	public DemoMethodReferencing(int a){
		this.a = a;
	}
	
	public DemoMethodReferencing(int a, int b){
		this.a = a;
		this.b = b;
	}
	
	public DemoMethodReferencing(int a, int b, String c, String d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	
	}
	
	public static Date getCurrentDate() {
		return new Date();
	}
	
	public static int square(int n) {
		return n*n;
	}
	
	public static String getName() {
		return DemoMethodReferencing.class.getName();
	}
	
	public static Date getOldDate() {
		Date d = new Date();
		d.setDate(new Date().getDate()-1);
		return d;
	}
	
	public String notStatic() {
		return "Non static";
	}
	
	public int getInstanceVariable() {
		return this.a;
	}
	
	public int getSum() {
		return this.a + this.b;
	}

	public String getAll() {
		return "DemoMethodReferencing [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
	}	
	
}
