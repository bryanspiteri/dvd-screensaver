package com.hyblocker.dvdscreensaver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class InfoWindow {
	
	public JFrame window; // Store the window as an instance so that we can access it later
	public JTextArea info; // Store the label as an instance so that we can access it later

	public InfoWindow() {

	}

	public void InitWindow() {
		Dimension size = new Dimension(240, 110); // Declare the size
		window = new JFrame("Info"); // Declare the window
		info = new JTextArea(); // Declare the info
		info.setEditable(false); // Read only
		// set the font of "info" to "Consolas" at size 11
		Font gameFont = new Font("Consolas", 0, 14);
		gameFont = gameFont.deriveFont(Font.PLAIN, 11);
		info.setFont(gameFont);
		// Add "info"
		window.add(info, BorderLayout.NORTH);
		// Set the size
		window.setSize(size);
		window.setMinimumSize(size);
		window.setResizable(false);
		// Display the JFrame
		window.pack();
		window.setVisible(true);
	}

	public void OnTick() {
		// Update the text
		info.setText("                INFO\nSpeed: " + Game.instance.logo.speed + "      (Use the mouse scroll\nHits: "
				+ Game.instance.logo.hits + "           wheel and arrow keys\nCorner Hits: "
				+ Game.instance.logo.cornerhits + "      to change speed!)");
	}
}
