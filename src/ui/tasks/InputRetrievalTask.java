package ui.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public
class InputRetrievalTask
	implements Runnable
{
	/**
	 * scanner used to retrieve user input from
	 * the command line.
	 */
	private Scanner
		scanner;
	private boolean
		running;
	private
	List<String>
		inputQueue;


	public
	InputRetrievalTask()
	{
		inputQueue
			= new LinkedList<String>();
	}

	@Override
	public
	void run()
	{
		running
			= true;
		while ( running )
		{
			if ( scanner.hasNext() )
			{
				inputQueue.add( scanner.nextLine() );
			}
		}
	}

	public void stop()
	{
		running = false;
	}

	public
	void setScanner( Scanner scanner )
	{
		this.scanner
			= scanner;
	}

	public
	void setInputQueue( List<String> inputQueue )
	{
		this.inputQueue
			= inputQueue;
	}
}
