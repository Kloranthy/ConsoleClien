package execution;

import java.util.LinkedList;
import java.util.List;
/**
 * a simple execution module that will be used to contain all
 * things thread related.
 * <p>
 * this will be abstracted and replaced with something better at a
 * later date.
 */
public
class ExecutionModule
{
	private List<Runnable>
		mTasks;
	private List<Thread>
		mThreads;

	public
	ExecutionModule()
	{
		mTasks
			= new LinkedList<Runnable>();
		mThreads
			= new LinkedList<Thread>();
	}

	public
	void execute( Runnable task )
	{
		mTasks.add( task );
		Thread
			thread
			= new Thread( task );
		mThreads.add( thread );
		thread.start();
	}
}
