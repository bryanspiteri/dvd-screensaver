package com.hyblocker.dvdscreensaver;

public class Game {
	public static Game instance; // So that we don't create a new instance of "Game" by mistake
	public Window window; // Reference to the window so that we can create it and update the logo's
							// position
	public boolean executeGame = true; // If false we don't run the game

	// Ticks per Second
	public final float gameTick = 30f; // Frame rate (FPS) of the screensaver
	public double deltaTime = 0; // Time in seconds that passed between the last iteration of the main Game loop
	public long delay = 0; // Time in seconds to wait between this and he next frame. Changes every frame

	public int WindowHeight, WindowWidth; // Dimensions of the window (x,y)

	public DVDLogo logo; // The logo itself
	public InfoWindow info; // Keep a reference of the stats window so that we can update them

	public Game() {
		instance = this; // Set the instance to this
	}

	public void init() { // Initialise the main thread
		new Window().Make(); // make a new temporary window object and tell it to initiliase itself
		executeGame = true; // set the game to executing
		logo.init(); // load the image of the logo and set the variable as the instance
		info = new InfoWindow(); // Create a new stats window
		info.InitWindow(); // initialise the stats window
		GameLoop(); // begin the gameloop
	}

	public void GameLoop() {
		executeGame = true; // set the game to executing (IN CASE)
		while (executeGame) { // Make an infinite loop that halts when executeGame is set to false
			long time = System.currentTimeMillis(); // get the current system time in milliseconds and store it in RAM

			// window
			WindowHeight = window.app.getHeight(); // get the window dimensions
			WindowWidth = window.app.getWidth(); // we do this in the loop so that
			// it changes with the window size, since I
			// can change the window's dimensions

			// How long each frame should last - time it took for one frame
			delay = (long) ((1000 / gameTick) - (System.currentTimeMillis() - time));

			// Wait one frame
			// aka 1/gameTick of a second (1/30 in this case)
			if (delay > 0) { // Don't do wait one frame if we shouldn't wait (skip this frame)
				try {
					deltaTime += 1000 / gameTick; // increase deltatime
					if (deltaTime > 1 || deltaTime < 0) { // MAKE SURE THAT DELTATIME IS ALWAYS BETWEEN 1 AND 0
						deltaTime = 0;
					}
					instance.OnTick(); // Call the OnTick method (where we have our logic)
					instance.OnDraw(); // Call the OnDraw method (UNUSED)
					Thread.sleep(delay); // wait the delay and move to the next iteration
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void Quit() {
		executeGame = false; // Stop executing the game
		try { // try to wait for 1/10 of a second
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0); // quit
	}

	public void OnTick() { // Called every frame
		logo.OnTick(); // Call the logo's OnTick method
		info.OnTick(); // Update the stats
	}

	public void OnDraw() { // UNUSED

	}
}