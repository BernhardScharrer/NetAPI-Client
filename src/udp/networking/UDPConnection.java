package udp.networking;

import java.net.DatagramSocket;
import java.net.SocketException;

import utils.Console;
import utils.ErrorType;

public abstract class UDPConnection {
	
	private String ip;
	private int port;
	private int buffer_length;
	private Console console;
	private DatagramSocket socket;
	private UDPChannel channel;
	
	/**
	 * 
	 * constructs a udp connection to server
	 * 
	 * @param ip
	 * @param port
	 * @param buffer_length
	 * @param console
	 */
	public UDPConnection(String ip, int port, int buffer_length, Console console) {
		this.ip = ip;
		this.port = port;
		this.console = console;
		this.buffer_length = buffer_length;
		try {
			this.socket = new DatagramSocket();
			channel = new UDPChannel(this);
		} catch (SocketException e) {
			lostConnection(ErrorType.BIND_EXCEPTION);
		} finally {
			disconnect();
		}
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public int getBufferLength() {
		return buffer_length;
	}

	public Console getConsole() {
		return console;
	}
	
	public DatagramSocket getSocket() {
		return socket;
	}
	
	/**
	 * message from server incoming
	 * 
	 * @param msg
	 */
	public abstract void recieve(String msg);
	
	/**
	 * lost connection
	 * 
	 * @param errortype
	 */
	public abstract void lostConnection(ErrorType error);
	
	/**
	 * send message to server
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		channel.send(msg);
	}
	
	/**
	 * disconnect from server
	 */
	public void disconnect() {
		if (channel != null) channel.close();
		if (socket != null) socket.close();
	}
	
}
