package concurrency.javaCodingProblems;

/**
 * A block of code marked with synchronized can be executed by a single thread at a time.
 * A thread can achieve locks at the class level or at the object level
 * @author akshay
 *
 */
public class ObjectVsClassLocks {
	public static void main(String[] args) {
		ObjectVsClassLocks obClassLocks = new ObjectVsClassLocks();
		obClassLocks.runThreads();
		Thread t1 = new Thread(()->{method1(); obClassLocks.method2(); obClassLocks.method3();});
		Thread t2 = new Thread(()->{method1(); obClassLocks.method2(); obClassLocks.method3();});
		t1.setName("ClassLockThread1");
		t2.setName("ClassLockThread2");
		t1.start();
		t2.start();
	}
	
	public void runThreads() {
		ObjectLock oll = new ObjectLock();
		Thread t1 = new Thread(()->{oll.method1(); oll.method2(); oll.method3();});
		Thread t2 = new Thread(()->{oll.method1(); oll.method2(); oll.method3();});
		t1.setName("ObLockThread1");
		t2.setName("ObLockThread2");
		t1.start();
		t2.start();
		
	}
	
	class ObjectLock{
		public synchronized void method1() {
			System.out.println(this.getClass().getSimpleName()+" -Method1 running by: "+ Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void method2() {
			System.out.println(this.getClass().getSimpleName()+" -Method2 running by: "+ Thread.currentThread().getName());
			synchronized(this) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(this.getClass().getSimpleName()+" -Method2 synchronized block running by: "+ Thread.currentThread().getName());
			}
		}
		private final Object olLock = new Object();
		public void method3() {
			System.out.println(this.getClass().getSimpleName()+" -Method3 running by: "+ Thread.currentThread().getName());
			synchronized (olLock) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(this.getClass().getSimpleName()+" -Method3 synchronized block running by: "+ Thread.currentThread().getName());
			}
		}
	}

	/**
	 * CLASS LEVEL LOCK
	 * In order to protect static data, locking at the class level can be achieved by marking a static method/bock
	 * or acquring a lock on the .class reference with synchronized
	 */
	
	//static method
	public static synchronized void method1() {
		System.out.println(ObjectVsClassLocks.class.getSimpleName()+" -Method1 running by: "+ Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//synchronized block on .class
	public void method2() {
		System.out.println(this.getClass().getSimpleName()+" -Method2 running by: "+ Thread.currentThread().getName());
		synchronized(ObjectVsClassLocks.class) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(this.getClass().getSimpleName()+" -Method2 synchronized block running by: "+ Thread.currentThread().getName());
		}
	}
	
	//Synchronized block of code and lock on some other static object
	private final static Object alock = new Object();
	
	public void method3() {
		System.out.println(this.getClass().getSimpleName()+" -Method3 running by: "+ Thread.currentThread().getName());
		synchronized(alock) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(this.getClass().getSimpleName()+" -Method3 synchronized block running by: "+ Thread.currentThread().getName());
		}
	}

	/**
	 * Some common cases that imply synchronization
	 * 1. Two threads can execute concurrently a {synchronized static method and a non-static} method of the same class
	 * This works because the threads acquire locks on different objects. Like in above code method1 is static and method2 and method3
	 * are non static they are running in parallel.
	 * 
	 * 2. Two threads cannot concurrently execute two different {synchronized static methods} of the same class,
	 * this does not work because the first thread acquires a class level lock.
	 * 
	 * 3. Two threads can concurrently execute {non-synchronized, synchronized static and synchronized non-static} 
	 * 
	 * 4. It is safe to call a synchronized method from another synchronized method of the same class that requires same lock.
	 * This works because synchronized is reentrant (as long as the same lock, the lock acquired for first method is used in second method as well)
	 * 
	 *  
	 */
}
