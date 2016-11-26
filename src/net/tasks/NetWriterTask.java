package net.tasks;

import net.Connection;

import java.util.List;
public
class NetWriterTask
	implements Runnable
{
	private boolean running;
	private
	List<String>
		        mNetOutQueue;
	private
	Connection mConnection;

	@Override
	public
	void run()
	{
		running = true;
		while ( running )
		{
			if( mNetOutQueue.isEmpty())
			{
				try
				{
					Thread.sleep( 1000 );
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
			else
			{
				mConnection.send( mNetOutQueue.remove( 0 ) );
			}
		}
	}

	public void stop()
	{
		running = false;
	}

	public
	void setNetOutQueue( List<String> netOutQueue )
	{
		mNetOutQueue
			= netOutQueue;
	}

	public void setConnection( Connection connection)
	{
		mConnection = connection;
	}
}
