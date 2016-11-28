package net.tasks;

import core.ModuleLocator;
import log.LogModule;
import net.NetModule;
import ui.UIModule;

import java.util.List;

public
class LoginTask
	implements Runnable
{

	@Override
	public
	void run()
	{
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		// get needed modules
		NetModule
			netModule
			= (NetModule)
			moduleLocator.getModule( "net" );
		UIModule
			uiModule
			= (UIModule) moduleLocator.getModule
			( "ui" );
		// begin the login sequence
		boolean
			loginComplete
			= false;
		while ( !loginComplete )
		{
			String
				logMessage
				= "starting login sequence";
			LogModule.log(
				"LoginTask",
				logMessage
			             );
			String
				prompt
				= "please enter your account name";
			String
				accountName
				= uiModule.requestInput( prompt );
			accountName
				= accountName.trim();
			while ( accountName.isEmpty() )
			{
				prompt
					= "please enter a valid account name";
				accountName
					= uiModule.requestInput( prompt );
				accountName
					= accountName.trim();
			}
			prompt
				= "please enter your password";
			String
				accountPassword
				= uiModule.requestInput( prompt );
			accountPassword
				= accountPassword.trim();
			while ( accountPassword.isEmpty() )
			{
				prompt
					= "please enter a valid password";
				accountPassword
					= uiModule.requestInput( prompt );
				accountPassword
					= accountPassword.trim();
			}
			netModule.send(
				"LOGIN " + accountName + " " + accountPassword );
			List<String>
				netInQueue
				= netModule.getNetInQueue();
			boolean
				responseReceived
				= false;
			while ( !responseReceived )
			{
				while ( netInQueue.isEmpty() )
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
				// possible problem: unrelated responses are never
				// removed from the queue...
				String
					response
					= netInQueue.remove( 0 );
				switch ( response )
				{
					case "LOGIN_SUCCESS":
						responseReceived
							= true;
						loginComplete
							= true;
						break;
					case "LOGIN_FAILED":
						responseReceived
							= true;
						break;
					default:
						// unrelated response, return it to the queue
						netInQueue.add( response );
				}
			}
		}
		// now what? logged in successfully
	}

	private
	boolean continueLoginAttempt()
	{
		String
			logMessage
			= "continueLoginAttempt called";
		LogModule.log(
			"LoginTask",
			logMessage
		             );
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		UIModule
			uiModule
			= (UIModule) moduleLocator.getModule
			( "ui" );
		String
			prompt
			= "try logging in again?";
		String
			response
			= uiModule.requestInput( prompt );
		response
			= response.trim();
		boolean
			validResponse
			= false;
		while ( !validResponse )
		{
			switch ( response )
			{
				case "yes":
					return true;
				case "no":
					return false;
				default:
					uiModule.display( "this is a yes or no question." );
					response
						= uiModule.requestInput( prompt );
					response
						= response.trim();
			}
		}
		return false; // shouldn't be reached but whatever
	}
}
