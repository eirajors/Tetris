package no.uib.inf101.grid;

import java.util.Iterator;

/**
 * Iterator that iterates through all cells in a grid row by row
 * 
 * Each call to next() returns a GridCell containing the position
 * and the value stored at that position
 */
public class RowWiseGridIterator implements Iterator<GridCell> {

  private CellPosition next;
  private final IGrid grid;

  /**
   * Creates an iterator for the given grid
   * 
   * @param grid the grid to iterate through
   */
  public RowWiseGridIterator(IGrid grid) {
    this.next = new CellPosition(0, 0);
    this.grid = grid;
  }

  @Override
  public boolean hasNext() {
    if (next.row() + 1 < 0) {
      throw new IllegalArgumentException("Row cannot be a negative number.");
    }
    if (next.col() + 1 < 0) {
      throw new IllegalArgumentException("Col cannot be a negative number.");
    }
    return grid.positionIsOnGrid(next);
  }

  @Override
  public GridCell next() {

    CellPosition posNow = next;

    Character cellSymbol = grid.get(posNow);

    if (next.col() + 1 < grid.cols()) {
      next = new CellPosition(posNow.row(), posNow.col() + 1);
    } else {
      next = new CellPosition(next.row() + 1, 0);
    }

    GridCell cell = new GridCell(posNow, cellSymbol);

    return cell;
  }

}
