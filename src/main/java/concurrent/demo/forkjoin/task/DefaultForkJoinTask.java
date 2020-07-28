package concurrent.demo.forkjoin.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

/**
 * @author iyut
 */
public class DefaultForkJoinTask<T> extends AbstractTask<T> {

	private List<AbstractTask> taskList;

	public DefaultForkJoinTask(T context) {
		super(context);
		taskList = new ArrayList<>();
	}

	public DefaultForkJoinTask<T> addTask(Task task){
		taskList.add(new AbstractTask<T>(this.context) {
			@Override
			public void load(T context) {
				task.load(context);
			}
		});
		return this;
	}

	@Override
	public void load(Object context) {
		this.taskList.forEach(ForkJoinTask::fork);
	}

	@Override
	public T getContext(){
		this.taskList.forEach(ForkJoinTask::join);
		return this.context;
	}
}
