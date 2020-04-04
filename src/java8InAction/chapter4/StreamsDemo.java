package java8InAction.chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class StreamsDemo {

	public static void main(String[] args) {
		getLowCalorieDishJava7(Dish.menu).forEach(System.out::println);
		getLowCalorieDishJava8(Dish.menu).forEach(System.out::println);
		getLowCalorieDishJava8Parallel(Dish.menu).forEach(System.out::println);
		
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

}
