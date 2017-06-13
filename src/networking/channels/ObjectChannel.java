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
		
		try {
			in = new ObjectInputStream(super.socket.getInputStream());
			out = new ObjectOutputStream(super.socket.getOutputStream());
			
			console.debug("Succesfully set up Object-IO!");
			
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
			e.printStackTrace();
		}
		
	}
	
}
