package no.uib.inf101.tetris.model;

import java.util.ArrayList;
import java.util.Iterator;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public class TetrisBoard implements Iterable<GridCell>, GridDimension {

  private IGrid grid;

  // konstruktør
  public TetrisBoard(IGrid grid) {
    this.grid = grid;
  }

  public TetrisBoard(int rows, int cols) {
    this(new Grid(rows, cols, '-'));
  }

  @Override
  public int rows() {
    return grid.rows();
  }

  @Override
  public int cols() {
    return grid.cols();
  }

  @Override
  public Iterator<GridCell> iterator() {
    return grid.iterator();
  }

  public boolean isFree(CellPosition pos) {
    // hvis fargesymbolet i cellen er '-'(tom), returner true, hvis ikke false
    if (grid.get(pos) == '-') {
      return true;
    }
    return false;
  }

  // en hjelpemetode for å lime fast den fallende tetrominoen til brettet(kan være
  // i tetrisboard)
  // fargelegger rutene, slik at de forblir den fargen
  public final void glueToBoard(Tetromino tetromino) {

    for (GridCell cell : tetromino) {

      CellPosition pos = cell.pos();
      grid.set(pos, cell.value());
    }
  }

  public int clearRows() {
    int clearedCount = 0;

    for (int r = grid.rows() - 1; r >= 0; r--) {
      if (isRowFilled(r)) {
        removeRow(r);
        r++;
        clearedCount++;
      }
    }
    return clearedCount;

  }

  /**
   * 
   * @param row
   * @return
   */
  boolean isRowFilled(int row) {
    int notFreeCells = 0;

    for (GridCell cell : grid) {
      if (cell.pos().row() == row) {
        if (!isFree(cell.pos())) {
          notFreeCells++;
        }
      }
    }

    if (notFreeCells == grid.cols()) {
      return true;
    }
    return false;
  }

  void removeRow(int row) {

    for (int r = row; r > 0; r--) {
      copyRowTo(r - 1, r);
    }
    fillTopRowWithEmpty(0);
  }

  void copyRowTo(int originalRow, int targetRow) {

    for (int col = 0; col < grid.cols(); col++) {
      CellPosition originalCell = new CellPosition(originalRow, col);
      CellPosition targetCell = new CellPosition(targetRow, col);

      grid.set(targetCell, grid.get(originalCell));
    }
  }

  void fillTopRowWithEmpty(int row) {
    for (int col = 0; col < grid.cols(); col++) {
      CellPosition cellToFill = new CellPosition(row, col);

      grid.set(cellToFill, '-');
    }
  }

}
