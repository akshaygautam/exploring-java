package java8InAction.chapter7;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ComparisonStreams {

	public static void main(String[] args) {
		System.out.println("Iterative sum is done in: "+measurePerformance(ComparisonStreams::iterativeSum, 10_000_000)+" msecs");
		System.out.println("Sequential sum is done in: "+measurePerformance(ComparisonStreams::sequentialSum, 10_000_000)+" msecs");
		System.out.println("Parallel sum is done in: "+measurePerformance(ComparisonStreams::parallelSum, 10_000_000)+" msecs");
		/**
		 * Reason why parallel is slow are:
		 * 1) iterator generates boxed objects which have to be unboxed before numbers are added // could use LongStream
		 * 2) iterator is difficult to divide into independent chunks to execute in parallel (because output of one function call is 
		 * input of next)
		 * 
		 * Some stream operations are more parallelizable than others
		 */
		
		System.out.println("--------Efficient way---------");
		System.out.println("Sequential sum is done in: "+measurePerformance(ComparisonStreams::sequentialSumEf, 10_000_000)+" msecs");
		System.out.println("Parallel sum is done in: "+measurePerformance(ComparisonStreams::parallelSumEf, 10_000_000)+" msecs");
	}
	
	public static long measurePerformance(Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for(int i=0; i<10; i++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime()-start)/1_000_000;
			System.out.println("Result: "+sum);
			if(duration < fastest) fastest = duration;
		}
		return fastest;
	}
	
	public static long iterativeSum(long n) {
		long result=0;
		for(long i=1L; i<=n; i++) {
			result +=i;
		}
		return result;
	}
	
	public static long sequentialSum(long n) {
		return Stream.iterate(1L, i->i+1)
				.limit(n)
				.reduce(0L, Long::sum);
	}
	
	
	public static long parallelSum(long n) {
		return Stream.iterate(1L, i->i+1)
				.limit(n)
				.parallel()
				.reduce(0L, Long::sum);
	}
	
	public static long sequentialSumEf(long n) {
		return LongStream.rangeClosed(1, n)
//				.reduce(0L, Long::sum);
				.sum();
		}
	
	
	public static long parallelSumEf(long n) {
		return LongStream.rangeClosed(1, n)
				.parallel()
//				.reduce(0L, Long::sum);
				.sum();
	}

}
