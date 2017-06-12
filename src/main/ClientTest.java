package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientTest extends JFrame {
	
	public static void main(String[] args) {
		new ClientTest();
	}
	
	public ClientTest() {
		
		super("Client");
		super.setBounds(0, 0, 300, 300);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = (JPanel) super.getContentPane();
		
		JButton connect = new JButton("CONNECT");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (connect.getText().equals("CONNECT")) {
					connect.setText("DISCONNECT");
					Client.connect();
				} else {
					connect.setText("CONNECT");
					Client.disconnect();
				}
				
			}
		});
		panel.add(connect, BorderLayout.SOUTH);
		
		panel.add(new Chat(), BorderLayout.CENTER);
		
		super.setVisible(true);
		
	}

}
