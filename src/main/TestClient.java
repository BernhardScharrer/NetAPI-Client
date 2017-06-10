package main;

import networking.Connection;
import networking.DefaultConsole;
import networking.channel.StringChannel;

public class TestClient {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new DefaultConsole());
		
		con.addChannel(new MyChannel(con));
		
	}
	
	private static class MyChannel extends StringChannel {

		public MyChannel(Connection con) {
			super("Test", con, 1000);
		}

		@Override
		protected void incoming(String string) {
			System.out.println(string);
		}
		
	}
	
}
