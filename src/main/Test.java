package main;

import networking.Connection;
import networking.ConsoleHandler;
import networking.channel.StringChannel;

public class Test {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new ConsoleHandler() {
			
			@Override
			public void warn(String msg) {
				
			}
			
			@Override
			public void info(String msg) {
				
			}
			
			@Override
			public void error(String msg) {
				System.out.println(msg);
			}
			
			@Override
			public void debug(String msg) {
				
			}
		});
		
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
