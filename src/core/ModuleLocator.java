package core;

import abstractions.Module;
import log.LogModule;

import java.util.HashMap;
public
class ModuleLocator
{
	/**
	 * the sole instance of the module locator.
	 */
	private static ModuleLocator
		mInstance;
	private
	HashMap<String, Module>
		mModules;

	/**
	 * singleton constructor
	 */
	private
	ModuleLocator()
	{
		mModules
			= new HashMap<String, Module>();
	}

	/**
	 * singleton access
	 *
	 * @return the sole instance of the module locator
	 */
	public static
	ModuleLocator getInstance()
	{
		if ( mInstance
		     == null )
		{
			mInstance
				= new ModuleLocator();
		}
		return mInstance;
	}

	public
	void addModule( Module module )
	{
		String
			moduleName
			= module.getName();
		if ( hasModule( moduleName ) )
		{
			String
				logMessage
				= moduleName + " already present";
			LogModule.log(
				"ModuleLocator",
				logMessage
			             );
		}
		else
		{
			mModules.put(
				moduleName,
				module
			            );
		}
	}

	public
	boolean hasModule( String moduleName )
	{
		return mModules.containsKey( moduleName );
	}

	public
	Module getModule( String moduleName )
	{
		if ( hasModule( moduleName ) )
		{
			return mModules.get( moduleName );
		}
		return null;
	}

	public
	void removeModule( String moduleName )
	{
		if ( hasModule( moduleName ) )
		{
			Module
				module
				= mModules.remove( moduleName );
		}
	}

	public
	void stopAllModules()
	{
		for ( Module module : mModules.values() )
		{
			module.stop();
		}
	}
}
