package no.uib.inf101.tetris.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public class TetrisBoardTest {
  // ferdig
  @Test
  void testConstructStartsWithEmpty() {
    int rows = 3;
    int cols = 5;
    Grid grid = new Grid(rows, cols, '-');
    TetrisBoard board = new TetrisBoard(grid);
    int countFree = 0;
    int countTaken = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        CellPosition pos = new CellPosition(r, c);
        if (board.isFree(pos)) {
          countFree++;
        } else {
          countTaken++;
        }
      }
    }

    assertEquals(rows * cols, countFree);
    assertEquals(0, countTaken);
  }

  // gjør??
  @Test
  void testConstructs() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Grid(3, -1);
    }, "Can not have negative number of cols");
    assertThrows(IllegalArgumentException.class, () -> {
      new Grid(-1, 3);
    }, "Can not have negative number of rows");
  }

  // gjør??
  @Test
  void testIterator() {
    IGrid grid = new Grid(3, 2, 'x');
    grid.set(new CellPosition(0, 0), 'a');
    grid.set(new CellPosition(1, 1), 'b');
    grid.set(new CellPosition(2, 1), 'c');

    List<GridCell> items = new ArrayList<>();
    for (GridCell coordinateItem : grid) {
      items.add(coordinateItem);
    }

    assertEquals(3 * 2, items.size());
    assertTrue(items.contains(new GridCell(new CellPosition(0, 0), 'a')));
    assertTrue(items.contains(new GridCell(new CellPosition(1, 1), 'b')));
    assertTrue(items.contains(new GridCell(new CellPosition(2, 1), 'c')));
    assertTrue(items.contains(new GridCell(new CellPosition(0, 1), 'x')));
  }

  // gjør??
  @Test
  void testPositionIsOnGrid() {
    IGrid grid = new Grid(3, 2, 'z');

    assertTrue(grid.positionIsOnGrid(new CellPosition(2, 1)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(3, 1)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(2, 2)));

    assertTrue(grid.positionIsOnGrid(new CellPosition(0, 0)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(-1, 0)));
    assertFalse(grid.positionIsOnGrid(new CellPosition(0, -1)));

    assertFalse(grid.positionIsOnGrid(null));
  }

  // ferdig
  @Test
  void testIsFree() {
    int rows = 3;
    int cols = 5;
    Grid grid = new Grid(rows, cols, '-');

    grid.set(new CellPosition(0, 1), 'r');
    grid.set(new CellPosition(2, 4), 'b');
    grid.set(new CellPosition(2, 1), 'r');

    TetrisBoard board = new TetrisBoard(grid);

    assertTrue(board.isFree(new CellPosition(0, 0)));
    assertTrue(board.isFree(new CellPosition(1, 0)));
    assertFalse(board.isFree(new CellPosition(0, 1)));
    assertFalse(board.isFree(new CellPosition(2, 4)));
    assertFalse(board.isFree(new CellPosition(2, 1)));

  }

  // tester om det er like mange emptycells etter liming som at
  // hadde vært om det var en tetromino der.
  // tester og om verdien i cellen er lik som det grid sin verdi, altså lik farge
  @Test
  void testGlueToBoard() {
    Grid grid = new Grid(15, 10, '-');

    TetrisBoard board = new TetrisBoard(grid);

    Tetromino tetromino = Tetromino.newTetromino('T');
    tetromino.shiftedBy(5, 5);

    board.glueToBoard(tetromino);

    int emptyCells = 0;
    for (GridCell cell : board) {
      if (board.isFree(cell.pos())) {
        emptyCells++;
      }
    }

    int tetCells = 0;
    for (GridCell tetCell : tetromino) {
      tetCells++;

      CellPosition pos = tetCell.pos();
      assertEquals(tetCell.value(), grid.get(pos));
    }
    assertEquals(emptyCells, 15 * 10 - tetCells);
  }

  @Test
  public void testFullRowClears() {

    int row = 10;

    Grid grid = new Grid(15, 10, '-');
    for (CellPosition pos : grid.positions()) {
      if (pos.row() == 10) {
        grid.set(pos, 'I');
      }
      if (pos.row() > 10) {
        if ((pos.row() + pos.col()) % 2 == 0)
          grid.set(pos, 'O');
      }
    }
    TetrisBoard board = new TetrisBoard(grid);
    int cleared = board.clearRows();

    assertEquals(1, cleared);

    for (int col = 0; col < 10; col++) {
      assertTrue(board.isFree(new CellPosition(row, col)));
    }
  }

}