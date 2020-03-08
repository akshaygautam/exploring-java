package concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinDemo1 {
	ForkJoinPool commonPool = ForkJoinPool.commonPool();
	DemoRecursiveTask task = new DemoRecursiveTask();
}

class DemoRecursiveTask extends RecursiveAction{

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		
	}
	
}