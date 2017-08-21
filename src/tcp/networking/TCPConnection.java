package tcp.networking;

import java.util.ArrayList;
import java.util.List;

import tcp.networking.channels.TCPChannel;
import tcp.networking.channels.TCPMainChannel;
import utils.Console;
import utils.ErrorType;

/**
 * represents a connection to a server.
 * servers will only allow one connection
 * per client.
 */
public abstract class TCPConnection {
	
	private String ip;
	private int port;
	private Console console;
	private TCPMainChannel main;
	
	private TCPChannel next = null;
	
	private List<TCPChannel> channels = new ArrayList<>();
	
	private int uuid = -1;
	
	public TCPConnection(String ip, int port, Console console) {
		console.info("Client is going to start now!");
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		
		this.main = new TCPMainChannel();
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
	public void addChannel(TCPChannel channel) {
		
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
		for (TCPChannel channel : channels) channel.stop();
	}
	
	public TCPChannel getChannel(String name) {
		for (TCPChannel channel : channels) if (channel.getName().equals(name)) return channel;
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
