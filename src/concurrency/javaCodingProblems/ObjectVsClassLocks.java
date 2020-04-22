package concurrency.javaCodingProblems;

/**
 * A block of code marked with synchronized can be executed by a single thread at a time.
 * A thread can achieve locks at the class level or at the object level
 * @author akshay
 *
 */
public class ObjectVsClassLocks {
	public static void main(String[] args) {
		ObjectVsClassLocks obLocks = new ObjectVsClassLocks();
		obLocks.runThreads();
		Thread t1 = new Thread(()->{method1(); obLocks.method2(); obLocks.method3();});
		Thread t2 = new Thread(()->{method1(); obLocks.method2(); obLocks.method3();});
		 t1.run();
		 t2.run();
	}
	
	public void runThreads() {
		ObjectLock oll = new ObjectLock();
		Thread t1 = new Thread(()->{oll.method1(); oll.method2(); oll.method3();});
		Thread t2 = new Thread(()->{oll.method1(); oll.method2(); oll.method3();});
		t1.run();
		t2.run();
		
	}
	
	class ObjectLock{
		public synchronized void method1() {
			System.out.println(this.getClass().getSimpleName()+" -Method1 running ");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void method2() {
			System.out.println(this.getClass().getSimpleName()+" -Method2 running ");
			synchronized(this) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(this.getClass().getSimpleName()+" -Method2 synchronized block running ");
			}
		}
		private final Object olLock = new Object();
		public void method3() {
			System.out.println(this.getClass().getSimpleName()+" -Method3 running ");
			synchronized (olLock) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(this.getClass().getSimpleName()+" -Method3 synchronized block running ");
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
		System.out.println(ObjectVsClassLocks.class.getSimpleName()+" -Method1 running ");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//synchronized block on .class
	public void method2() {
		System.out.println(this.getClass().getSimpleName()+" -Method2 running ");
		synchronized(ObjectVsClassLocks.class) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(this.getClass().getSimpleName()+" -Method2 synchronized block running ");
		}
	}
	
	//Synchronized block of code and lock on some other static object
	private final static Object alock = new Object();
	
	public void method3() {
		System.out.println(this.getClass().getSimpleName()+" -Method3 running ");
		synchronized(alock) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(this.getClass().getSimpleName()+" -Method3 synchronized block running ");
		}
	}

}
