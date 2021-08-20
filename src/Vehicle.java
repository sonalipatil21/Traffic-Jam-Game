public class Vehicle {
	
	VehicleType m_VehicleType;
	boolean m_Vertical;
	Space m_Space;			// Start space or vehicle's back wheels
	int m_VehicleLength;

	public Vehicle()
	{
		m_VehicleType = VehicleType.MYCAR;
		m_Vertical = true;
		m_Space = new Space(0,0);
		m_VehicleLength = 1;		
	}
	
	public Vehicle(VehicleType vChoice, int startRow, int startCol, int length, boolean vertical )
	{
		m_VehicleType = vChoice;
		m_Space = new Space(startRow, startCol);
		m_VehicleLength = length;
		m_Vertical = vertical;
	}

	/**
	 * @return the type associated with this particular vehicle
	 */
	public VehicleType getVehicleType() {
		
		return m_VehicleType;
	}
	
	public void setVehicleType(VehicleType vChoice)
	{
		m_VehicleType = vChoice;
	}

	
	/**
	 * @return the vehicle length
	 */
	public int getVehicleLength() {
		
		return m_VehicleLength;
	}

	public boolean getIsVertical()
	{
		return m_Vertical;
	}
	
	public void setIsVertical(boolean vertical)
	{
		m_Vertical = vertical;
	}
	
	public void setStartRow(int startRow)
	{
		m_Space.setRow(startRow);
	}
	
	public void setStartCol(int startCol)
	{
		m_Space.setCol(startCol);
	}
	
	public Space getSpace()
	{
		return m_Space;
	}
	
	public void setSpace(Space s)
	{
		m_Space.setRow(s.getRow());
		m_Space.setCol(s.getCol());
	}
	
	/**
	 * Provides an array of Spaces that indicate where a particular Vehicle
	 * would be located, based on its current starting space
	 * 
	 * @return the array of Spaces occupied by that particular Vehicles
	 */
	public void move(int numSpaces)
	{
		if (m_Vertical)
		{
			m_Space.setRow(m_Space.getRow() + numSpaces);
		}
		else
		{
			m_Space.setCol(m_Space.getCol() + numSpaces);
		}
	}
	
	public Space ifIWereToMove(int numSpaces)
	{
		if (m_Vertical)
		{
			return new Space(m_Space.getRow() + numSpaces, m_Space.getCol());
		}
		else
		{
		
			return new Space(m_Space.getRow(), m_Space.getCol() + numSpaces);
		}
	}
	

	public Space[] spacesOccupied() {

		Space[] Spaces = new Space[m_VehicleLength];
		
		for (int i = 0; i < m_VehicleLength; i++)
		{
			if (m_Vertical)
			{
				Spaces[i] = new Space(m_Space.getRow() + i,  m_Space.getCol());		
			}
			else
			{
			    Spaces[i] = new Space(m_Space.getRow(),  m_Space.getCol() + i);		
			}
		}
		return Spaces;
	
	}

	
	/**
	 * Calculates an array of the spaces that would be traveled if a vehicle
	 * were to move numSpaces
	 * 
	 * @param numSpaces
	 *            The number of spaces to move (can be negative or positive)
	 * @return The array of Spaces that would need to be checked for Vehicles
	 */
	public Space[] spacesOccupiedOnTrail(int numSpaces) {
		
		Space[] spaces = new Space[Math.abs(numSpaces)];

		if (numSpaces >= 0)
		{
			for ( int i = 0; i < numSpaces; i++)
			{
				if (m_Vertical)
				{
					spaces[i] = new Space( m_Space.getRow() + m_VehicleLength + i,  m_Space.getCol());			
				}
				else
				{
					spaces[i] = new Space( m_Space.getRow(),  m_Space.getCol() + m_VehicleLength + i);		
				}		
			}		
		}
		else
		{
			for ( int i = 0; i < Math.abs(numSpaces); i++)
			{
				if (m_Vertical)
				{
					spaces[i] = new Space( m_Space.getRow() - i - 1,  m_Space.getCol());			
				}
				else
				{
					spaces[i] = new Space( m_Space.getRow(),  m_Space.getCol() - i - 1);		
				}		
			}				
		}
		return spaces;
	}
	
	public String toString()
	{
		
		switch(m_VehicleType)
		{
			case MYCAR:
				return "Car \nLength: 2 " +  "\nPosition: Row: " + m_Space.getRow() + " Column: " +  m_Space.getCol();
			case TRUCK:
				return "Truck \nLength: 3" + "\nPosition: Row: " + m_Space.getRow() + " Column: " + m_Space.getCol();
			case AUTO:
				return "Auto \nLength: 2" +  "\nPosition: Row: " + m_Space.getRow() + " Column: " + m_Space.getCol();
			default:
				return "Vehicle Type: Unknown";
		}
	}
	
	public static void printSpaces(Space[] arr) {
		
		if (arr != null)
		{
		  for(int i = 0; i < arr.length; i++) {
			System.out.print("Row:" + arr[i].getRow() + " Column:" + arr[i].getCol());	
			System.out.print("\n");
		  }
		  System.out.println();
	}
}
	
	public static void main(String[] args)
	{

		Vehicle someTruck = new Vehicle(VehicleType.TRUCK, 1, 1, 3, true);
		Vehicle someAuto = new Vehicle(VehicleType.AUTO, 2, 2, 2, false);
		
		System.out.println(someTruck.toString());
		System.out.println("\n");
		System.out.println(someAuto.toString());
		System.out.println("\n");
		System.out.println("This next test is for spacesOccupied: ");
		System.out.println("vert truck at r1c1 should give you r1c1; r2c1; r3c1 as the spaces occupied:does it?");
		Vehicle.printSpaces(someTruck.spacesOccupied());
		System.out.println("horiz auto at r2c2 should give you r2c2; r2c3 as the spaces occupied:does it?");
		printSpaces(someAuto.spacesOccupied());
		System.out.println("if we were to move horiz auto -2 it should give you at least r2c0; r2c1; it may also add r2c2; r2c3 to its answer:does it?");
		printSpaces(someAuto.spacesOccupiedOnTrail(-2));
		
		//ADD SOME MOVE AND IFIWERETOMOVE TEST CODE BELOW THIS LINE
		someAuto.move(2);
		System.out.println("The location should end up at r2c4 and r2r5 ");
		printSpaces(someAuto.spacesOccupied());
		
		Space possibleSpace = someAuto.ifIWereToMove(-3);
		System.out.print("Row:" + possibleSpace.getRow() + " Column:" + possibleSpace.getCol());	
	}
}
