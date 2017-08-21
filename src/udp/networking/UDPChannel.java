package udp.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import utils.ErrorType;

public class UDPChannel {

	private UDPConnection connection;
	private DatagramPacket packet;
	private Thread listener;
	
	/**
	 * controlls recieve and transmit to server
	 * 
	 * @param connection
	 */
	public UDPChannel(UDPConnection connection) {
		this.connection = connection;
		try {
			this.packet = new DatagramPacket(
					new byte[connection.getBufferLength()],
					connection.getBufferLength(),
					InetAddress.getByName(connection.getIp()),
					connection.getPort());
		} catch (UnknownHostException e) {
			connection.lostConnection(ErrorType.UNKNOWN_HOST);
		} finally {
			connection.disconnect();
		}
		
		this.listener = new Thread(() -> {
			
			try {
				while (true) {
					connection.getSocket().receive(packet);
					connection.recieve(new String(packet.getData()));
				}
			} catch (IOException e) {
				connection.lostConnection(ErrorType.IO);
			} finally {
				connection.disconnect();
			}
			
		});
		
		this.listener.start();
		
	}
	
	/**
	 * send message to server
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		try {
			packet.setData(msg.getBytes());
			connection.getSocket().send(packet);
		} catch (IOException e) {
			connection.lostConnection(ErrorType.IO);
		} finally {
			connection.disconnect();
		}
	}

	/**
	 * stop listener
	 */
	public void close() {
		if (listener != null) listener.interrupt();
	}
	
}
