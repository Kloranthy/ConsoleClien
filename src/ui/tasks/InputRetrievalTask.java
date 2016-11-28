package ui.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public
class InputRetrievalTask
	implements Runnable
{
	/**
	 * mScanner used to retrieve user input from the command line.
	 */
	private Scanner
		mScanner;
	/**
	 * crappy pseudo state
	 */
	private boolean
		mRunning;
	/**
	 * queue containing retrieved user input
	 */
	private
	List<String>
		mInputQueue;


	public
	InputRetrievalTask()
	{
		mInputQueue
			= new LinkedList<String>();
	}

	@Override
	public
	void run()
	{
		mRunning
			= true;
		while ( mRunning )
		{
			try
			{
				String
					input
					= mScanner.nextLine();
				if ( input
				     == null )
				{
					continue;
				}
				else if ( input.isEmpty() )
				{
					continue;
				}
				else
				{
					mInputQueue.add( input );
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
	}

	public
	void stop()
	{
		mRunning
			= false;
	}

	public
	void setScanner( Scanner scanner )
	{
		this.mScanner
			= scanner;
	}

	public
	List<String> getInputQueue()
	{
		return mInputQueue;
	}

	public
	void setInputQueue( List<String> inputQueue )
	{
		this.mInputQueue
			= inputQueue;
	}
}
