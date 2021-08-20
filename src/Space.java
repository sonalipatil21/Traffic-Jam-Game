/**
 * Simple class that represents a row and a column, with simple getters and setters for both
 * @author Sonali Patil
 */

public class Space {
	private int m_Row;
	private int m_Col;
	/**
	 * The constructor that will set up the object to store a row and column
	 * 
	 * @param row
	 * @param col
	 */
	public Space(int row, int col) {
		m_Row = row;
		m_Col = col;
		
	}
	
	public int getRow() {		
		return m_Row;
	}
	
	public void setRow(int boardRow)
	{
		m_Row = boardRow;
	}
	
	public int getCol() {
		return m_Col;
	}
	public void setCol(int boardCol)
	{
		m_Col = boardCol;
	}
	
}
