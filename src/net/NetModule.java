package net;

import abstractions.Module;
import core.ModuleLocator;
import execution.ExecutionModule;
import log.LogModule;
import net.tasks.LoginTask;
import net.tasks.NetReaderTask;
import net.tasks.NetWriterTask;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
public
class NetModule
	extends Module
{

	/**
	 * crappy pseudo state variable
	 */
	private boolean
		running;
	/**
	 * task that receives input over net
	 */
	private
	NetReaderTask
		mNetReaderTask;
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
		super( "net" );
		String
			logMessage
			= "constructor called";
		LogModule.log(
			getName(),
			logMessage
		             );
		mNetInQueue
			= new LinkedList<>();
		mNetOutQueue
			= new LinkedList<>();
	}

	public
	List<String> getNetInQueue()
	{
		return mNetInQueue;
	}

	public
	void setNetInQueue( List<String> netInQueue )
	{
		mNetInQueue
			= netInQueue;
	}

	public
	List<String> getNetOutQueue()
	{
		return mNetOutQueue;
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
		String
			logMessage
			= "isConnected called";
		LogModule.log(
			getName(),
			logMessage );
		return mConnection
		       != null;
	}

	public
	boolean connectToServer(
		String hostName,
		int portNumber
	                       )
	{
		String
			logMessage
			= "connectToServer called";
		LogModule.log(
			getName(),
			logMessage );
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
			logMessage
				= "encountered an exception in connectToServer";
			LogModule.log(
				getName(),
				logMessage );
			e.printStackTrace();
		}
		return false;
	}

	public
	void login()
	{
		String
			logMessage
			= "login called";
		LogModule.log(
			getName(),
			logMessage
		             );
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		LoginTask
			loginTask
			= new LoginTask();
		ExecutionModule
			executionModule
			= (ExecutionModule)
			moduleLocator.getModule( "execution" );
		executionModule.execute( loginTask );
	}

	public
	void disconnectFromServer()
	{
		String
			logMessage
			= "disconnectFromServer called";
		LogModule.log(
			getName(),
			logMessage
		             );
		send( "DISCONNECTING" );
		stopConnectionIOTasks();
		mConnection.close();
		mConnection
			= null;
	}

	public
	void send( String out )
	{
		String
			logMessage
			= "send called";
		LogModule.log(
			getName(),
			logMessage
		             );
		mNetOutQueue.add( out );
	}

	/**
	 * stops the io tasks, allowing the threads hosting them to
	 * finish
	 */
	private
	void stopConnectionIOTasks()
	{
		String
			logMessage
			= "stopConnectionIOTasks called";
		LogModule.log(
			getName(),
			logMessage );
		mNetReaderTask.stop();
		mNetWriterTask.stop();
	}

	private
	void startConnectionIOTasks()
	{
		String
			logMessage
			= "startConnectionIOTasks called";
		LogModule.log(
			getName(),
			logMessage );
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
	}

	@Override
	public
	void start()
	{
		String
			logMessage
			= "start called";
		LogModule.log(
			getName(),
			logMessage
		             );
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		ExecutionModule
			executionModule
			= (ExecutionModule)
			moduleLocator.getModule( "execution" );
		executionModule.execute( mNetReaderTask );
		executionModule.execute( mNetWriterTask );
	}

	@Override
	public
	void stop()
	{
		String
			logMessage
			= "stop called";
		LogModule.log(
			getName(),
			logMessage
		             );
		if ( mNetReaderTask != null )
		{
			mNetReaderTask.stop();
		}
		if ( mNetWriterTask != null )
		{
			mNetWriterTask.stop();
		}

		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		moduleLocator.removeModule( "net" );
	}
}
