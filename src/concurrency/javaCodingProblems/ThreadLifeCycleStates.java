package concurrency.javaCodingProblems;

/**
 * In order to write thread-safe classes, we can consider the following techniques
 * 1.	Have no state.
 * 2.	Have state, but don't share it.
 * 3.	Have state, but an immutable state.
 * 4.	Use message-passing
 * 5.	Use synchronized blocks
 * 6. 	Use volatile variable
 * 7.	Use data structures from concurrent packages
 * 8.	Use synchronizers (eg: CountDownLatch and barrier)
 * 9.	Use lock form locks package
 * @author aksha
 *
 */
public class ThreadLifeCycleStates {

	public static void main(String[] args) throws Exception {
		newThread();
		runnableThread();
		blockedThread();
		waitingState();
		waitingState2();
		timedWaiting();
		terminatedState();
	}
	
	/**
	 * NEW state of thread is when thread is created only 
	 * @return
	 */
	private static void newThread() {
		Thread thread = new Thread(()->{});
		thread.setName("newStateThread");
		System.out.println("New Thread: "+thread.getName()+" is in: "+thread.getState()+" state");
	}

	/**
	 * Thread is in RUNNABLE is start() method is called.
	 * A thread can be running or ready to run. When it is ready to run, a thread
	 * is waiting for the JVM scheduler to allocate the needed resources and time to run to it.
	 * @return
	 * 
	 * Beacuse of thread scheduler it is not guranteed that Runnable state will get printed
	 */
	private static void runnableThread() {
		Thread thread = new Thread(()->{});
		thread.setName("runnableStateThread");
		thread.start();
		System.out.println("Runnable Thread: "+thread.getName()+" is in: "+thread.getState()+" state");
	}

	/**
	 * When a thread is trying to execute I/O task or sychronized block, it may enter into BLOCKED state
	 * @throws InterruptedException
	 */
	private static void blockedThread() throws InterruptedException {
		Thread t1 = new Thread(new SyncCode());
		Thread t2 = new Thread(new SyncCode());
		t1.start();
		Thread.sleep(2000);
		t2.start();
		Thread.sleep(2000);
		System.out.println("Runnable Thread: "+t1.getName()+" is in: "+t1.getState()+" state");
		System.out.println("Runnable Thread: "+t2.getName()+" is in: "+t2.getState()+" state");
		System.exit(0);
	}
	
	/**
	 * When a thread t1 waits for another thread t2 to finish it is in the WAITING state
	 */
	private static void waitingState() throws Exception{
		new Thread(()->{
			Thread t1 = Thread.currentThread();
			Thread t2 = new Thread(()->{
				try {
					Thread.sleep(2000);
					System.out.println("WaitingThread t1: "+t1.getState());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			t2.start();
			try {
				t2.join(); // T1 will join after t2 is complete
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("T1 running");
		}).start();
	}
	
	/**
	 * When a thread t1 waits for another thread t2 to finish it is in the WAITING state
	 */
	private static void waitingState2() {
		Thread t2 = new Thread(() -> {
			try {
				System.out.println("Running t2");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T2 completes");
		});
		
		Thread t1 = new Thread(() -> {
			System.out.println("T1 running");
			try {
				System.out.println("T1 will join after t2 completes");
				t2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T1 joins again");
		});
		
		t1.start();
		t2.start();
	}
	
	/**
	 * A thread t1, that waits for an explicit period of time for another thread, t2 to finish is in the TIMED_WAITING state
	 * @throws InterruptedException 
	 */
	private static void timedWaiting() throws InterruptedException {
		Thread t1 = new Thread(()->{try {
			System.out.println("Timed waiting t1 starts");
			Thread.sleep(2000);
			System.out.println("Timed waiting t1 completes");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		
		t1.start();
		Thread.sleep(500); // Sleeping current thread to get t1 chance to go in running state
		System.out.println("Timed waiting thread t1: "+t1.getState());
	}
	
	/**
	 * A thread that successfully finishes its job or is abnormally interrupted is is the TERMINATE state
	 * @throws InterruptedException 
	 * 
	 */
	private static void terminatedState() throws InterruptedException {
		Thread t = new Thread(()->{});
		t.start();
		Thread.sleep(500);
		System.out.println("Terminated thread t is in: "+t.getState()+" state");
	}
	

	private static class SyncCode implements Runnable{

		@Override
		public void run() {
			System.out.println("Thread "+Thread.currentThread().getName()+ " is in run() method");
			syncMethod();
		}
		
		public static synchronized void syncMethod() {
			System.out.println("Thread "+ Thread.currentThread().getName()+" is in syncMethod() method");
			while(true) {}
		}
		
	}
}
