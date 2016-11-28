package ui;

import abstractions.Module;
import core.ModuleLocator;
import execution.ExecutionModule;
import ui.tasks.InputRetrievalTask;
import ui.tasks.OutputDisplayTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public
class UIModule
	extends Module
{
	/**
	 * crappy pseudo state variable
	 */
	private boolean
		running;
	/**
	 * task that reads in user input from the command line and adds
	 * it to a queue
	 */
	private InputRetrievalTask
		mInputRetrievalTask;
	/**
	 * task that displays out from the output queue
	 */
	private OutputDisplayTask
		mOutputDisplayTask;

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
		super( "ui" );
		mUserInputQueue
			= new LinkedList<String>();
		mOutputQueue
			= new LinkedList<String>();
	}

	public
	List<String> getUserInputQueue()
	{
		return mUserInputQueue;
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
	List<String> getOutputQueue()
	{
		return mOutputQueue;
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
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// create the user input retrieval task and initialize it
		mInputRetrievalTask
			= new InputRetrievalTask();
		mInputRetrievalTask.setScanner(
			new Scanner( System.in )
		                              );
		mInputRetrievalTask.setInputQueue( mUserInputQueue );
		// create the output display task and initialize it
		mOutputDisplayTask
			= new OutputDisplayTask();
		mOutputDisplayTask.setOutputQueue( mOutputQueue );
		// get the execution module
		ExecutionModule
			executionModule
			= (ExecutionModule) moduleLocator
			.getModule( "execution" );
		// have it run the IO tasks
		executionModule.execute( mInputRetrievalTask );
		executionModule.execute( mOutputDisplayTask );
	}

	public
	void stop()
	{
		mInputRetrievalTask.stop();
		mOutputDisplayTask.stop();
	}


	public
	void display( String output )
	{
		mOutputQueue.add( output );
	}

	/**
	 * retrieves user input in response to a prompt
	 *
	 * @param prompt
	 * 	message displayed to user
	 *
	 * @return user response
	 */
	public
	String requestInput( String prompt )
	{
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// stop the IO tasks for the swap
		mInputRetrievalTask.stop();
		mOutputDisplayTask.stop();
		// retrieve the queues for the tasks
		List<String>
			oldInputQueue
			= mInputRetrievalTask
			.getInputQueue();
		List<String>
			oldOutputQueue
			= mOutputDisplayTask
			.getOutputQueue();
		// create queues for prompt and response
		List<String>
			responseQueue
			= new LinkedList<String>();
		List<String>
			promptQueue
			= new LinkedList<String>();
		promptQueue.add( prompt );
		// give the prompt and response queues to IO tasks
		mInputRetrievalTask.setInputQueue( responseQueue );
		mOutputDisplayTask.setOutputQueue( promptQueue );
		// get the execution module using the module locator
		ExecutionModule
			executionModule
			= (ExecutionModule) moduleLocator
			.getModule( "execution" );
		// use the execution module to run the IO tasks
		executionModule.execute( mInputRetrievalTask );
		executionModule.execute( mOutputDisplayTask );
		// wait for response
		while ( responseQueue.isEmpty() )
		{
			try
			{
				Thread.sleep( 666 );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
		// stop the IO tasks again to swap back old queues
		mInputRetrievalTask.stop();
		mOutputDisplayTask.stop();
		// return the old queues to the IO tasks
		mInputRetrievalTask.setInputQueue( oldInputQueue );
		mOutputDisplayTask.setOutputQueue( oldOutputQueue );
		// use the execution module to run the IO tasks
		executionModule.execute( mInputRetrievalTask );
		executionModule.execute( mOutputDisplayTask );
		// all this for one string...
		return responseQueue.remove( 0 );
	}
}
