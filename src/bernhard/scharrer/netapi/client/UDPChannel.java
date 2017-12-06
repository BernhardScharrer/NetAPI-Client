package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPChannel {
	
	private final int INTEGER_PACKET = 0;
	private final int FLOAT_PACKET = 1;
	private final int BYTE_SIZE = 4;
	
	private UDPModul udp;
	private Console console;
	private Thread listener;
	
	private int buffer_length;
	private byte[] receive_buffer;
	private boolean started;
	
	private int[] idata;
	private float[] fdata;
	
	private DatagramSocket usocket;
	private DatagramPacket receiving_packet;
	private DatagramPacket send_packet;

	UDPChannel(Client client, UDPModul udp, Console console, String ip, int uport, int buffer, int cuid) {
		this.udp = udp;
		this.console = console;
		
		try {
			this.usocket = new DatagramSocket(uport);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.receive_buffer = new byte[BYTE_SIZE*buffer_length+1];
		this.receiving_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
		
		startListener();
		
		started = true;
		
	}

	private void startListener() {
		listener = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					receiving_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
					console.debug("Starting udp listener.");
					while (true) {
						usocket.receive(receiving_packet);
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
			
			idata = new int[buffer_length];
			for (int i = 0;i<idata.length;i++) {
				idata[i] = converToInt(receive_buffer, 1+i*BYTE_SIZE);
			}
			udp.receive(idata);
			
		} else if (receive_buffer[0]==FLOAT_PACKET) {
			
			fdata = new float[buffer_length];
			// TODO generate float array
			udp.receive(fdata);
			
		}
		
	}
	
	void send(int[] buffer) {
		if (started) {
			if (buffer_length==buffer.length) {
				try {
					send_packet = new DatagramPacket(generateIntPacket(buffer), BYTE_SIZE*buffer_length+1, usocket.getRemoteSocketAddress());
					usocket.send(send_packet);
				} catch (IOException e) {
					console.warn("Stream broke down!");
					cleanUp();
				}
			} else {
				console.warn("Can't send datagram! (length does not match to: "+buffer_length+")");
			}
		} else {
			console.error("Can't send datagrams before binding socket!");
		}
	}
	
	void send(float[] buffer) {
		// TODO
	}
	
	private byte[] generateIntPacket(int[] data) {
		byte[] buffer = new byte[(BYTE_SIZE*buffer_length)+1];
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
		
		if (usocket!= null&&!usocket.isClosed()) {
			usocket.close();
		}
		
		send_packet = null;
		receiving_packet = null;
		
	}
	
}
