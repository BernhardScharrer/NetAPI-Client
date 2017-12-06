package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bernhard.scharrer.netapi.packet.Packet;

public class Client {

	private TCPChannel tcp_channel;
	private UDPChannel udp_channel;
	
	private Socket socket;
	
	private String ip;
	private int port;
	
	private Console console;
	private TCPModul tcp;
	private UDPModul udp;
	
	private boolean initialized;
	private boolean started;
	private boolean closed;
	private boolean headline;
	
	Client(String ip, int port, TCPModul tcp, UDPModul udp, Console console) {
		
		this.ip = ip;
		this.port = port;
		this.console = console;
		this.tcp = tcp;
		this.udp = udp;
		initialized = true;
		
	}
	
	void initUDP(int uport, int buffer, int cuid) {
		console.debug("UDP modul has been initialized.");
		this.udp_channel = new UDPChannel(this, udp, console, ip, uport, buffer, cuid);
	}
	
	public void start() {
		
		try {

			socket = new Socket(ip, port);
			
			console.debug("Succesfully bound to tcp socket!");
			tcp_channel = new TCPChannel(this, socket, tcp, console);
			
			console.debug("Succesfully created tcp streams!");
			
			if (udp!=null) {
				send("\r\r\r");
				console.debug("Requesting udp informations from server.");
			} else {
				console.debug("Starting without udp modul.");
			}
			
			started = true;
			
			if (headline) printHeadline();
			
		} catch (UnknownHostException e) {
			console.error("Could not resolve host: "+ip);
			cleanUp();
		} catch (IOException e) {
			console.error("Could not build connection to: "+ip);
			cleanUp();
		}
		
	}
	
	public void setDebugging(boolean debugging) {
		this.console.setDebugging(debugging);
	}
	
	public void setHeadlineVisible(boolean headline) {
		this.headline = headline;
	}
	
	public void send(String message) {
		if (tcp_channel!=null) tcp_channel.send(message);
	}
	
	public void send(Packet packet) {
		if (tcp_channel!=null) tcp_channel.send(packet);
	}
	
	public void send(int[] data) {
		if (udp_channel!=null) {
			udp_channel.send(data);
		} else {
			console.warn("Bind UDP Modul first before sending datagrams.");
		}
	}
	
	public void send(float[] data) {
		if (udp_channel!=null) {
			udp_channel.send(data);
		} else {
			console.warn("Bind UDP Modul first before sending datagrams.");
		}
	}
	
	public Console getConsole() {
		return console;
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isClosed() {
		return closed;
	}

	void cleanUp() {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				console.error("Could not close socket correctly!");
			}
		}
		
		if (tcp_channel != null) {
			tcp_channel.cleanUp();
		}
		
		if (udp_channel != null) {
			udp_channel.cleanUp();
		}
		
		closed = true;
	}
	
	private static void printHeadline() {
		System.out.println("\n ____  _____        _        _       _______  _____  ");
		System.out.println("|_   \\|_   _|      / |_     / \\     |_   __ \\|_   _| ");
		System.out.println("  |   \\ | |  .---.`| |-'   / _ \\      | |__) | | |   ");
		System.out.println("  | |\\ \\| | / /__\\\\| |    / ___ \\     |  ___/  | |   ");
		System.out.println(" _| |_\\   |_| \\__.,| |, _/ /   \\ \\_  _| |_    _| |_  ");
		System.out.println("|_____|\\____|'.__.'\\__/|____| |____||_____|  |_____| ");
		System.out.println("");
		System.out.println("Written by Bernhard Scharrer\n");
	}
	
}
