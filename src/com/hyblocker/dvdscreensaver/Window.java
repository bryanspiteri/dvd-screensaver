package com.hyblocker.dvdscreensaver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window implements MouseWheelListener, KeyListener {

	public JFrame app; // Instance of the Window
	public ImageObserver imgobserver;
	public Dimension size = new Dimension(640, 480); // the window's dimensions

	public Window() {

	}

	public void Make() {
		Game.instance.window = this; // Set the window instance to this
		app = new JFrame("DVD Screensaver"); // Create a new Java Window
		Game.instance.WindowWidth = 640; // Set the width
		Game.instance.WindowHeight = 480; // Set the height
		Game.instance.logo = new DVDLogo(); // Create a new DVD logo
		// black background
		app.setBackground(Color.decode("000000"));
		app.add("Center", Game.instance.logo); // Add the logo to the JFrame
		// Set the size of the JFrame
		app.setSize(size);
		app.setMinimumSize(size);
		app.setExtendedState(app.getExtendedState() | JFrame.MAXIMIZED_BOTH); // Load in maximised
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close operation
		Game.instance.logo.addMouseWheelListener(this); // Add a mouse wheel listener to the DVD logo
		app.addKeyListener(this); // Add a keyboard listener to the window
		// show window
		app.pack();
		app.setVisible(true);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation(); // get the mouse wheel rotation in notches
		if (notches < 0) { // if it increased (scroll wheel up)
			Game.instance.logo.speed++; // Increase speed
		} else {
			Game.instance.logo.speed--; // Decrease speed
		}
	}

	public void keyPressed(KeyEvent key) {
		System.out.println(key.getKeyCode());
		if (key.getKeyCode() == 38) { // Up
			Game.instance.logo.speed++; // Increase speed
		}
		if (key.getKeyCode() == 40) { // Down
			Game.instance.logo.speed--; // Decrease speed
		}
	}

	public void keyReleased(KeyEvent key) {

	}

	public void keyTyped(KeyEvent e) {

	}
}
