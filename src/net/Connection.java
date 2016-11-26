package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public
class Connection
{
	private Socket
		mSocket;
	private
	DataOutputStream
		mNetOut;
	private
	DataInputStream
		mNetIn;

	public
	Connection( Socket socket )
	{
		mSocket
			= socket;
		try
		{
			mNetOut
				= new DataOutputStream(
					                      socket.getOutputStream()
			);
			mNetIn
				= new DataInputStream(
												socket
				                        .getInputStream()
			);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	public void send(String out)
	{
		try
		{
			mNetOut.writeUTF( out );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	public String receive()
	{
		String in = "";
		try
		{
			in = mNetIn.readUTF();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return in;
	}

	public void close()
	{
		try
		{
			mNetOut.close();
			mNetIn.close();
			mSocket.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
}
