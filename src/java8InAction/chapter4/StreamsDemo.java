package java8InAction.chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;



public class StreamsDemo {

	public static void main(String[] args) {
		//getLowCalorieDishJava7(Dish.menu).forEach(System.out::println);
		//getLowCalorieDishJava8(Dish.menu).forEach(System.out::println);
		//getLowCalorieDishJava8Parallel(Dish.menu).forEach(System.out::println);
		List<Dish2> d2 = Dish.menu.parallelStream()
				.map(d->{
					System.out.println(d);
					return StreamsDemo.convertDishToDish2(d);
					}).collect(Collectors.toList());
		d2.stream().forEach(System.out::println);
		
		
	}

	private static List<String> getLowCalorieDishJava8Parallel(List<Dish> menu) {
		return menu.parallelStream()
				.filter(d->d.getCalories() < 400)
				.sorted(Comparator.comparing(Dish::getCalories))
				.map(Dish::getName)
				.collect(Collectors.toList());
	}

	private static List<String> getLowCalorieDishJava8(List<Dish> menu) {
		return menu.stream()
				.filter((d)->d.getCalories() < 400)
				.sorted(Comparator.comparing(Dish::getCalories))
				.map(Dish::getName)
				.collect(Collectors.toList());
	}

	private static List<String> getLowCalorieDishJava7(List<Dish> menu) {
		List<String> lowCalorieDishName = new ArrayList<String>();
		List<Dish> lowCalorieDish = new ArrayList<Dish>();
		for(Dish d: menu) {
			if(d.getCalories() < 400)
				lowCalorieDish.add(d);
		}
		
		Collections.sort(lowCalorieDish, Comparator.comparing(Dish::getCalories));
		for(Dish d: lowCalorieDish) {
			lowCalorieDishName.add(d.getName());
		}
		return lowCalorieDishName;
	}
	
	private static Dish2 convertDishToDish2(Dish d) {
		if(d == null) return null;
		Dish2 dish = new Dish2(d);
		return dish;
	}

	static class Dish2{


	    private final String name;
	    private final boolean vegetarian;
	    private final int calories;
	    private final java8InAction.chapter4.Dish.Type type;

	    public Dish2(Dish d) {
	        this.name = d.getName()+"_Dish2";
	        this.vegetarian = d.isVegetarian();
	        this.calories = d.getCalories();
	        this.type = d.getType();
	    }

	    public String getName() {
	        return name;
	    }

	    public boolean isVegetarian() {
	        return vegetarian;
	    }

	    public int getCalories() {
	        return calories;
	    }

	    public java8InAction.chapter4.Dish.Type  getType() {
	        return type;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }

	    public static final List<Dish> menu =
	            Arrays.asList( new Dish("pork", false, 800, Dish.Type.MEAT),
	                           new Dish("beef", false, 700, Dish.Type.MEAT),
	                           new Dish("chicken", false, 400, Dish.Type.MEAT),
	                           new Dish("french fries", true, 530, Dish.Type.OTHER),
	                           new Dish("rice", true, 350, Dish.Type.OTHER),
	                           new Dish("season fruit", true, 120, Dish.Type.OTHER),
	                           null,
	                           new Dish("pizza", true, 550, Dish.Type.OTHER),
	                           new Dish("prawns", false, 400, Dish.Type.FISH),
	                           new Dish("salmon", false, 450, Dish.Type.FISH));

	}
}
