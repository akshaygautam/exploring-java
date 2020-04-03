package methodRefrencing;

import java.util.Date;

class DemoMethodReferencing {
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
}
