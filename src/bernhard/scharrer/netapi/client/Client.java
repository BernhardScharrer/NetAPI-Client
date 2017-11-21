package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bernhard.scharrer.netapi.packet.Packet;

public class Client {
	
	private Channel channel;
	private Socket socket;
	private Console console;
	
	Client(String ip, int port, TrafficListener listener, Console console) {
		
		this.console = console;
		
		try {
			socket = new Socket(ip, port);
			console.debug("Succesfully connected to server!");
			channel = new Channel(this, socket, listener, console);
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
		System.exit(0);
	}
	
}
