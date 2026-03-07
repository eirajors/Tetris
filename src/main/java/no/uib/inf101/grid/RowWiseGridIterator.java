package no.uib.inf101.grid;

import java.util.Iterator;

public class RowWiseGridIterator implements Iterator<GridCell> {

  private CellPosition next;
  private final IGrid grid;

  // konstruktør
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
    return next.row() + 1 <= grid.rows();
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
