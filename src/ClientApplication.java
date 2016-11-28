import net.NetModule;
import ui.UIModule;

import java.util.LinkedList;
import java.util.List;

/**
 * a simple client prototype
 */
public
class ClientApplication
{
	/**
	 * controller containing the application logic
	 */
	private ApplicationController
		mApplicationController;

	/**
	 * module containing all the ui related variables and
	 * functions
	 */
	private
	UIModule
		mUIModule;

	private NetModule
		mNetModule;

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
	 * constructor
	 */
	public
	ClientApplication()
	{
		// the queues that will connect the modules
		mUserInputQueue
			= new LinkedList<String>();
		mOutputQueue
			= new LinkedList<String>();
		mNetInQueue
			= new LinkedList<String>();
		mNetOutQueue
			= new LinkedList<String>();
		// the modules
		// ui
		mUIModule
			= new UIModule();
		mUIModule.setUserInputQueue( mUserInputQueue );
		mUIModule.setOutputQueue( mOutputQueue );
		// net
		mNetModule
			= new NetModule();
		mNetModule.setNetInQueue( mNetInQueue );
		mNetModule.setNetOutQueue( mNetOutQueue );
		// the application controller
		mApplicationController
			= new ApplicationController( this );
		mApplicationController.setUserInputQueue( mUserInputQueue );
		mApplicationController.setOutputQueue( mOutputQueue );
	}

	public static
	void main( String[] args )
	{
		ClientApplication
			clientApp
			= new ClientApplication();
		clientApp.startApplication();
	}

	/**
	 * starts the application
	 */
	public
	void startApplication()
	{
		mUIModule.start();
		Thread
			thread
			= new Thread( mApplicationController );
		thread.start();
		// initialize variables and launch pre-login task
		// 1st prompt: enter host name and port number to connect to, or cancel
		// 2nd prompt: enter user name and password to login, or cancel
		// 3rd prompt: doing stuff with connection to server
	}

	/**
	 * stops the application
	 */
	public
	void stopApplication()
	{
		mUIModule.stop();
		mApplicationController.stop();
	}

	// TODO replace get module methods with locator
	public
	UIModule getUIModule()
	{
		return mUIModule;
	}

	public
	NetModule getNetModule()
	{
		return mNetModule;
	}
}
