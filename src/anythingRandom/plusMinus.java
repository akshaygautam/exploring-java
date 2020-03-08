package anythingRandom;

public class plusMinus {
	static int i = 0;
	public static void main(String[] args) {
		plus(0);
	}
	
	public static void plus(int a) {
		if(a == 10) return;
		plus(++a);
		System.out.println("at I= "+(i+1)+" a: "+a);
	
	}
}
