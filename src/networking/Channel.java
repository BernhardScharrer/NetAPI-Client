package networking;

import java.io.IOException;
import java.net.Socket;

public abstract class Channel {
	
	protected String name;
	protected Socket socket;
	protected Connection con;
	protected int timeout;
	
	public Channel(String name, Connection con, int timeout) {
		
		this.name = name;
		this.con = con;
		this.timeout = timeout;
		
		try {
			socket = new Socket(con.getIP(), con.getPort());
			socket.setSoTimeout(timeout);
			while (!socket.isBound());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setup();
	}

	protected abstract void setup();
	
	public abstract void close();

	public String getName() {
		return name;
	}
	
}
