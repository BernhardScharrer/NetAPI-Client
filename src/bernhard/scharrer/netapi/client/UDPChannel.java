package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPChannel {
	
	private final int INTEGER_PACKET = 0;
	private final int FLOAT_PACKET = 1;
	private final int BYTE_SIZE = 4;
	
	private UDPModul udp_modul;
	private Console console;
	private Thread listener;
	
	private int uport;
	private int length;
	private byte[] receive_buffer;
	private boolean started;
	
	private int[] idata;
	private float[] fdata;
	
	private DatagramSocket socket;
	private DatagramPacket receiving_packet;
	private DatagramPacket send_packet;
	
	UDPChannel(UDPModul udp_modul, Console console, String ip, int uport, int length) {
		
		this.udp_modul = udp_modul;
		this.console = console;
		this.length = length;
		this.receive_buffer = new byte[BYTE_SIZE*length+1];
		
		try {
			socket = new DatagramSocket(uport);
			receiving_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
			startListener();
			started = true;
		} catch (SocketException e) {
			
		}
		
	}

	private void startListener() {
		listener = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						socket.receive(receiving_packet);
						System.out.println("Received!");
						receive_buffer = receiving_packet.getData();
						receive();
					}
				} catch (IOException e) {
					console.warn("Stream broke down!");
					cleanUp();
				}				
			}
		});
		
		listener.start();
	}
	
	private synchronized void receive() {
		
		if (receive_buffer[0]==INTEGER_PACKET) {
			
			idata = new int[length];
			for (int i = 0;i<idata.length;i++) {
				idata[i] = converToInt(receive_buffer, 1+i*BYTE_SIZE);
			}
			udp_modul.receive(idata);
			
		} else if (receive_buffer[0]==FLOAT_PACKET) {
			
			fdata = new float[length];
			// TODO generate float array
			udp_modul.receive(fdata);
			
		}
		
	}
	
	void send(int[] buffer) {
		if (started) {
			if (length==buffer.length) {
				try {
					send_packet = new DatagramPacket(generateIntPacket(buffer), BYTE_SIZE*length+1, socket.getRemoteSocketAddress());
					socket.send(send_packet);
				} catch (IOException e) {
					console.warn("Stream broke down!");
					cleanUp();
				}
			} else {
				console.warn("Can't send datagram! (length does not match to: "+length+")");
			}
		} else {
			console.error("Can't send datagrams before binding socket!");
		}
	}
	
	void send(float[] buffer) {
		// TODO
	}
	
	private byte[] generateIntPacket(int[] data) {
		byte[] buffer = new byte[(BYTE_SIZE*length)+1];
		int n = 1;
		buffer[0] = INTEGER_PACKET;
		for (int i : data) {
			convertInt(buffer, n++*BYTE_SIZE, i);
		}
		return buffer;
	}
	
	private void convertInt(byte[] buffer, int start, int value) {
		buffer[start] = (byte) (value >>> 24);
		buffer[start+1] = (byte) (value >>> 16);
		buffer[start+2] = (byte) (value >>> 8);
		buffer[start+3] = (byte) (value);
	}

	private int converToInt(byte[] buffer, int start) {
		return (buffer[start+3] < 0 ? buffer[start+3] + 256 : buffer[start+3])
				+ ((buffer[start+2] < 0 ? buffer[start+2] + 256 : buffer[start+2]) << 8)
				+ ((buffer[start+1] < 0 ? buffer[start+1] + 256 : buffer[start+1]) << 16)
				+ ((buffer[start] < 0 ? buffer[start] + 256 : buffer[start]) << 24);
	}
	
	void cleanUp() {
		
		started = false;
		
		if (listener!=null&&listener.isAlive()) {
			listener.interrupt();
		}
		
		if (socket!= null&&!socket.isClosed()) {
			socket.close();
		}
		
		send_packet = null;
		receiving_packet = null;
		
	}
	
}
