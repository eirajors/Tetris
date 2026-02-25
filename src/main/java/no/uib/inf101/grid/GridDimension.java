package no.uib.inf101.grid;

public interface GridDimension {

	/** Number of rows in the grid */
	int rows();

	/** Number of columns in the grid */
	int cols();

	/**
	 * Returns a representation of all the valid positions within this
	 * GridDimension
	 * @return Iterable<Position> of all valid positions
	 */
	default Iterable<CellPosition> positions(){
		return new Positions(this);
	}
	
	/**
	 * Reports whether the position is within bounds for this grid
	 * 
	 * @param pos position to check
	 * @return true if the coordinate is within bounds, false otherwise
	 */
	boolean positionIsOnGrid(CellPosition pos);
}
