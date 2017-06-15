package test;

import java.util.Scanner;

import networking.Connection;
import networking.DefaultConsole;
import networking.channels.StringChannel;

public class ClientTest {
	
	public static void main(String[] args) {
		
		Connection con = new Connection("localhost", 7777, new DefaultConsole());
		
		MyChannel channel = new MyChannel();
		
		con.addChannel(channel);
		
		Scanner scanner = new Scanner(System.in);
		
		String line = "";
		
		while ((line=scanner.nextLine())!=null) {
			channel.send(line);
		}
		
	}
	
	private static class MyChannel extends StringChannel {

		public MyChannel() {
			super("TEST");
		}

		@Override
		protected void incoming(String msg) {
			System.out.println("IN: " + msg);
		}
		
	}
	
}
