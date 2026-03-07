package no.uib.inf101.grid;

public record CellPosition(int row, int col) {

  /**
   * 
   * @param pos
   */
  public CellPosition(CellPosition pos) {
    this(pos.row, pos.col);
  }

}
