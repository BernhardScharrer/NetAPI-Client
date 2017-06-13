package networking;

import networking.channels.Channel;
import networking.utils.Console;

/**
 * represents a connection to a server.
 * servers will only allow one connection
 * per client.
 */
public class Connection {
	
	private String ip;
	private int port;
	private Console console;
	
	public Connection(String ip, int port, Console console) {
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		
		
		
	}
	
	/**
	 * add a channel to this connection
	 */
	public void addChannel(Channel channel) {
		// TODO send on main
		channel.init(this, console);
	}
	
	/**
	 * getters 
	 */
	
	public String getIP() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public Console getConsole() {
		return console;
	}
	
	
	
}
