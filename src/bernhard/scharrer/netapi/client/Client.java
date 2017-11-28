package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bernhard.scharrer.netapi.packet.Packet;

public class Client {
	
	private static final int NO_UDP = -1;
	
	private Channel channel;
	private DatagramHandler datagrams;
	private Socket socket;
	private Console console;
	
	Client(String ip, int port, int buffer_length, TrafficListener listener, Console console) {
		
		this.console = console;
		
		try {
			socket = new Socket(ip, port);
			console.debug("Succesfully connected to server!");
			channel = new Channel(this, socket, listener, console);
			if (buffer_length != NO_UDP) datagrams = new DatagramHandler(listener, console, ip, port, buffer_length);
			console.debug("Succesfully created streams!");
		} catch (UnknownHostException e) {
			console.error("Could not resolve host: "+ip);
			cleanUp();
		} catch (IOException e) {
			console.error("Could not build connection to: "+ip);
			cleanUp();
		}
		
	}
	
	public void send(String message) {
		channel.send(message);
	}
	
	public void send(Packet packet) {
		channel.send(packet);
	}
	
	public void send(int[] data) {
		datagrams.send(data);
	}
	
	void cleanUp() {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				console.error("Could not close socket correctly!");
			}
		}
		
		if (channel != null) {
			channel.cleanUp();
		}
		
		if (datagrams != null) {
			datagrams.cleanUp();
		}
		
		System.exit(0);
	}
	
}
