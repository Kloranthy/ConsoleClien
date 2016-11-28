package net.tasks;

import net.Connection;

import java.util.List;
public
class NetReaderTask
	implements Runnable
{

	private boolean
		running;

	private
	Connection
		mConnection;

	private
	List<String>
		mNetInQueue;

	@Override
	public
	void run()
	{
		running
			= true;
		while ( running )
		{
			mNetInQueue.add(
				mConnection.receive()
			               );
		}
	}

	public
	void setNetInQueue( List<String> netInQueue )
	{
		this.mNetInQueue
			= netInQueue;
	}

	public
	void setConnection( Connection connection )
	{
		mConnection
			= connection;
	}

	public
	void stop()
	{
		running
			= false;
	}
}
