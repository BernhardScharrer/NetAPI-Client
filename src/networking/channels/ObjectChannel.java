package networking.channels;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

import networking.ErrorType;

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
	 * methods
	 */
	
	void send(Object object) {
		try {
			

			console.info("\n-------------------------");
	        console.info("OUT --> " + object.toString());
	        console.info("-------------------------\n");
			
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			console.error("Error while trying to sned object!");
			e.printStackTrace();
		}
	}
	
	/**
	 * implemented methods
	 */
	
	@Override
	void createIO() {
		
		Object obj = null;
		
		try {
			
			console.debug("Start binding IN-Socket...");
			
			in = new ObjectInputStream(super.socket.getInputStream());
			
			console.debug("Finished!");
			console.debug("Start binding OUT-Socket...");
			
			out = new ObjectOutputStream(super.socket.getOutputStream());
			
			console.debug("Finished!");
			
			console.debug("Succesfully set up object stream! ("+super.getName()+")");
			super.ready = true;
			
			while ((obj = in.readObject()) != null) {
		        console.info("\n-------------------------");
		        console.info("IN <-- " + obj.toString());
		        console.info("-------------------------\n");
				recieve(obj);
			}
			
			console.debug("Succesfully set up Object-IO!");
		
		} catch (EOFException e) {
			console.warn("Lost connection to server! (EOF)");
			con.lostConnection(ErrorType.EOF);
			con.close();
		} catch (SocketException e) {
			console.warn("Lost connection to server!");
			con.lostConnection(ErrorType.NO_ERROR);
			con.close();
		} catch (IOException e) {
			console.error("IO-Excpetion occured while object was incoming.");
			con.lostConnection(ErrorType.NO_ERROR);
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void closeIO() {
		
		try {
			if (in!=null) in.close(); else console.warn("IN-Stream was already closed!");
			if (out!=null) out.close(); else console.warn("OUT-Stream was already closed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * abstract methods
	 */
	
	protected abstract void recieve(Object obj);
	
}
