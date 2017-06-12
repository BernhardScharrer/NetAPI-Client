package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Chat extends JPanel {
	
	private JTextArea area;

	public Chat() {
		super();
		super.setLayout(new BorderLayout());
		
		area = new JTextArea();
		super.add(area, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel(new GridLayout(1, 2));
		JButton send = new JButton("SEND");
		JButton clear = new JButton("CLEAR");
		buttons.add(send);
		buttons.add(clear);
		
		super.add(buttons, BorderLayout.SOUTH);
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("");
			}
		});
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.send(area.getText());
			}
		});
		
	}

}
