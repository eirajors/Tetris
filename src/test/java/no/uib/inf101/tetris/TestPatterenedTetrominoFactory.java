package no.uib.inf101.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.tetris.model.tetromino.Tetromino;

public class TestPatterenedTetrominoFactory {

  @Test
  void TestGetNext() {
    PatternedTetrominoFactory factory = new PatternedTetrominoFactory("LSJ");

    assertEquals(Tetromino.newTetromino('L'), (factory.getNext()));
    assertEquals(Tetromino.newTetromino('S'), (factory.getNext()));
    assertEquals(Tetromino.newTetromino('J'), (factory.getNext()));
    assertEquals(Tetromino.newTetromino('L'), (factory.getNext()));
    assertEquals(Tetromino.newTetromino('S'), (factory.getNext()));
    assertEquals(Tetromino.newTetromino('J'), (factory.getNext()));

  }

}
