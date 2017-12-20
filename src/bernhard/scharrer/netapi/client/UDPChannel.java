package bernhard.scharrer.netapi.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPChannel {
	
	private final int INTEGER_PACKET = 0;
	private final int FLOAT_PACKET = 1;
	private final int BYTE_SIZE = 4;
	private final int OFFSET = 4;
	
	private UDPModul udp;
	private Console console;
	private Thread listener;
	
	private int buffer;
	private byte[] receive_buffer;
	private boolean started;
	
	private int[] idata;
	private float[] fdata;
	
	private InetAddress server;
	private DatagramSocket socket;
	private DatagramPacket receiving_packet;
	private DatagramPacket send_packet;
	private int uport;

	UDPChannel(Client client, UDPModul udp, Console console, String ip, int uport, int buffer, int cuid) {
		
		try {
			
			this.udp = udp;
			this.uport = uport;
			this.buffer = buffer;
			this.console = console;
			this.receive_buffer = new byte[BYTE_SIZE*buffer+OFFSET];
			this.socket = new DatagramSocket();
			this.receiving_packet = new DatagramPacket(receive_buffer, receive_buffer.length);
			this.server = InetAddress.getByName(ip);
			
			startListener();
			
			started = true;
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	private void startListener() {
		listener = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					console.debug("Starting udp listener.");
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
			
			idata = new int[buffer];
			for (int i = 0;i<idata.length;i++) {
				idata[i] = converToInt(receive_buffer, OFFSET+i*BYTE_SIZE);
			}
			udp.receive(idata);
			
		} else if (receive_buffer[0]==FLOAT_PACKET) {
			
			fdata = new float[buffer];
			// TODO generate float array
			udp.receive(fdata);
			
		}
		
	}
	
	void send(int[] data) {
		if (started) {
			if (buffer==data.length) {
				try {
					send_packet = new DatagramPacket(generateIntPacket(data), BYTE_SIZE*buffer+OFFSET, server, uport);
					socket.send(send_packet);
					System.out.println("Sended!");
				} catch (IOException e) {
					console.warn("Stream broke down!");
					cleanUp();
				}
			} else {
				console.warn("Can't send datagram! (length does not match to: "+buffer+")");
			}
		} else {
			console.error("Can't send datagrams before binding socket!");
		}
	}
	
	void send(float[] buffer) {
		// TODO
	}
	
	private byte[] generateIntPacket(int[] data) {
		byte[] datagram = new byte[(BYTE_SIZE*buffer)+OFFSET];
		int n = 0;
		datagram[0] = INTEGER_PACKET;
		for (int i : data) {
			convertInt(datagram, n++*BYTE_SIZE+OFFSET, i);
		}
		return datagram;
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
