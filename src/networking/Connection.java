package networking;

import java.util.ArrayList;
import java.util.List;

import networking.channel.Channel;
import networking.channel.MainChannel;

public class Connection {
	
	private String ip;
	private int port;
	
	private Console console;
	private MainChannel main_channel;
	private List<Channel> channels;
	
	/**
	 *
	 * represents a connection to the server
	 * 
	 * you can try to have multiple connections
	 * the server but the server will only allow one
	 * 
	 */
	public Connection(String ip, int port, Console console) {
		
		console.info("Trying to build connection to: "+ip+":"+port);
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		this.channels = new ArrayList<>();
		
		main_channel = new MainChannel(this);
		
	}

	public String getIP() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	public Console getConsole() {
		return console;
	}
	
	public void addChannel(Channel channel) {
		main_channel.send("channel;"+channel.getType());
		this.channels.add(channel);
	}
	
	public Channel getChannel(String name) {
		for (Channel channel : channels) if (channel.getName().equals(name)) return channel;
		return null;
	}
	
	public void close() {
		
		main_channel.send("disconnect");
		
		for (Channel channel : channels) channel.stop();
		main_channel.close();
	}
	
	public void incoming(String command) {
		
		String[] args = command.split(";");
		
		switch (command) {
		case "disconnect":
			close();
			break;
		}
		
	}
	
}
