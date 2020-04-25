package concurrency.javaCodingProblems;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class ThreadPoolSingleThread {
	private static final int MAX_PROD_TIME_MS=5000;
	private static final int MAX_CONS_TIME_MS=7000;
	private static final int TIMEOUT_MS= MAX_CONS_TIME_MS +1000;
	private static Random rnd = new Random();
	private static volatile boolean runningProducer;
	private static volatile boolean runningConsumer;
	private static final TransferQueue<String> queue = new LinkedTransferQueue<>();
	
	public static void main(String[] args) throws InterruptedException {
		AssembelyLine.startAssembelyLine();
		Thread.sleep(10 * 1000);
		AssembelyLine.stopAssemblyLine();
		AssembelyLine.startAssembelyLine();
		Thread.sleep(10 * 1000);
		AssembelyLine.stopAssemblyLine();
	}
	
	private static Runnable getProducer(){
		return ()->{
			while(runningProducer){
				try {
					String bulb = "bulb-"+rnd.nextInt(1000);
					Thread.sleep(rnd.nextInt(MAX_PROD_TIME_MS));
					boolean transfered = queue.tryTransfer(bulb,TIMEOUT_MS,TimeUnit.MILLISECONDS);
					if(transfered) {
						System.out.println("Checked: "+bulb);
					}
				}catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		};
	}
	
	private static Runnable getConsumer() {
		return ()->{
			while(runningConsumer) {
				try {
					String bulb = queue.poll(MAX_PROD_TIME_MS,TimeUnit.MILLISECONDS);
					if(bulb != null) {
						Thread.sleep(rnd.nextInt(MAX_CONS_TIME_MS));
						System.out.println("Packed: "+bulb);
					}
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		};
	}
	private static class AssembelyLine{
		static ExecutorService producerService;
		static ExecutorService consumerService;
		private AssembelyLine() {
			throw new AssertionError("Only one assembly line is present!");
		}
		
		public static void startAssembelyLine() {
			if(runningConsumer || runningProducer) {
				System.out.println("Assembly line already running");
				return;
			}
			
			System.out.println("Starting assembly line....");
			System.out.println("Remaining bulbs from previous run: "+queue.size());
			Runnable producer = getProducer();
			Runnable consumer = getConsumer();
			
			runningProducer = true;
			producerService = Executors.newSingleThreadExecutor();
			producerService.execute(producer);
			
			
			runningConsumer = true;
			consumerService = Executors.newSingleThreadExecutor();
			consumerService.execute(consumer);
		}
		
		public static void stopAssemblyLine() {
			System.out.println("Stopping assembly line");
			boolean isProducerDown = shutDownProducer();
			boolean isConsumerDown = shutDownConsumer();
			
			if(!isProducerDown || !isConsumerDown) {
				System.out.println("Abnormal stop");
				System.exit(0);
			}
			System.out.println("Assembly line was successfully stopped");
		}

		private static boolean shutDownConsumer() {
			runningConsumer = false;
			return shutDownExecutor(consumerService);
		}

		private static boolean shutDownProducer() {
			runningProducer = false;
			return shutDownExecutor(producerService);
		}

		private static boolean shutDownExecutor(ExecutorService executor) {
			executor.shutdown();
			try {
				if(!executor.awaitTermination(TIMEOUT_MS*2, TimeUnit.MILLISECONDS)) {
					executor.shutdownNow();
					return executor.awaitTermination(TIMEOUT_MS*2, TimeUnit.MILLISECONDS);
				}
				return true;
			}catch(InterruptedException e) {
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
			return false;
		}	
		
	}
}
