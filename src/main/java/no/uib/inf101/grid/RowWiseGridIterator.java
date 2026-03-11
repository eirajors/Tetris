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
    if (this.next.row() + 1 < 0) {
      throw new IllegalArgumentException("Row cannot be a negative number.");
    }
    if (this.next.col() + 1 < 0) {
      throw new IllegalArgumentException("Col cannot be a negative number.");
    }
    return this.grid.positionIsOnGrid(this.next);
  }

  @Override
  public GridCell next() {

    CellPosition posNow = this.next;

    Character cellSymbol = this.grid.get(posNow);

    if (this.next.col() + 1 < this.grid.cols()) {
      this.next = new CellPosition(posNow.row(), posNow.col() + 1);
    } else {
      this.next = new CellPosition(this.next.row() + 1, 0);
    }

    GridCell cell = new GridCell(posNow, cellSymbol);

    return cell;
  }

}
