package networking.channel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import networking.Connection;

abstract class ObjChannel extends Channel {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean ready;
	
	public ObjChannel(String name, Connection con) {
		super(name, con);
		start();
	}

	@Override
	protected void setup() {
		
		Object obj = null;
		
		try {
			
			con.getConsole().debug("Trying to setup IO...");
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			con.getConsole().debug("Finished with IO-setup!");
			ready = true;
			
			while ((obj = in.readObject()) != null) {
				recieve(obj);
			}
			
			close();
			
		} catch (IOException e) {
			close();
		} catch (ClassNotFoundException e) {
			super.con.getConsole().error("Incoming object is strange... (" + obj.toString() + ")");
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			if (in!=null) in.close();
			if (out!=null) out.close();
			socket.close();
		} catch (IOException e) {
			con.getConsole().error("Could not close channel! ("+name+")");
			e.printStackTrace();
		}
	}
	
	abstract void recieve(Object obj);
	
	void send(Object object) {
		while(!ready) try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); };
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			con.getConsole().error("Error while sending obj! ("+object.toString()+")");
			e.printStackTrace();
		}
	}
	
}
