package networking.channel;

import java.io.IOException;
import java.net.Socket;

import networking.Connection;

public abstract class Channel {
	
	protected String name;
	protected Socket socket;
	protected Connection con;
	protected int timeout;
	
	/**
	 *
	 * represents an abstract model of a channel
	 * 
	 */
	Channel(String name, Connection con, int timeout) {
		
		this.name = name;
		this.con = con;
		this.timeout = timeout;
		
		try {
			socket = new Socket(con.getIP(), con.getPort());
			socket.setSoTimeout(timeout);
			while (!socket.isBound());
		} catch (IOException e) {
			con.getConsole().error("Could not create connection to: "+con.getIP()+":"+con.getPort());
			e.printStackTrace();
			return;
		}
		
		setup();
	}

	public String getName() {
		return name;
	}
	
	protected abstract void setup();
	protected abstract ChannelType getType();
	
	public abstract void close();
	
}
