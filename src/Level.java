import java.util.*;


public class Level {	
	private Board board;
	
	//TODO fill out this class with a Level constructor
	//all the other methods necessary and any other instance variables needed
	public Level(int nRows, int nCols) {
		
		board = new Board(nRows, nCols);
		
		board.addVehicle(VehicleType.MYCAR, 2, 0, 2, false);
		board.addVehicle(VehicleType.TRUCK, 0, 2, 3, true);
		board.addVehicle(VehicleType.AUTO,  0, 4, 2, false);
		board.addVehicle(VehicleType.AUTO,  1, 4, 2, true);
		board.addVehicle(VehicleType.AUTO,  1, 5, 2, true);
		board.addVehicle(VehicleType.AUTO,  3, 3, 2, true);
		board.addVehicle(VehicleType.AUTO,  4, 4, 2, false);
	}
	
	/**
	 * @return the number of columns on the board
	 */
	public int getColumns() {
		
		int cols = 0;
		
		if (board !=null)
			cols = board.getNumCols();
		return cols;
	}
	
	/**
	 * @return the number of row on the board
	 */
	public int getRows() {
		
		int rows = 0;
		
		if (board !=null)
			rows = board.getNumRows();
		return rows;
	}
	
	/**
	 * @return List of vehicles
	 */
	public ArrayList<Vehicle> getVehiclesOnBoard() {
		
		return board.getVehiclesOnBoard();
	}
	
	/**
	 * @return the vehicle found at the space
	 */
	public Vehicle getVehicle(Space space) {
		
		return board.getVehicle(space);
	}
	
	
	/**
	 * @return true if it can move number of spaces
	 */
	public boolean canMoveNumSpaces(Space start, int numSpaces) {
		
		return board.canMoveNumSpaces(start, numSpaces);
	}
	
	/**
	 * @return true if it moves number of spaces
	 */
	public boolean moveNumSpaces(Space start, int numSpaces) {	
		
		return board.moveNumSpaces(start, numSpaces);
	}
	
	public boolean isCarReachedEnd() {	
		
		for (int row = 0; row < board.getNumRows(); row++)
		{
			for (int col = 0; col < board.getNumCols(); col++)
			{
				Vehicle v = board.getVehicle(new Space(row, col));
				if ( (v !=null) && v.getVehicleType() == VehicleType.MYCAR)
				{
					Space spaceOccupied = v.getSpace();
					
					if (spaceOccupied.getCol() + v.m_VehicleLength >= getColumns() )
						return true;
					
					break;
				}
			}
		}
		return false;
	}	
	//Methods already defined for you
	/**
	 * generates the string representation of the level, including the row and column headers to make it look like
	 * a table
	 * 
	 * @return the string representation
	 */
	public String toString() {
		String result = generateColHeader(getColumns());
		result+=addRowHeader(board.toString());
		return result;
	}
	
	/**
	 * This method will add the row information
	 * needed to the board and is used by the toString method
	 * 
	 * @param origBoard the original board without the header information
	 * @return the board with the header information
	 */
	private String addRowHeader(String origBoard) {
		String result = "";
		String[] elems = origBoard.split("\n");
		for(int i = 0; i < elems.length; i++) {
			result += (char)('A' + i) + "|" + elems[i] + "\n"; 
		}
		return result;
	}
	
	/**
	 * This one is responsible for making the row of column numbers at the top and is used by the toString method
	 * 
	 * @param cols the number of columns in the board
	 * @return if the # of columns is five then it would return "12345\n-----\n"
	 */
	private String generateColHeader(int cols) {
		String result = "  ";
		for(int i = 1; i <= cols; i++) {
			result+=i;
		}
		result+="\n  ";
		for(int i = 0; i < cols; i++) {
			result+="-";
		}
		result+="\n";
		return result;
	}
}
