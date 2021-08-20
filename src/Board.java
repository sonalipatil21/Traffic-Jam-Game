import java.util.*;

/**
 * This represents the board.  It contains a 2d array of the vehicles
 * (referred later to as grid), and will be in charge of doing the bounds type checking for doing any of the moves.
 * It will also have a method display board which will give back a string representation of the board.
 * 
 *
 */

public class Board {
	Vehicle[][] grid;
	ArrayList<Vehicle> vehicles;
	int m_NumRows;
	int m_NumCols;
		
	/**
	 * Constructor for the board which sets up an empty grid of size rows by columns
	 * 
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	public Board(int rows, int cols) {
		
		m_NumRows = rows;
		m_NumCols = cols;
		grid = new Vehicle[rows][cols];
		vehicles = new ArrayList<Vehicle>();
	}
	
	/**
	 * @return number of columns the board has
	 */
	public int getNumCols() {

		return m_NumCols;
	}

	/**
	 * @return number of rows the board has
	 */
	public int getNumRows() {

		return m_NumRows;
	}
	
	/**
	 * @return List of vehicles
	 */
	public ArrayList<Vehicle> getVehiclesOnBoard() {
		
		return vehicles;
	}
	
	private boolean IsVehicleOccupied(Vehicle v, Space s)
	{
		if (v != null)
		{
			Space [] spaces = v.spacesOccupied();
			
			if (spaces != null)
			{
				for (int i = 0; i < spaces.length; i++)
				{
					if (spaces[i].getRow() == s.getRow() && spaces[i].getCol() == s.getCol())
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Grabs the vehicle present on a particular space if any is there
	 * If a Vehicle occupies three spaces, the same Vehicle pointer should be returned for all three spaces
	 * 
	 * @param s the desired space where you want to look to see if a vehicle is there
	 * @return a pointer to the Vehicle object present on that space, if no Vehicle is present, null is returned
	 */
	public Vehicle getVehicle(Space s) {
		
		if ((s.getRow() < m_NumRows) && s.getCol() < m_NumCols)
		{
			for (int r = 0; r < m_NumRows; r++)
			{
				for (int c = 0; c < m_NumCols; c++)
				{
					Vehicle v = grid[r][c];
					if (IsVehicleOccupied(v, s))
						return v;
				}
			}
		}
		return null;
	}

	/**
	 * adds a vehicle to the board. It would be good to do some checks for a legal placement here.
	 * 
	 * @param type type of the vehicle
	 * @param startRow row for location of vehicle's top
	 * @param startCol column for for location of vehicle leftmost space
	 * @param length number of spaces the vehicle occupies on the board
	 * @param vert true if the vehicle should be vertical
	 */
	public void addVehicle(VehicleType type, int startRow, int startCol, int length, boolean vert) {
		
		Vehicle v = new Vehicle(type, startRow, startCol, length, vert);
		grid[startRow][startCol] = v;
		vehicles.add(v);
	}

	/**
	 * This method moves the vehicle on a certain row/column a specific number of spaces
	 * 
	 * @param start the starting row/column of the vehicle in question
	 * @param numSpaces the number of spaces to be moved by the vehicle (can be positive or negative)
	 * @return whether or not the move actually happened
	 */
	public boolean moveNumSpaces(Space start, int numSpaces) {
		
		if (canMoveNumSpaces(start, numSpaces))
		{
			Vehicle v = getVehicle(start);
			if (v != null)
				v.move(numSpaces);
		}
			
		return false;
	}
	
	/**
	 * This method just checks to see if the vehicle on a certain row/column can move a specific number of spaces, though
	 * it will not move the vehicle.  You should use this when you wish to move or want to see if you can
	 * move a vehicle numSpaces without going out of bounds or hitting another vehicle
	 * 
	 * @param start the starting row/column of the vehicle in question
	 * @param numSpaces number of spaces to be moved by the vehicle (positive or negative)
	 * @return whether or not the move is possible
	 */
	public boolean canMoveNumSpaces(Space start, int numSpaces) {
	
		boolean canMove = false;
		Vehicle v = getVehicle(start);
		
		if (v != null)
		{
			//Space s = v.ifIWereToMove(numSpaces);
			Space [] spaces = v.spacesOccupiedOnTrail(numSpaces);

			if (spaces.length == 0)
			{	
				return canMove;
			}
			
			canMove = true;
			
			for (int i = 0; i < spaces.length; i++)
			{
				int row = spaces[i].getRow();
				int col = spaces[i].getCol();
				
				if (row < 0 || col < 0)
					canMove = false;
				
				if (row < m_NumRows && col < m_NumCols)
				{
					if (getVehicle(spaces[i]) != null && getVehicle(spaces[i]) != v)					
						canMove = false;
				}
				else
				{
					canMove = false;
				}
			}
		}
		return canMove;
	}
	
	// This method helps create a string version of the board
	// You do not need to call this at all, just let it be
	public String toString() {
		
		return BoardConverter.createString(this);
	}
	
	/* Testing methods down here for testing the board 
	 * make sure you run the board and it works before you write the rest of the program! */
	
	public static void main(String[] args) {
		Board b = new Board(5, 5);
		b.addVehicle(VehicleType.MYCAR, 1, 0, 2, false);
		b.addVehicle(VehicleType.TRUCK, 0, 2, 3, true);
		b.addVehicle(VehicleType.AUTO, 3, 3, 2, true);
		b.addVehicle(VehicleType.AUTO, 0, 3, 2, true);
		System.out.println(b);
		testCanMove(b);
		testMoving(b);
		System.out.println(b);
	}
	
	public static void testMoving(Board b) {
		System.out.println("just moving some stuff around");
		b.moveNumSpaces(new Space(1, 2), 1);
		b.moveNumSpaces(new Space(1, 2), 1);
		b.moveNumSpaces(new Space(1, 1), 1);
	}
	
	public static void testCanMove(Board b) {
		System.out.println("Ok, now testing some moves...");
		System.out.println("These should all be true");
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(0, 2), 2));
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(1, 2), 2));
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(2, 2), 2));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(3, 3), -1));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(4, 3), -1));
		
		System.out.println("And these should all be false");
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(3, 2), 2));
		System.out.println("Moving the car into truck " + b.canMoveNumSpaces(new Space(1, 0), 1));
		System.out.println("Moving the car into truck " + b.canMoveNumSpaces(new Space(1, 0), 2));
		System.out.println("Moving nothing at all " + b.canMoveNumSpaces(new Space(4, 4), -1));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(3, 3), -2));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(4, 3), -2));
		System.out.println("Moving upper auto up " + b.canMoveNumSpaces(new Space(0, 3), -1));
		System.out.println("Moving upper auto up " + b.canMoveNumSpaces(new Space(1, 3), -1));
	}
}
