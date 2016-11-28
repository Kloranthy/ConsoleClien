import net.NetModule;

import java.util.ArrayList;
import java.util.List;
public
class ApplicationController
	implements Runnable
{

	/**
	 * crappy pseudo state variable
	 */
	private boolean
		running;
	/**
	 * the application being controlled
	 */
	private ClientApplication
		mClientApplication;
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
	 * constructor
	 *
	 * @param clientApplication
	 * 	the application to be controlled
	 */
	public
	ApplicationController( ClientApplication clientApplication )
	{
		mClientApplication
			= clientApplication;
	}

	public
	void setUserInputQueue(
		List<String> userInputQueue
	                      )
	{
		mUserInputQueue
			= userInputQueue;
	}

	public
	void setOutputQueue( List<String> outputQueue )
	{
		mOutputQueue
			= outputQueue;
	}

	@Override
	public
	void run()
	{
		running
			= true;
		while ( running )
		{
			if ( mUserInputQueue.isEmpty() )
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
			else
			{
				handleInput(
					mUserInputQueue.remove( 0 )
				           );
			}
		}
	}

	/**
	 * @param input
	 * 	the line of input to be processed
	 */
	public
	void handleInput( String input )
	{
		if ( input == null )
		{
			return;
		}
		input.trim();
		if ( input.isEmpty() )
		{
			return;
		}
		String[]
			inputTokens
			= parseInput( input );
		switch ( inputTokens[ 0 ] )
		{
			case "stop":
				mClientApplication.stopApplication();
				break;
			case "connect":
				if ( inputTokens.length
				     != 3 )
				{
					String
						msg
						= "usage: connect <hostName> <portNumber>";
					mOutputQueue.add( msg );
					return;
				}
				else
				{
					NetModule
						netModule
						= mClientApplication.getNetModule();
					String
						hostName
						= inputTokens[ 1 ];
					int
						portNumber
						= Integer.parseInt( inputTokens[ 2 ] );
					boolean
						connected
						= netModule.connectToServer(
						hostName,
						portNumber
						                           );
					if ( connected )
					{
						String
							msg
							= "successfully connected to server";
						mOutputQueue.add( msg );
					}
					else
					{
						String
							msg
							= "unable to connect to server";
						mOutputQueue.add( msg );
					}
				}
				break;
			default:
				String
					msg
					= "unrecognized command: "
					  + inputTokens[ 0 ];
				mOutputQueue.add( msg );
		}
	}

	private
	String[] parseInput( String input )
	{
		ArrayList<String>
			tokens
			= new ArrayList<String>();
		StringBuilder
			sb
			= new StringBuilder();
		for (
			int
			i
			= 0;
			i
			< input.length();
			i++
			)
		{
			if ( input.charAt( i )
			     == ' ' )
			{
				tokens.add( sb.toString() );
				sb.setLength( 0 );
			}
			else
			{
				sb.append( input.charAt( i ) );
			}
		}
		tokens.add( sb.toString() );
		return tokens.toArray( new String[ tokens.size() ] );
	}

	public
	void stop()
	{
		running
			= false;
	}
}
