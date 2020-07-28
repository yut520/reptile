package concurrent.demo.forkjoin.task;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolFactory {

	private int parallelism;

	private ExtendForkJoinPool pool;

	public ForkJoinPoolFactory(){
		this(Runtime.getRuntime().availableProcessors() * 2);
	}

	public ForkJoinPoolFactory(int parallelism) {
		this.parallelism = parallelism;
		this.pool = new ExtendForkJoinPool();
	}

	public ExtendForkJoinPool getPool(){
		return pool;
	}

	public void distory(){
		pool.shutdown();
	}

	@Data
	static class Context{

		public int addAns;

		public int mulAns;

		public String concatAns;

		public Map<String, Object> ans = new ConcurrentHashMap<>();
	}

	public static void main(String[] args) {

		ForkJoinPool pool = new ForkJoinPoolFactory().getPool();
		Context context = new Context();
		DefaultForkJoinTask<Context> loader = new DefaultForkJoinTask<>(context);

		loader.addTask((Task<Context>) x -> {
			context.addAns = 100;
			System.out.println("add thread === " + Thread.currentThread());
		});

		loader.addTask((Task<Context>) x -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			context.mulAns = 50;
			System.out.println("nul thread === " + Thread.currentThread());
		});

		loader.addTask((Task<Context>) x -> {
			context.concatAns = "hello word";
			System.out.println("concst thread === " + Thread.currentThread());
		});

		DefaultForkJoinTask<Context> subLoader = new DefaultForkJoinTask<>(context);

		subLoader.addTask((Task<Context>) context1 -> {
			System.out.println("sub thread1: " + Thread.currentThread() + " | now: " + System.currentTimeMillis());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			context1.ans.put(Thread.currentThread().getName(), System.currentTimeMillis());
		});

		subLoader.addTask((Task<Context>) context12 -> {
			System.out.println("sub thread2: " + Thread.currentThread() + " | now: " + System.currentTimeMillis());
			context12.ans.put(Thread.currentThread().getName(), System.currentTimeMillis());
		});

		loader.addTask(subLoader);
		long start = System.currentTimeMillis();
		System.out.println("------- start: " + start);

		// 提交任务，同步阻塞调用方式
		pool.invoke(loader);

		System.out.println("------- end: " + (System.currentTimeMillis() - start));

		// 输出返回结果，要求3s后输出，所有的结果都设置完毕
		System.out.println("the ans: " + context);
	}
}
