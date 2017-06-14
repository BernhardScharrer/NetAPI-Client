package networking.channels;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * represents an object channel
 */
public abstract class ObjectChannel extends Channel {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ObjectChannel(String name) {
		super(name);
	}
	
	/**
	 * implemented methods
	 */
	
	@Override
	void createIO() {
		
		Object obj = null;
		
		try {
			in = new ObjectInputStream(super.socket.getInputStream());
			out = new ObjectOutputStream(super.socket.getOutputStream());
			
			while ((obj = in.readObject()) != null) {
				console.error("Incoming object: " + obj.toString());
				recieve(obj);
			}
			
			console.debug("Succesfully set up Object-IO!");
			
		} catch (IOException e) {
			console.error("IO-Excpetion occured while object was incoming.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void closeIO() {
		
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * abstract methods
	 */
	
	protected abstract void recieve(Object obj);
	
}
