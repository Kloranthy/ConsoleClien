package core;

import execution.ExecutionModule;
import log.LogModule;
import net.NetModule;
import ui.UIModule;

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
	 * constructor
	 */
	public
	ClientApplication()
	{
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// create and initialize the modules
		// execution
		ExecutionModule
			executionModule
			= new ExecutionModule();
		// log
		LogModule
			logModule
			= new LogModule();
		// ui
		UIModule
			uiModule
			= new UIModule();
		// net
		NetModule
			netModule
			= new NetModule();
		// add the modules to the module locator
		moduleLocator.addModule( executionModule );
		moduleLocator.addModule( logModule );
		moduleLocator.addModule( uiModule );
		moduleLocator.addModule( netModule );
		// the application controller
		mApplicationController
			= new ApplicationController( this );
		mApplicationController.setUserInputQueue(
			uiModule.getUserInputQueue()
		                                        );
		mApplicationController.setOutputQueue(
			uiModule.getOutputQueue()
		                                     );
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
	 * starts the application and its modules
	 */
	public
	void startApplication()
	{
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// start the modules
		// execution
		ExecutionModule
			executionModule
			= (ExecutionModule)
			moduleLocator
				.getModule( "execution" );
		executionModule.start();
		// log
		LogModule
			logModule
			= (LogModule) moduleLocator.getModule
			( "log" );
		logModule.start();
		// ui
		UIModule
			uiModule
			= (UIModule) moduleLocator.getModule(
			"ui" );
		uiModule.start();
		// net
		NetModule
			netModule
			= (NetModule) moduleLocator.getModule
			( "net" );
		netModule.start();
		// run the application controller...
		executionModule.execute( mApplicationController );
	}

	/**
	 * stops the application
	 */
	public
	void stopApplication()
	{
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// stop the modules
		moduleLocator.stopAllModules();
	}
}
