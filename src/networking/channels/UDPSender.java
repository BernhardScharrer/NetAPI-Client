package networking.channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import utils.Console;

public class UDPSender {
	
	private Console console;
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Thread sender;
	private String msg = "";
	
	/**
	 * start udp sender to ip:port
	 * 
	 * @param ip
	 * @param port
	 * @param buffer_length
	 * @param console
	 */
	public UDPSender(final String ip, final int port, final int buffer_length, final Console console) {
		
		console.info("Started UDP sender on "+ip+":"+port);
		
		sender = new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					UDPSender.this.socket = new DatagramSocket();
					while (true) {
						UDPSender.this.packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(ip), port);
						UDPSender.this.socket.send(packet);
					}

				} catch (SocketException e) {
					console.error("Can't bind socket to " + ip + ":" + port);
				} catch (UnknownHostException e) {
					console.error("Unknown host: " + ip + ":" + port);
				} catch (IOException e) {
					console.error("IO-Exception occured at " + ip + ":" + port);
				}

			}
		});
		
		sender.start();
	}
	
	/**
	 * write message into buffer
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		console.debug("Send: "+msg);
		this.msg = msg;
	}
	
	/**
	 * stops sending routine and closes socket
	 */
	public void close() {
		if (sender != null) sender.interrupt();
		if (socket != null) socket.close();
	}
	
}