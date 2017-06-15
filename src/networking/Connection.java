package networking;

import networking.channels.Channel;
import networking.channels.MainChannel;
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
	private MainChannel main;
	
	public Connection(String ip, int port, Console console) {
		
		console.info("Client is going to start now!");
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		
		this.main = new MainChannel();
		this.main.init(this, console);
		this.main.start();
		
	}
	
	/**
	 * add a channel to this connection
	 */
	public void addChannel(Channel channel) {
		sendToServer("channel;"+channel.getType()+";"+channel.getName());
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
	
	/**
	 * main channel
	 */
	
	private void sendToServer(String msg) {
		main.send(msg);
	}
	
	public void recieveFromServer(String msg) {
		
		
		
	}
	
	
}
