package ui;

import ui.tasks.InputRetrievalTask;
import ui.tasks.OutputDisplayTask;

import java.util.List;
import java.util.Scanner;
public
class UIModule
{
	/**
	 * task that reads in user input from the command line and adds
	 * it to a queue
	 */
	private InputRetrievalTask
		mInputRetrievalTask;
	/**
	 * thread hosting the input retrieval task
	 */
	private Thread
		mInputRetrievalThread;
	/**
	 * task that displays out from the output queue
	 */
	private OutputDisplayTask
		mOutputDisplayTask;
	/**
	 * thread hosting the output display task
	 */
	private Thread
		mOutputDisplayThread;
	/**
	 * queue of user input
	 */
	private
	List<String>
		mUserInputQueue;
	/**
	 * queue of output to be displayed
	 */
	private
	List<String>
		mOutputQueue;

	public
	UIModule()
	{
	}

	public
	void setUserInputQueue(
		List<String> userInputQueue
	                      )
	{
		mUserInputQueue
			= userInputQueue;
	}

	public
	void setOutputQueue(
		List<String> outputQueue
	                   )
	{
		mOutputQueue
			= outputQueue;
	}

	public
	void start()
	{
		// create the user input retrieval task
		// and initialize it
		mInputRetrievalTask
			= new InputRetrievalTask();
		mInputRetrievalTask.setScanner( new Scanner( System.in ) );
		mInputRetrievalTask.setInputQueue( mUserInputQueue );
		// create the output display task and initialize it
		mOutputDisplayTask
			= new OutputDisplayTask();
		mOutputDisplayTask.setOutputQueue( mOutputQueue );
		// create the threads that will run the ui.tasks
		mInputRetrievalThread
			= new Thread( mInputRetrievalTask );
		mOutputDisplayThread
			= new Thread( mOutputDisplayTask );
		// start the threads
		mInputRetrievalThread.start();
		mOutputDisplayThread.start();
	}

	public
	void stop()
	{
		mInputRetrievalTask.stop();
		mOutputDisplayTask.stop();
	}
}
