package networking;

import java.util.ArrayList;
import java.util.List;

import networking.channels.Channel;
import networking.channels.MainChannel;
import networking.utils.Console;

/**
 * represents a connection to a server.
 * servers will only allow one connection
 * per client.
 */
public abstract class Connection {
	
	private String ip;
	private int port;
	private Console console;
	private MainChannel main;
	
	private Channel next = null;
	
	private List<Channel> channels = new ArrayList<>();
	
	private int uuid = -1;
	
	public Connection(String ip, int port, Console console) {
		console.info("Client is going to start now!");
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		
		this.main = new MainChannel();
		this.main.init(this, console);
		this.main.start();
		
		this.main.waitLoading();
		
		while (uuid == -1)
		try { Thread.sleep(50); }
		catch (InterruptedException e) { e.printStackTrace(); }
		
		console.info("UUID has been verified!");
		
	}
	
	/**
	 * add a channel to this connection
	 */
	public void addChannel(Channel channel) {
		
		channel.init(this, console);
		next = channel;
		sendToServer("channel;"+uuid+";"+channel.getType()+";"+channel.getName());
		
		channel.waitLoading();
		
	}
	
	/**
	 * closes connection
	 */
	public void close() {
		main.stop();
		for (Channel channel : channels) channel.stop();
	}
	
	public Channel getChannel(String name) {
		for (Channel channel : channels) if (channel.getName().equals(name)) return channel;
		return null;
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
		
		String[] args = msg.split(";");
		
		switch (args[0]) {
		
		case "uuid":
			this.uuid = Integer.parseInt(args[1]);
			break;
		
		case "channel":
			if (args[1].equals("accept")) {
				if (next != null) {
					next.start();
					channels.add(next);
					console.debug("Channel was accepted!");
				} else {
					console.error("Channel was denied!");
				}
			}
			break;
		
		}
		
	}
	
	public abstract void lostConnection(ErrorType error);
	
	
}
