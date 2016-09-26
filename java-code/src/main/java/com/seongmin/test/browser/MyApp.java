package com.seongmin.test.browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyApp {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		final JTextField urlField = new JTextField("http://centerkey.com");
		JButton webButton = new JButton("Web Trip");
		webButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				BrowserLauncher.openURL(urlField.getText().trim());
				BrowserLauncher.openWebpage(URI.create(urlField.getText().trim()));
			}
		});
		frame.setTitle("Bare Bones Browser Launch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(new JLabel("URL:"));
		panel.add(urlField);
		panel.add(webButton);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
