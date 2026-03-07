package no.uib.inf101.grid;

/**
 * This represents a position on a grid
 */
public record CellPosition(int row, int col) {

  public CellPosition(CellPosition pos) {
    this(pos.row, pos.col);
  }

}
