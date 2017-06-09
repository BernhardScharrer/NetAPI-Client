package networking.channel;

import java.io.IOException;
import java.net.Socket;

import networking.Connection;

public abstract class Channel {
	
	protected String name;
	protected Socket socket;
	protected Connection con;
	protected int timeout;
	
	Channel(String name, Connection con, int timeout) {
		
		this.name = name;
		this.con = con;
		this.timeout = timeout;
		
		try {
			socket = new Socket(con.getIP(), con.getPort());
			socket.setSoTimeout(timeout);
			while (!socket.isBound());
		} catch (IOException e) {
			con.getConsole().error("Could establish connection to: "+con.getIP()+":"+con.getPort());
			e.printStackTrace();
			return;
		}
		
		setup();
	}

	protected abstract void setup();
	
	public abstract void close();

	public String getName() {
		return name;
	}
	
}
