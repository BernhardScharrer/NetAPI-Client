package networking.channel;

import networking.Connection;

public class MainChannel extends StringChannel {
	
	public MainChannel(Connection con) {
		super("MAIN", con, 3000);
	}

	@Override
	protected void incoming(String command) {
		con.incoming(command);
	}

}
