package java8InAction.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BehaviourParameterization {

	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
		
		/*
		 * Using Thread class with lambda function of Runnable interface 
		 */
		Thread java7Thread = new Thread(() -> java7Ways(inventory));
		
		Thread java8PredicatesThread = new Thread(() -> java8Predicates(inventory));
		
		/*
		 * Anonymous class 
		 */
		Thread java8AnonymousClassThread = new Thread(() ->  {
			List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
				public boolean test(Apple a) {
					return a.getColor().equals("red");
				}
			});
			System.out.println("****** java8AnonymousClassThread ******");
			System.out.println(redApples2);
		}) ;
		
		/*
		 * Lambda
		 */
		Thread java8LambdaThread = new Thread(()->{
			List<Apple> lambdaApple = filter(inventory, (Apple apple) -> apple.getWeight() < 150);
			System.out.println("****** java8LambdaThread ******");
			System.out.println(lambdaApple);
		});
		
		java7Thread.run();
		java8PredicatesThread.run();
		java8AnonymousClassThread.run();
		java8LambdaThread.run();

	}

	private static Object java8Predicates(List<Apple> inventory) {
		/*
		 * Predicates using java 8
		 */
		System.out.println("****** java8PredicatesThread ******");
		List<Apple> greenApplePredicate = filter(inventory, new AppleColorPredicate());
		System.out.println(greenApplePredicate);

		List<Apple> heavyApplePredicate = filter(inventory, new HeavyApplePredicate());
		System.out.println(heavyApplePredicate);
		List<Apple> heavyRedApplePredicate = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(heavyRedApplePredicate);
		return null;
	}

	private static Object java7Ways(List<Apple> inventory) {
		/*
		 * Traditional Java 7
		 */
		System.out.println("****** java7Thread ******");
		List<Apple> greenApples = filterApplesByColor(inventory, "green");
		System.out.println(greenApples);
		List<Apple> heavyApples = filterApplesByWeight(inventory, 150);
		System.out.println(heavyApples);
		return null;
	}

	private static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getColor().equals(color)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > weight) {
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color) {
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" + "color='" + color + '\'' + ", weight=" + weight + '}';
		}
	}

	interface ApplePredicate {
		public boolean test(Apple apple);
	}

	static class AppleColorPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple apple) {
			return apple.getColor().equals("green");
		}

	}

	static class HeavyApplePredicate implements ApplePredicate {

		@Override
		public boolean test(Apple apple) {
			return apple.getWeight() > 150;
		}

	}

	static class AppleRedAndHeavyPredicate implements ApplePredicate {
		public boolean test(Apple apple) {
			return "red".equals(apple.getColor()) && apple.getWeight() > 150;
		}
	}

}
