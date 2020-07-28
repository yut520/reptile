package concurrent.demo.forkjoin.task;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ExtendForkJoinPool extends ForkJoinPool {

	public ExtendForkJoinPool() {
		super();
	}

	public ExtendForkJoinPool(int parallelism) {
		super(parallelism);
	}

	public ExtendForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
		super(parallelism, factory, handler, asyncMode);
	}

	@Override
	public <T> T invoke(ForkJoinTask<T> task) {
		if (task instanceof AbstractTask){
			super.invoke(task);
			return (T) ((AbstractTask) task).getContext();
		} else {
			return super.invoke(task);
		}
	}
}
