package networking.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import networking.Connection;

public abstract class ByteChannel extends Channel {
	
	private InputStream in;
	private OutputStream out;
	private byte[] buffer;
	
	public ByteChannel(String name, Connection con, int timeout, int size) {
		super(name, con, timeout);
		this.setup();
	}

	@Override
	protected void setup() {
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			while (in.read(buffer) != -1) {
				incoming(buffer);
			}
			
			con.close();
		} catch (IOException e) {
			super.con.getConsole().error("Could not setup IO!");
			e.printStackTrace();
		}
	}
	
	protected abstract void incoming(byte[] buffer);

	@Override
	protected ChannelType getType() {
		return ChannelType.BYTE;
	}

	@Override
	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			con.getConsole().error("Could not close channel! ("+name+")");
			e.printStackTrace();
		}
	}

}
