package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import no.uib.inf101.tetris.model.tetromino.Tetromino;

public class TestTetromino {

  @Test
  void testHashCode() {

    Tetromino obj1 = Tetromino.newTetromino('T');
    Tetromino obj2 = Tetromino.newTetromino('T');

    int obj1Hash = obj1.hashCode();
    assertEquals(obj1, obj2);
    assertEquals(obj1Hash, obj2.hashCode());
  }

  @Test
  void testEquals() {
    Tetromino obj1 = Tetromino.newTetromino('T');
    Tetromino obj2 = Tetromino.newTetromino('T');
    Tetromino obj3 = Tetromino.newTetromino('S');

    assertTrue(obj1.equals(obj2));
    assertTrue(obj2.equals(obj1));
    assertFalse(obj1.equals(obj3));
    assertFalse(obj3.equals(obj1));
  }

  @Test
  void testIterator() {
    Tetromino obj1 = Tetromino.newTetromino('T');
    int count = 0;
    List<GridCell> values = new ArrayList<>();

    for (GridCell cell : obj1) {
      count++;
      values.add(cell);
    }
    assertEquals(4, count);
    assertEquals(4, values.size());
    assertTrue(values.contains(new GridCell(new CellPosition(1, 2), 'T')));
    assertFalse(values.contains(new GridCell(new CellPosition(0, 2), 'T')));
  }

  @Test
  void testShifting() {
    Tetromino obj = Tetromino.newTetromino('T');

    Tetromino shiftedObj = obj.shiftedBy(2, 4);

    assertEquals(shiftedObj.pos, new CellPosition(2, 4));
  }

  @Test
  void testRotatedTetromino() {
    boolean[][] rotatedDummyMatrise = { { false, true, false }, { true, true, false }, { false, true, false } };

    Tetromino tetromino = Tetromino.newTetromino('T');
    Tetromino rotated = tetromino.rotatedTetromino();
    boolean[][] matriseRotatedMatrise = rotated.getMatrise();

    assertTrue(Arrays.deepEquals(rotatedDummyMatrise, matriseRotatedMatrise));
    assertFalse(Arrays.deepEquals(matriseRotatedMatrise, tetromino.getMatrise()));
  }

  @Test
  void testClearRows() {
    Grid grid = new Grid(5, 5, '-');
    TetrisBoard board = new TetrisBoard(grid);

    for (GridCell cell : grid) {
      CellPosition cellPos = cell.pos();
      grid.set(cellPos, 'R');
    }

    for (GridCell cell2 : grid) {
      assertTrue(grid.get(cell2.pos()) == 'R');
    }

    assertTrue(board.clearRows() == 5);

    for (GridCell cellAfterClear : board) {
      assertTrue(grid.get(cellAfterClear.pos()) == '-');
    }
  }

  @Test 
  void testIsFree() {
    Grid grid = new Grid(5, 5, '-');
    TetrisBoard board = new TetrisBoard(grid);
    for (int r = 0; r < board.rows(); r++){
      for (int c = 0; c < board.cols(); c++){
        if (r == 3) {
          CellPosition posNotFree = new CellPosition(r, c);
          grid.set(posNotFree, 'R');
          assertFalse(board.isFree(posNotFree));
        }
        else if(r != 3){
          CellPosition posFree = new CellPosition(r, c);
          assertTrue(board.isFree(posFree));
        }
      }
    }
  }

  @Test
  void testClearAll() {
    

  }

}
