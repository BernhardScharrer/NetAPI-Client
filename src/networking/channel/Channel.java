package networking.channel;

import java.io.IOException;
import java.net.Socket;

import networking.Connection;

public abstract class Channel {
	
	protected String name;
	protected Socket socket;
	protected Connection con;
	
	private Thread channel;
	
	/**
	 *
	 * represents an abstract model of a channel
	 * 
	 */
	Channel(String name, Connection con) {
		
		this.name = name;
		this.con = con;
		this.con.prepareChannel(name, getType());
		
		try {
			socket = new Socket(con.getIP(), con.getPort());
			while (!socket.isBound());
		} catch (IOException e) {
			con.getConsole().error("Could not create connection to: "+con.getIP()+":"+con.getPort());
			System.exit(-1);
		}
		
	}

	public void start() {
		channel = new Thread(new Runnable() {
			public void run() {
				setup();
			}
		});
		channel.start();
	}
	
	public void stop() {
		channel.interrupt();
		close();
	}
	
	public String getName() {
		return name;
	}
	
	protected abstract void setup();
	public abstract ChannelType getType();
	
	abstract void close();
	
}
