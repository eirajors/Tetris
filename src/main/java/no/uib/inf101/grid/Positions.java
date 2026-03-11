package no.uib.inf101.grid;

import java.util.Iterator;

/** 
 * An Iterable that provides all positions in a grid
 * 
 * The positions are iterated row by row starting from (0,0)
 */
public class Positions implements Iterable<CellPosition> {

  private GridDimension grid;

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
    return new PositionIterator(grid);
  }
}

/**
 * Iterator that iterates through all positions in a grid row by row
 */
class PositionIterator implements Iterator<CellPosition> {

  private GridDimension grid;
  private CellPosition current = new CellPosition(0, 0);

  public PositionIterator(GridDimension grid) {
    this.grid = grid;
  }

  @Override
  public boolean hasNext() {
    return grid.positionIsOnGrid(current);
  }

  @Override
  public CellPosition next() {
    CellPosition next = current;
    current = new CellPosition(current.row(), current.col() + 1);
    if (!grid.positionIsOnGrid(current)) {
      current = new CellPosition(current.row() + 1, 0);
    }
    return next;
  }

}