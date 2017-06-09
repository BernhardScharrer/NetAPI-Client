package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

abstract class ObjChannel extends Channel implements Listener {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ObjChannel(String name, Connection con, int timeout) {
		super(name, con, timeout);
	}

	@Override
	protected void setup() {
		try {
			
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
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
			e.printStackTrace();
		}
	}
	
	void send(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
