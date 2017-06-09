package networking;

import java.util.ArrayList;
import java.util.List;

public class Connection {
	
	private String ip;
	private int port;
	
	private ConsoleHandler console;
	private List<Channel> channels;
	
	public Connection(String ip, int port, ConsoleHandler console) {
		this.ip = ip;
		this.port = port;
		this.console = console;
		this.channels = new ArrayList<>();
	}

	public String getIP() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	public ConsoleHandler getConsole() {
		return console;
	}
	
	public void addChannel(Channel channel) {
		this.channels.add(channel);
	}
	
	public Channel getChannel(String name) {
		for (Channel channel : channels) if (channel.getName().equals(name)) return channel;
		return null;
	}
	
	public void close() {
		for (Channel channel : channels) channel.close();
	}
	
}
