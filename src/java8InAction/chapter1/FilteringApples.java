package java8InAction.chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {

	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
		
		/*
		 * Traditional way create a method and call it use loops etc
		 */
		System.out.println("-----JAVA-7-----");
		List<Apple> greenApple = filterGreenApples(inventory);
		List<Apple> heavyApple = filterHeavyApples(inventory);
		System.out.println(greenApple);
		System.out.println(heavyApple);
		
		System.out.println("---- Applying cool JAVA-8------");
		System.out.println("****** METHOD REFERENCE *******");
		/*
		 * Using method reference and predicates
		 * We passed the function as argument to a method
		 * :: <- method reference symbol
		 */
		List<Apple> greenApplePredicate = filterApples(inventory, FilteringApples::isGreenApple);
		List<Apple> heavyApplePredicate = filterApples(inventory, FilteringApples::isHeavyApple);
		System.out.println(greenApplePredicate);
		System.out.println(heavyApplePredicate);
		
		System.out.println("****** LAMBDA FUNCTION *******");
		/*
		 * Using Lambdas or anonymous function
		 * 
		 */
		List<Apple> greenAppleLambda = filterApples(inventory, (Apple a)->"green".equals(a.getColor()));
		List<Apple> heavyAppleLambda = filterApples(inventory, (Apple a)->a.getWeight() > 150);
		System.out.println(greenAppleLambda);
		System.out.println(heavyAppleLambda);
		
		List<Apple> weirdAppleLambda = filterApples(inventory, (Apple a)->a.getWeight() > 180 && a.getColor().equals("brown"));
		System.out.println(weirdAppleLambda);
		
		
	} 

	/*
	 * These are my predicates
	 */
	public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor()); 
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }
    /*
     * Predicate is a function as argument
     */
    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }   
    
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("green".equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterHeavyApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > 150) {
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

}
