package tcp.networking.channels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class TCPByteChannel extends TCPChannel {

	private InputStream in;
	private OutputStream out;
	private byte[] buffer;
	
	public TCPByteChannel(String name, int size) {
		super(name);
		buffer = new byte[size];
	}
	
	/**
	 * methods
	 */
	
	public void send(byte[] buffer) {
		if (buffer.length!=this.buffer.length) {
			console.warn("Trying to transmit irregular byte array!");
		}
		try {
			out.write(buffer);
			out.flush();
		} catch (IOException e) {
			console.error("IO-Exception while trying to send byte data!");
			e.printStackTrace();
		}
	}

	/**
	 * implemented methods
	 */
	
	@Override
	void createIO() {
		
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			console.debug("Succesfully set up BYTE-Channel!");
			super.ready = true;
			
			while (in.read(buffer) != -1) {
				console.debug("Incoming byte data!");
				incoming(buffer);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void closeIO() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			console.error("Could not close IO!");
		}
	}

	@Override
	public TCPChannelType getType() {
		return TCPChannelType.BYTE;
	}
	
	/**
	 * abstract methods
	 */
	
	protected abstract void incoming(byte[] buffer);

}
