package abstractions;

public abstract
class Module
{
	private final String
		mName;

	public
	Module( String name )
	{
		mName
			= name;
	}

	public
	String getName()
	{
		return mName;
	}

	public abstract
	void start();

	public abstract
	void stop();
}
