package no.uib.inf101.grid;

public record CellPosition(int row, int col) {

  /**
   * Creates a new CellPosition with the same row and column as the given position
   * 
   * @param pos the CellPosition to copy
   */
  public CellPosition(CellPosition pos) {
    this(pos.row, pos.col);
  }

}
