package java8InAction.chapter7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinDemo {
	public static void main(String[] args) {
		long[] l = LongStream.rangeClosed(1, 10_000_000).toArray();
		ForkJoinTask<Long>sum = new ForkJoinSum(l);
		System.out.println(new ForkJoinPool().invoke(sum));
	}
	
	private static class ForkJoinSum extends RecursiveTask<Long>{
		private final long[] numbers;
		private final int start;
		private final int end;
		private static final long THRESHOLD = 10_000;
		
		public ForkJoinSum(long[] numbers) {
			this(numbers, 0, numbers.length);
		}
		
		private ForkJoinSum(long[] numbers, int start, int end) {
			this.start = start;
			this.numbers = numbers;
			this.end = end;
		}
		
		@Override
		protected Long compute() {
			int length = end - start;
			if(length <= THRESHOLD) {
				return computeSequentially();
			}
			System.out.println("Dividing left: "+numbers+" start: "+start+" end: "+start+length/2);
			ForkJoinSum leftTask = new ForkJoinSum(numbers, start, start+length/2);	
			leftTask.fork();
			System.out.println("Dividing right: "+numbers+" start: "+start+length/2+" end: "+end);
			ForkJoinSum rightTask = new ForkJoinSum(numbers, start+length/2, end);
			
			Long rightResult = rightTask.compute();
			Long leftResult = leftTask.join();
			return rightResult+leftResult;
		}

		private Long computeSequentially() {
			long sum = 0;
			for(int i=start; i<end; i++) {
				sum+=numbers[i];
			}
			return sum;
		}
		
	}

}
