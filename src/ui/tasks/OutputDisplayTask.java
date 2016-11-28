package ui.tasks;

import java.util.List;
public
class OutputDisplayTask
	implements Runnable
{
	private boolean
		running;
	private
	List<String>
		mOutputQueue;

	@Override
	public
	void run()
	{
		running
			= true;
		while ( running )
		{
			if ( mOutputQueue.isEmpty() )
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
			else
			{
				System.out.println( mOutputQueue.remove( 0 ) );
			}
		}
	}

	public
	void stop()
	{
		running
			= false;
	}

	public
	void setOutputQueue( List<String> outputQueue )
	{
		mOutputQueue
			= outputQueue;
	}
}
