package concurrency.javaCodingProblems;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Thread pool is a collection of threads that can be used to execute tasks.
 * A thread pool is responsible for managing the creation, allocation and life cycles of its thread
 * and contributing to better performance
 * @author akshay
 *
 */
public class ThreadPools {

	public static void main(String[] args) throws Exception {
		ThreadPools demo = new ThreadPools();
		demo.runSimpleExecutor();
		demo.runThreadPoolExecutor();
	}
	
	/**
	 * Simple Executor
	 */
	public void runSimpleExecutor() {
		SimpleExecutor se = new SimpleExecutor();
		se.execute(()->{System.out.println("Running simple executor");});
	}
	
	class SimpleExecutor implements Executor{

		@Override
		public void execute(Runnable command) {
			(new Thread(command)).start();
		}
		
	}

	/**
	 * ThreadPoolExecutor
	 * A more complex and comprehensive interface that provides many additional methods is ExecutorService.
	 * This is an enriched version of Executor.
	 * 
	 * @throws InterruptedException 
	 */
	private void runThreadPoolExecutor() throws InterruptedException {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(5);
		final AtomicInteger counter = new AtomicInteger();
		
		ThreadFactory threadFactory = (runnable) ->{
			System.out.println("Creating a new Cool-Thread-"+counter.incrementAndGet());
			return new Thread(runnable, "Cool-Thread-"+counter.get());
		};
		
		RejectedExecutionHandler rejectedExecutionHandler = (runnable,executor)->{
			if(runnable instanceof SimpleThreadPoolExecutor) {
				SimpleThreadPoolExecutor task = (SimpleThreadPoolExecutor) runnable;
				System.out.println("Rejecting task: "+task.taskId);
			}
		};
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS, queue, threadFactory, rejectedExecutionHandler);
		
		IntStream.rangeClosed(1, 50).forEach((i)->executor.execute(new SimpleThreadPoolExecutor(i)));
		executor.shutdown();
		executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
				
	}
	class SimpleThreadPoolExecutor implements Runnable{

		private final int taskId;
		
		public SimpleThreadPoolExecutor(int taskId) {
			this.taskId = taskId;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Executing task "+taskId+" via "+Thread.currentThread().getName());
		}
		
	}

	/**
	 * ScheduledExecutorService is an ExecutorService that can schedule tasks for execution
	 * after a given delay, or execute periodically.
	 * 
	 * Thread pools via executors
	 * 
	 * Executors.newSingleThreadExecutor(): This pool manages only one thread with an unbound queue, which only executes one task
	 * at a time.
	 * 
	 * Executors.newCachedThreadPool(): This thread pool that creates new threads and remove idle threads as they are needed, the core
	 * pool size is 0 and the maximum pool size is Integer.MAX_VALUE.
	 * 
	 * Executors.newFixedThreadPool(5): This is a thread pool with fixed number of threads and an unbounded queue, which creates the effect of an
	 * infinite timeout.
	 * 
	 * Executors.newWorkStealPool(): This thread pool is based on a work-stealing algo.
	 * 
	 * Executors.newScheduledThreadPool(5): A thread pool that can schedule commands to run after a given delay, or to execute periodically
	 */

}
