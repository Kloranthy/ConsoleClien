package log;

import abstractions.Module;
import core.ModuleLocator;
import ui.UIModule;
public
class LogModule
	extends Module
{
	public
	LogModule()
	{
		super( "log" );
	}

	public static
	void log(
		String source,
		String logMessage
	        )
	{
		long
			time
			= System.currentTimeMillis();
		ModuleLocator
			moduleLocator
			= ModuleLocator.getInstance();
		if ( moduleLocator.hasModule( "ui" ) )
		{
			UIModule
				uiModule
				= (UIModule) moduleLocator.getModule(
				"ui" );
			uiModule.display(
				time + " " + source + " " + logMessage );
		}
	}

	@Override
	public
	void start()
	{
		// not needed?
	}

	@Override
	public
	void stop()
	{
		// not needed?
	}
}
