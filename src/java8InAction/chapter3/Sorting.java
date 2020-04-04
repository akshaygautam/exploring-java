package java8InAction.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting {

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red")));
        
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, "green"));
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2){
                return a1.getWeight().compareTo(a2.getWeight()); 
        }});
        System.out.println(inventory);
        
        inventory.set(1, new Apple(20, "green"));
        
        inventory.sort((a,b)->a.getWeight().compareTo(b.getWeight()));
        System.out.println(inventory);
        
        inventory.set(2, new Apple(10, "green"));
        
        inventory.sort(Comparator.comparing(Apple::getWeight));
        
        System.out.println(inventory);
	
	}

	private static class AppleComparator implements Comparator<Apple> {
		public int compare(Apple a1, Apple a2) {
			return a1.getWeight().compareTo(a2.getWeight());
		}
	}

	private static class Apple {

		private Integer weight = 0;
		private String color = "";

		public Apple(Integer weight, String color) {
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
