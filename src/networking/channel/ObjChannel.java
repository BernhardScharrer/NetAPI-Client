package networking.channel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import networking.Connection;

abstract class ObjChannel extends Channel {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ObjChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}

	@Override
	protected void setup() {
		
		Object obj = null;
		
		try {
			
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			while ((obj = in.readObject()) != null) {
				recieve(obj);
			}
			
			close();
			
		} catch (IOException e) {
			super.con.getConsole().error("Could not setup IO!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			super.con.getConsole().error("Incoming object is strange... (" + obj.toString() + ")");
			e.printStackTrace();
		}
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
	
	abstract void recieve(Object obj);
	
	void send(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			con.getConsole().error("Error while sending obj! ("+object.toString()+")");
			e.printStackTrace();
		}
	}
	
}
