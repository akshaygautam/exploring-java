package java8InAction.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Lambdas {

	public static void main(String[] args) {
		Runnable r = ()->System.out.println("Lambda thread");
		r.run();
		List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));
		List<Apple> greenApple = filter(inventory, apple->apple.color.equals("green"));
		System.out.println(greenApple);
	
		Comparator<Apple> c = (apple1, apple2)-> apple1.getWeight().compareTo(apple2.getWeight());
		inventory.sort(c);
		
		System.out.println(inventory);
	}
	
	
	private static List<Apple> filter(List<Apple> inventory, ApplePredicate predicate) {
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory){
			if(predicate.test(apple)){
				result.add(apple);
			}
		}
		return result;
	}
	
	interface ApplePredicate{
		public boolean test(Apple a);
	}

	private static class Apple{

		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
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
			return "Apple{" +
					"color='" + color + '\'' +
					", weight=" + weight +
					'}';
		}
	
	}
}
