/**
 * File responsible for taking care of the console interaction between the user
 * and the traffic jam level.  This console game should only interact with the level.
 * You should not create any other instance variables or will you need to modify any other methods in console game
 * You will have to use methods defined below
 * 
 * @author Osvaldo
 */

import java.io.*;

public class ConsoleGame {
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = 6;
	
	private Level level;

	public static void main(String[] args) {
		ConsoleGame cg = new ConsoleGame();
		cg.playGame();
	}
	
	public ConsoleGame() {
		level = new Level(NUM_ROWS, NUM_COLS);
	}
	
	public void playGame() {
		/* TODO: This is the only method you'll need to fill in, which should be the loop that 
		 * continually asks the user for a location and a number and then tries to move that location by
		 * that many spaces.  The following line just prints out one version of the grid (once you have the board up
		 * and running, this should print something out that is more significant)
		 */
		int moveCount = 0;
		
		System.out.println(level);  //use this line to print out the current level with the header row information
		while (true)
		{		
			System.out.println("What is your next move? (Moves so far " + moveCount + ")");
			
			Space loc = getLocationFromUser(NUM_ROWS, NUM_COLS);
			if (loc == null)
				break;
				
			int numSpaces = getInteger("How many spaces would you like this vehicle to move? ");
			
			if (level.canMoveNumSpaces(loc, numSpaces))
			{
				level.moveNumSpaces(loc, numSpaces);
				moveCount++;
			}
			else
			{
				System.out.println("sorry but that vehicle can't be moved to where you want, please try again");				
			}
			System.out.println(level);  //use this line to print out the current level with the header row information
			if (level.isCarReachedEnd())
			{
				System.out.println("Congratulations!!");
				break;
			}
		}
	}

	/**
	 * Taken and modified from Lecturer Jerry Cain's wordladder code from Stanford
	 * Repeatedly prompts the user to either hit return (to quit) or to enter a location in the form of "A2"
	 * In the unprecedented event that the platform's IO
	 * system gets out of whack, the method will terminated whatever application called it.
	 * No unchecked exceptions are ever thrown.
	 *
	 * @return the RowCol that represents the user's response (e.g. "A1" would be row=0, col=0)
	 */     
	private final static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
	public static Space getLocationFromUser(int maxRows, int maxCols) {
		try {
			while (true) {
				System.out.print("Please enter a location using the letter for row and number for column: ");
				String response = consoleReader.readLine();
				Space loc = convertStringToIntPair(response);
				if (isValidLocation(loc, maxRows, maxCols)) return loc;
				System.out.println("Sorry, but \"" + response + "\" isn't a valid response.  Please try again.");
				System.out.println();
			}
		} catch (IOException ioe) {
			System.err.println("Problem encountered while reading text from standard input.  Bailing.");
			System.exit(1);
		}

		return null; // we never get here, but the Java compiler can't figure that out
	}

	/**
	 * waits to for the user to input a number, similar to cin &gt;&gt; num in c++, with the custom message
	 * that prompts the user for a number given beforehand.
	 * 
	 * @param message the message to print before waiting for the number
	 * @return the integer the user inputs
	 */
	
	public static int getInteger(String message) {
		int num;
		while(true) {
			System.out.println(message);
			try {
				num = Integer.parseInt(consoleReader.readLine());
				return num;
			}catch(NumberFormatException nfe) {
				System.err.println("You didn't enter a valid number, please try again.");
			}catch(IOException ioe) {
				System.err.println("Problem encountered while reading text from standard input.  Bailing.");
				System.exit(1);
			}
		}
	}

	/**
	 * This helper method is used by getLocation to ensure that location entered is a valid one
	 * 
	 * @param loc the location you are trying to determine as being valid or not
	 * @param maxRows the maximum # of rows before the location is no longer valid
	 * @param maxCols the maximum # of columns before the location is no longer valid
	 * @return whether or not the location is valid
	 */
	public static boolean isValidLocation(Space loc, int maxRows, int maxCols) {
		if(loc == null) return false;
		return loc.getRow() < maxRows && loc.getCol() < maxCols;
	}

	/**
	 * This helper method is used by getLocation to convert a user string to a row column
	 * 
	 * @param location the string response given by the user to specify a location
	 * @return a RowCol which has the row and column number
	 */
	public static Space convertStringToIntPair(String location) {
		if(location == null || location.equals("") || location.length() != 2) return null;
		location = location.toUpperCase();
		char row = location.charAt(0);
		char col = location.charAt(1);
		return new Space(row-'A', col-'1');
	}
}
