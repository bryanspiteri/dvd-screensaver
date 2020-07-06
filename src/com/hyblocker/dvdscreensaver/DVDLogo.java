package com.hyblocker.dvdscreensaver;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

public class DVDLogo extends Component {

	public String LogoURL = "https://i.imgur.com/n88wvXA.png"; // The URL to the DVD logo

	boolean doFreezeOnCornerHit = false; // Controls whether the screensaver freezes for a bit everytime it hits the corner

	// Store the image component of the logo.
	BufferedImage dvdImg;

	// Specify the direction of the logo
	public short xDir = 1, yDir = 1;
	// Store the position of the logo
	public int x = 0, y = 0;

	// ಠ_ಠ
	public int speed = 1;
	public int hits = 0, cornerhits = 0;

	// width and height of the logo
	int width, height;

	// current color in colors[]
	int colorIndex = 0;

	// instance of random (for unique numbers everytime)
	Random random = new Random();

	// Store all colors in an array
	Color[] colors = {
		Color.decode("#FFFFFF"),
		Color.decode("#FF0000"),
		Color.decode("#FF6A00"),
		Color.decode("#FFD800"),
		Color.decode("#B6FF00"),
		Color.decode("#4CFF00"),
		Color.decode("#00FF21"),
		Color.decode("#00FF90"),
		Color.decode("#0094FF"),
		Color.decode("#0026FF"),
		Color.decode("#4800FF"),
		Color.decode("#B200FF"),
		Color.decode("#FF00DC"),
		Color.decode("#FF006E"),
	};

	public DVDLogo() {
		init();
	}

	public void init() {
		loadImage();
	}

	public void loadImage() {
		try {
			URL imageSrc = new URL(LogoURL);
			try {
				InputStream logoData; // Contains the raw logo bytes
				String appdata = System.getenv("APPDATA"); // Get the folder for appdata
				File dvdImgLocal = new File(appdata + "\\dvd.png"); // create a file object that is referencing the
																	// cached
				if (dvdImgLocal.exists()) { // if there's a cached version of the logo
					dvdImg = ImageIO.read(dvdImgLocal); // load it
				} else {
					logoData = imageSrc.openStream(); // create a new stream for the logo's image data
					dvdImg = ImageIO.read(logoData); // try downloading the logo from the internet

					// write the file in case of NullPointerException
					PrintWriter fileWriter;
					try {
						fileWriter = new PrintWriter(dvdImgLocal.getPath(), "UTF-8");
						fileWriter.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					ImageIO.write(dvdImg, "png", dvdImgLocal); // cache the logo in png format to retain transparency
					logoData.close(); // close the file stream
				}
				if (dvdImg != null) {
					// success so set x and y to center the logo
					// calculate the top left corner of the DVD logo
					x = (Game.instance.WindowWidth / 2) - (dvdImg.getWidth(null)) / 2;
					y = (Game.instance.WindowHeight / 2) - (dvdImg.getHeight(null)) / 2;
					// get the width and height
					width = dvdImg.getWidth(null);
					height = dvdImg.getHeight(null);
					if (dvdImg.getType() != BufferedImage.TYPE_INT_RGB) { // if the dvdImg isn't an RGB image
						// make it an RGB image
						BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						Graphics big = bi2.getGraphics();
						big.drawImage(dvdImg, 0, 0, null);
						dvdImg = bi2;
					}
					genNewColor(); // give it a random color
				} else {
					System.err.println("ERROR LOADING IMAGE");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				System.err.println("ERROR LOADING IMAGE");
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}

	public void OnTick() {
		if (dvdImg == null) { // If the dvdImg doesn't exist / hasn't been created yet
			try { // try for exception handling (ERROR 404)
				loadImage(); // load dvdImg
			} catch (Exception ex) {

			}
		}
		if (speed < 1) { // Set the speed to 1 if it's smaller than 1
    		speed = 1;
    	}
    	//loop to have an accurate simulation, if we add eg 1000, it will 100% go out of bounds instantly. It will only be visible for a couple of frames
    	for (int i = 0; i < speed; i++) {
        	x = x + xDir; //add on the x-axis
        	y = y + yDir; //add on the y-axis
        	//Hit wall
        	boolean didOnce = false; //used for corner checking
        	if ((x + dvdImg.getWidth()) > (Game.instance.WindowWidth - 16)) { //if out of bounds
        		xDir = -1; //change the xDir
        		genNewColor(); //generate a new color
        		hits++; //increase hits by 1
        		if (didOnce == true) { //if there was a hit already
        			cornerhits++; //increase corner hits by 1
        			if (doFreezeOnCornerHit) { //if freeze on corner hits is true, wait 1 second
            			try {
            				Thread.sleep(1000);
            			} catch (Exception ex) {
            				
            			}
            		}
        		}
        		didOnce = true; //set didOnce to true for corner checks
        	}
        	if (x < 0) { //if out of bounds
        		xDir = 1; //change the xDir
        		genNewColor(); //generate a new color
        		hits++; //increase hits by 1
        		if (didOnce == true) { //if there was a hit already
        			cornerhits++; //increase corner hits by 1
        			if (doFreezeOnCornerHit) { //if freeze on corner hits is true, wait 1 second
            			try {
            				Thread.sleep(1000);
            			} catch (Exception ex) {
            				
            			}
            		}
        		}
        		didOnce = true; //set didOnce to true for corner checks
        	}
			if ((y + dvdImg.getHeight()) > (Game.instance.WindowHeight - 40)) { //if out of bounds
        		yDir = -1; //change the yDir
        		genNewColor(); //generate a new color
        		hits++; //increase hits by 1
        		if (didOnce == true) { //if there was a hit already
        			cornerhits++; //increase corner hits by 1
        			if (doFreezeOnCornerHit) { //if freeze on corner hits is true, wait 1 second
            			try {
            				Thread.sleep(1000);
            			} catch (Exception ex) {
            				
            			}
            		}
        		}
        		didOnce = true; //set didOnce to true for corner checks
        	}
        	if (y < 0) { //if out of bounds
        		yDir = 1; //change the yDir
        		genNewColor(); //generate a new color
        		hits++; //increase hits by 1
        		if (didOnce == true) { //if there was a hit already
        			cornerhits++; //increase corner hits by 1
        			if (doFreezeOnCornerHit) { //if freeze on corner hits is true, wait 1 second
            			try {
            				Thread.sleep(1000);
            			} catch (Exception ex) {
            				
            			}
            		}
        		}
        		didOnce = true; //set didOnce to true for corner checks
        	}
    	}
    	repaint(); //Force the DVDLogo to render again
    }
    
    public void genNewColor() {
    	int pci = colorIndex; //store the current color
    	int nci = random.nextInt(colors.length+1); //generate a random number between 0 and colors.length
    	if (nci == pci) { //if its the previous colorIndex
    		nci++; //colorIndex++
    	}
    	if (nci >= colors.length) { //if we'll get an ArrayOutOfBoundsException
    		nci = colors.length - 1; //set it to colors.length
    	}
    	if (nci == pci) { //if its the previous colorIndex
    		nci++; //colorIndex++
    		if (nci >= colors.length) { //if we'll get an ArrayOutOfBoundsException
    			nci = 0; //set it to 0
    		}
    	}
    	colorIndex = nci; //set the colorIndex to the newly generated one
    }
    
    //required to extend Component
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    //required to extend Component
    static String[] getDescriptions() {
        return new String[1];
    }
    
    @Override //So that we render the logo so not to have a blank window.
    public void paint(Graphics g) {
    	BufferedImage image = tint(colors[colorIndex],dvdImg); //get the image
        Rectangle clip = g.getClipBounds(); //Get the window
        g.setColor(Color.BLACK); //set the current color to black
        g.fillRect(clip.x, clip.y, clip.width, clip.height); //set the screen to BLACK

    	Graphics2D g2d = (Graphics2D) g; //get a Graphics2D component from g
    	g2d.drawImage(dvdImg, x, y, null); //Draw the original DVD Logo
    	g2d.drawImage(image, x, y, null); //Draw the tinted DVD Logo on top of the normal one
    }

	//alais for tint(r,g,b,a,image);
    protected BufferedImage tint(Color col, BufferedImage logo)
    {
    	//get the r,g,b values and return the tinted sprite
		return tint(col.getRed() / 255, col.getGreen() / 255, col.getBlue() / 255, 1, logo);
    }
    
    protected BufferedImage tint(float r, float g, float b, float a, BufferedImage logo)
    {
    	//create a new BufferedImage which will be the tinted image
    	BufferedImage tintedSprite = new BufferedImage(logo.getWidth(), logo. getHeight(), BufferedImage.TRANSLUCENT);
    	//create a new tintedSprite
    	Graphics2D graphics = tintedSprite.createGraphics();
    	//draw the image to save it in RAM
    	graphics.drawImage(logo, 0, 0, null);
    	//now we cloned the "logo" into the "graphics"
    	graphics.dispose();
    	
    	for (int i = 0; i < tintedSprite.getWidth(); i++) //loop through the width
    	{
    		for (int j = 0; j < tintedSprite.getHeight(); j++) //loop through the height
    		{
    			//now we have the x and y coordinate of the pixel
    			//Get the rgba components from the sprite
    			int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
    			int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
    			int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
    			int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
    			//Multiply the rgba components to get the final values
    			rx *= r;
    			gx *= g;
    			bx *= b;
    			ax *= a;
    			//apply the tint
    			tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
    		}
    	}
    	//return the modified version of the tint
    	return tintedSprite;
    }
}