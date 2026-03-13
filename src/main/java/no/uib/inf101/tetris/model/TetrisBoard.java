package no.uib.inf101.tetris.model;

import java.util.Iterator;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

/**
 * Represents the board where tetrominoes are placed
 * 
 * The board stores the placed tiles and supports operations such as
 * checking if a position is free, gluing tetrominoes to the board
 * and clearing filled rows
 */
public class TetrisBoard implements Iterable<GridCell>, GridDimension {

  private final IGrid grid;
  private int clearedCount;

  // Constructor
  public TetrisBoard(IGrid grid) {
    this.grid = grid;
    this.clearedCount = 0;
  }

  public TetrisBoard(int rows, int cols) {
    this(new Grid(rows, cols, '-'));
    this.clearedCount = 0;
  }

  @Override
  public int rows() {
    return this.grid.rows();
  }

  @Override
  public int cols() {
    return this.grid.cols();
  }

  @Override
  public Iterator<GridCell> iterator() {
    return this.grid.iterator();
  }

  /**
   * Checks whether a position on the board is empty
   * 
   * @param pos the position to check
   * @return true if the position is empty (symbol is '-'), false otherwise
   */
  public boolean isFree(CellPosition pos) {
    if (this.grid.get(pos) == '-') {
      return true;
    }
    return false;
  }

  /**
   * Permanently places a tetromino on the board
   * 
   * @param tetromino the tetromino to glue to the board
   */
  public final void glueToBoard(Tetromino tetromino) {

    for (GridCell cell : tetromino) {

      CellPosition pos = cell.pos();
      this.grid.set(pos, cell.value());
    }
  }

  /**
   * Removes a all completely filled rows from the board
   * 
   * Rows above cleared rows are shifted downward
   * 
   * @return the number of the rows that were cleared
   */
  public int clearRows() {

    for (int r = this.grid.rows() - 1; r >= 0; r--) {
      if (isRowFilled(r)) {
        removeRow(r);
        r++;
        this.clearedCount++;
      }
    }
    return this.clearedCount;

  }

  /**
   * Checks whether a given row on the board is completely filled
   * 
   * @param row the row index to check
   * @return true if all cells in the row are occupied, false otherwise
   */
  private boolean isRowFilled(int row) {
    int notFreeCells = 0;

    for (GridCell cell : this.grid) {
      if (cell.pos().row() == row) {
        if (!isFree(cell.pos())) {
          notFreeCells++;
        }
      }
    }

    if (notFreeCells == this.grid.cols()) {
      return true;
    }
    return false;
  }

  /**
   * Removes a row from the board by shifting all rows above it down by one
   * 
   * The top row is then filled with empty cells
   * 
   * @param row the row to remove
   */
  private void removeRow(int row) {

    for (int r = row; r > 0; r--) {
      copyRowTo(r - 1, r);
    }
    fillTopRowWithEmpty(0);
  }

  /**
   * Copies the values of one given row to another given row
   * 
   * @param originalRow the row to copy from
   * @param targetRow the row to copy to
   */
  private void copyRowTo(int originalRow, int targetRow) {

    for (int col = 0; col < this.grid.cols(); col++) {
      CellPosition originalCell = new CellPosition(originalRow, col);
      CellPosition targetCell = new CellPosition(targetRow, col);

      grid.set(targetCell, this.grid.get(originalCell));
    }
  }

  /**
   * Fills a row with empty cells
   * 
   * @param row the row to fill with empty values
   */
  private void fillTopRowWithEmpty(int row) {
    for (int col = 0; col < this.grid.cols(); col++) {
      CellPosition cellToFill = new CellPosition(row, col);

      this.grid.set(cellToFill, '-');
    }
  }

  public void clearAll(){
    for(GridCell cell : this.grid){
      this.grid.set(cell.pos(), '-');
    }
  }

}
