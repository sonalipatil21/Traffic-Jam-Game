import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import acm.graphics.*;
import acm.program.*;

public class GraphicsGame extends GraphicsProgram {
	/**
	 * Here are all of the constants
	 */
	public static final int PROGRAM_WIDTH = 500;
	public static final int PROGRAM_HEIGHT = 500;
	public static final String lABEL_FONT = "Arial-Bold-22";
	public static final String EXIT_SIGN = "EXIT";
	public static final String IMG_FILENAME_PATH = "images/";
	public static final String IMG_EXTENSION = ".png";
	public static final String VERTICAL_IMG_FILENAME = "_vert";

	// instance variables
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 6;
	private Level level;
	private Space sel = null;
	
	
	public void init() {
		
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		level = new Level(NUM_ROWS, NUM_COLS);
		addMouseListeners();
	}

	public void run() {

		drawLevel();
	}

	private void drawLevel() {
		
		drawGridLines();
		drawCars();
		drawWinningTile();
	}

	/**
	 * This should draw the label EXIT and add it to the space that 
	 * represents the winning tile.
	 */
	private void drawWinningTile() {

		double x = level.getColumns() * spaceWidth() - spaceWidth() * 3 /4;
		double y = 2.5 * spaceHeight();
		
		GLabel label = new GLabel(EXIT_SIGN, x, y);
		label.setColor(Color.RED);
		label.setFont(lABEL_FONT);
		label.setVisible(true);
		label.sendToFront();
		add(label);
	}

	/**
	 * draw the lines of the grid.  Test this out and make sure you
	 * have it working first.  Should draw the number of grids
	 * based on the number of rows and columns in Level
	 */
	private void drawGridLines() {

		// Draws rows...
		for (int r = 0; r < level.getRows(); r++)
		{
			GLine line = new GLine(0, (int)(r * spaceHeight()),getWidth(),(int)(r * spaceHeight()));
			line.setColor(Color.BLACK);
			line.setVisible(true);
			add(line);
		}
		
		// Draw Columns
		for (int c = 0; c < level.getColumns(); c++)
		{
			GLine line = new GLine((int)(c * spaceWidth()), 0, (int)(c * spaceWidth()), getHeight());
			line.setColor(Color.BLACK);
			line.setVisible(true);
			add(line);
		}	
	}

	/**
	 * Maybe given a list of all the cars, you can go through them
	 * and call drawCar on each?
	 */
	private void drawCars() {
		
		ArrayList<Vehicle> vehicles = level.getVehiclesOnBoard();
		for (int i = 0; i < vehicles.size(); i++)
			drawCar(vehicles.get(i));
	}

	/**
	 * Given a vehicle object, which we will call v, use the information
	 * from that vehicle to then create a GImage and add it to the screen.
	 * Make sure to use the constants for the image path ("/images"), the
	 * extension ".png" and the additional suffix to the filename if the
	 * object is vertical when creating your GImage.  Also make sure to
	 * set the images size according to the size of your spaces
	 * 
	 * @param v the Vehicle object to be drawn
	 */
	private void drawCar(Vehicle v) {

		GImage image = null;

		if (v.getIsVertical())
		{
			double w = spaceWidth();
			double h = v.getVehicleLength() * spaceHeight();
			image = new GImage(getImage(getCodeBase(), IMG_FILENAME_PATH + v.getVehicleType().toString() + VERTICAL_IMG_FILENAME + IMG_EXTENSION));
			image.setSize(w, h);
		}
		else
		{
			double w = v.getVehicleLength() * spaceWidth();
			double h = spaceHeight();
			image = new GImage(getImage(getCodeBase(), IMG_FILENAME_PATH + v.getVehicleType().toString() + IMG_EXTENSION)); 
			image.setSize(w, h);
		}

		Space space = v.getSpace();
		double x = space.getCol() * spaceWidth();
		double y = space.getRow() * spaceHeight();

		image.move(x, y);
		image.setVisible(true);

		add(image);
	}

	// Check the vehicle and if possible move to new space.
	private void checkMove(Space space) {
		
		int numSpaces = 0;
		
		if (sel != null && space != null)
		{
			if (sel.getRow() == space.getRow())
				numSpaces = space.getCol() - sel.getCol();
			else
				numSpaces = space.getRow() - sel.getRow();
			
			if (level.canMoveNumSpaces(sel, numSpaces))
			{
				level.moveNumSpaces(sel, numSpaces);
				removeAll();
				drawLevel();
			}
			sel = null;
		}
		
		if (level.isCarReachedEnd())
		{
			double msgX = level.getColumns() * spaceWidth() / 4;
			double msgY = level.getColumns() * spaceHeight() / 2;
			
			removeAll();

			GLabel label = new GLabel("Congratulations!!", msgX, msgY);
			label.setColor(Color.RED);
			label.setFont(lABEL_FONT);
			label.setVisible(true);
			label.sendToFront();

			add(label);
		}
	}
	
	// Mouse listeners
	public void mousePressed(MouseEvent e) {
	
		int x = e.getX();
		int y = e.getY();
		sel = convertXYToRowColumn(x, y);	
	}
	
	public void mouseReleased(MouseEvent e) {
	
		int x = e.getX();
		int y = e.getY();
		
		Space space = convertXYToRowColumn(x, y);
		checkMove(space);
	}
	
	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those
	 * x and y coordinates, returning null if no Vehicle currently sits at
	 * those coordinates.
	 * 
	 * @param x the x coordinate in pixels
	 * @param y the y coordinate in pixels
	 * @return the Vehicle object that currently sits at that xy location
	 */
	private Vehicle getVehicleFromXY(double x, double y) {

		Space vSpace = convertXYToRowColumn(x, y);
		return level.getVehicle(vSpace);
	}

	/**
	 * This is a useful helper function to help you calculate
	 * the number of spaces that a vehicle moved while dragging
	 * so that you can then send that information over as 
	 * numSpacesMoved to that particular Vehicle object.
	 * 
	 * @return the number of spaces that were moved
	 */
	private int calculateSpacesMoved() {
		return 0;
	}

	/**
	 * Another helper function/method meant to return the rowcol
	 * given an x and y coordinate system.  Use this to help
	 * you write getVehicleFromXY
	 * 
	 * @param x x-coordinate (in pixels)
	 * @param y y-coordinate (in pixels)
	 * @return the RowCol associated with that x and y
	 */
	private Space convertXYToRowColumn(double x, double y) {

		int row = -1;
		int col = -1;
		
		// Find the row
		for (int r = 0; r < NUM_ROWS; r++)
		{
			if (y >= (r * spaceHeight()) &&  y <= ((r + 1) * spaceHeight()))
			{
				row = r;
				break;
			}
		}

		// Find the column
		for (int c = 0; c < NUM_COLS; c++)
		{
			if (x >= (c * spaceWidth()) &&  x <= ((c + 1) * spaceWidth()))
			{
				col = c;
				break;
			}
		}

		// Check if both row and columns are found
		if (row >= 0 && col >= 0)
			return new Space(row, col);
		else
			return null;
	}

	/**
	 * 
	 * @return the width (in pixels) of a single space in the grid
	 */
	private double spaceWidth() {
		
		return getWidth() / level.getColumns();
	}

	/**
	 * 
	 * @return the height in pixels of a single space in the grid 
	 */
	private double spaceHeight() {
		
		return getHeight() / level.getRows();
	}
}
