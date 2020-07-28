package concurrent.demo.forkjoin.task;

import java.util.concurrent.RecursiveAction;

/**
 * @author iyut
 */
public abstract class AbstractTask<T> extends RecursiveAction implements Task<T> {

	/**
	 * 这里就是用来保存返回的结果，由业务防自己在实现的load()方法中写入数据
	 */
	protected T context;

	public AbstractTask(T context){
		this.context = context;
	}

	@Override
	protected void compute() {
		load(context);
	}

	public T getContext(){
		this.join();
		return context;
	}

}
