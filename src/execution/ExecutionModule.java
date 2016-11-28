package execution;

import abstractions.Module;
import log.LogModule;

/**
 * a simple execution module that will be used to contain all
 * things thread related.
 * <p>
 * this will be abstracted and replaced with something better at a
 * later date.
 */
public
class ExecutionModule
	extends Module
{
	/**
	 * crappy pseudo state variable
	 */
	private boolean
		running;

	public
	ExecutionModule()
	{
		super( "execution" );
		String
			logMessage
			= "constructor called";
		LogModule.log(
			getName(),
			logMessage );
	}

	public
	void execute( Runnable task )
	{
		// TODO: check if running? might interfere with shutdown
		new Thread( task ).start();
	}

	@Override
	public
	void start()
	{
		running
			= true;
	}

	@Override
	public
	void stop()
	{
		running
			= false;
	}
}
