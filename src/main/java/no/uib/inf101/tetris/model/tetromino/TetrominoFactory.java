package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {

  /**
   * Returns a randomly generated Tetromino
   * 
   * @return a new Tetromino with a random shape
   */
  public Tetromino getNext();

}
