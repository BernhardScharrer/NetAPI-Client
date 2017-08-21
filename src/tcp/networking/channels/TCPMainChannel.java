package tcp.networking.channels;

/**
 * 
 * represents the main channel of a connection.
 * this channel is used to inform the server about very
 * important messages like "disconnect" or "new channel".
 * 
 * you can't acces this channel through your connection.
 * if you want to make your own string channel than add
 * a new one to the connection.
 * 
 */
public class TCPMainChannel extends TCPStringChannel {

	public TCPMainChannel() {
		super("MAIN");
	}

	@Override
	protected void incoming(String msg) {
		super.con.recieveFromServer(msg);
	}

}
