package no.uib.inf101.grid;

import java.util.Iterator;

/** 
 * An Iterable that provides all positions in a grid
 * 
 * The positions are iterated row by row starting from (0,0)
 */
public class Positions implements Iterable<CellPosition> {

  private final GridDimension grid;

  /**
   * Creates a Positions iterable for the given grid
   * 
   * @param grid the grid whose positions should be iterated over
   */
  public Positions(GridDimension grid) {
    this.grid = grid;
  }

  @Override
  public Iterator<CellPosition> iterator() {
    return new PositionIterator(this.grid);
  }
}

/**
 * Iterator that iterates through all positions in a grid row by row
 */
class PositionIterator implements Iterator<CellPosition> {

  private final GridDimension grid;
  private CellPosition current;

  public PositionIterator(GridDimension grid) {
    this.grid = grid;
    this.current = new CellPosition(0, 0);
  }

  @Override
  public boolean hasNext() {
    return this.grid.positionIsOnGrid(this.current);
  }

  @Override
  public CellPosition next() {
    CellPosition next = this.current;
    this.current = new CellPosition(this.current.row(), this.current.col() + 1);
    if (!this.grid.positionIsOnGrid(this.current)) {
      this.current = new CellPosition(this.current.row() + 1, 0);
    }
    return next;
  }

}