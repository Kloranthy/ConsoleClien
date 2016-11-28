package net;

import net.tasks.NetReaderTask;
import net.tasks.NetWriterTask;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
public
class NetModule
{
	private
	NetReaderTask
		mNetReaderTask;
	/**
	 * thread hosting the net input reader task
	 */
	private Thread
		mNetInThread;
	/**
	 * task that sends output over net
	 */
	private
	NetWriterTask
		mNetWriterTask;

	/**
	 * queue of received network input
	 */
	private
	List<String>
		mNetInQueue;
	/**
	 * thread hosting the net output task
	 */
	private Thread
		mNetOutThread;
	/**
	 * queue of output to be sent
	 */
	private
	List<String>
		mNetOutQueue;
	/**
	 * the client's connection to the server
	 */
	private Connection
		mConnection;

	public
	NetModule()
	{
	}


	public
	void setNetInQueue( List<String> netInQueue )
	{
		mNetInQueue
			= netInQueue;
	}

	public
	void setNetOutQueue( List<String> netOutQueue )
	{
		mNetOutQueue
			= netOutQueue;
	}

	public
	boolean isConnected()
	{
		return mConnection
		       != null;
	}

	public
	boolean connectToServer(
		String hostName,
		int portNumber
	                       )
	{
		try
		{
			mConnection
				= new Connection(
				new Socket(
					hostName,
					portNumber
				)
			);
			return true;
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * TODO replace this with Brian's fancy auth stuff
	 *
	 * @param userName
	 * @param password
	 */
	public
	void login(
		String userName,
		String password
	          )
	{
		// login task goes here
	}

	public
	void disconnectFromServer()
	{
		// TODO tell server that client is disconnecting
		stopConnectionTasks();
		mConnection.close();
		mConnection
			= null;
	}

	private
	void stopConnectionTasks()
	{
		mNetReaderTask.stop();
		mNetWriterTask.stop();
	}

	private
	void startConnectionTasks()
	{
		// create the net reader task and initialize it
		mNetReaderTask
			= new NetReaderTask();
		mNetReaderTask.setNetInQueue( mNetInQueue );
		mNetReaderTask.setConnection( mConnection );
		// create the net writer task and initialize it
		mNetWriterTask
			= new NetWriterTask();
		mNetWriterTask.setNetOutQueue( mNetOutQueue );
		mNetWriterTask.setConnection( mConnection );
		// create the threads that will run the ui.tasks
		mNetInThread
			= new Thread( mNetReaderTask );
		mNetOutThread
			= new Thread( mNetWriterTask );
		// start the threads
		mNetInThread.start();
		mNetOutThread.start();
	}
}
