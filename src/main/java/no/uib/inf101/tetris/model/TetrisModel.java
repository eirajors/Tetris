package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

/**
 * The main game model for Tetris
 * 
 * This class controls the game logic including: movement of tetrominos, rotation, 
 * dropping pieces, clearing rows, detecting game over
 */
public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

  private final TetrisBoard board;
  private final TetrominoFactory factory;
  private Tetromino fallingTet;
  private GameState gameState = GameState.ACTIVE_GAME;

  /**
   * Creates a new TetrisModel with default board size of 15 rows and 10 columns
   * 
   * A new random tetromino is generated and positioned at the top center of the board
   * The game state is initialized to ACTIVE_GAME
   */
  public TetrisModel() {
    this.board = new TetrisBoard(15, 10);
    this.factory = new RandomTetrominoFactory();

    this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(this.board);
  }
  /**
   * Creates a new TetrisModel using an existing grid as the underlying board
   * 
   * @param grid an existing grid defining the board layout, cannot be null
   * 
   * A new random tetromino is generated and positioned at the top center of the board
   * The game state is initialized to ACTIVE_GAME
   */
  public TetrisModel(IGrid grid) {
    this.board = new TetrisBoard(grid);
    this.factory = new RandomTetrominoFactory();

    this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(this.board);
  }

  @Override
  public GridDimension getDimension() {
    return this.board;
  }

  @Override
  public Iterable<GridCell> getTilesOnBoard() {
    return this.board;
  }

  @Override
  public Iterable<GridCell> getFallingTetrominoCells() {
    return this.fallingTet;
  }

  @Override
  public boolean moveTetromino(int deltaRow, int deltaCol) {

    Tetromino candidateTetromino = this.fallingTet.shiftedBy(deltaRow, deltaCol);

    if (!this.isLegalPosition(candidateTetromino)) {
      return false;
    }

    this.fallingTet = candidateTetromino;
    return true;
  }

  /**
   * Checks if every cell in a given tetromino is on the grid 
   * 
   * @param tetromino the tetromino which cells are iterated through
   * @return true if the whole tetromino is on the grid, and false otherwise
   */
  private boolean isLegalPosition(Tetromino tetromino) {
    for (GridCell cell : tetromino) {
      if (!this.board.positionIsOnGrid(cell.pos())) {
        return false;
      }
      if (!(this.board.isFree(cell.pos()))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean rotateTetromino() {
    Tetromino candTetromino = this.fallingTet.rotatedTetromino();

    if (!this.isLegalPosition(candTetromino)) {
      return false;
    }

    this.fallingTet = candTetromino;

    return true;
  }

  @Override
  public void dropTetromino() {
    while (this.moveTetromino(1, 0)) {
    }
    this.glueAndNewFalling();

  }

  /**
   * A help method that makes a new Tetromino, and centers it at the top 
   * of the board
   * 
   * @return a new falling tetromino
   */
  private Tetromino getNewTetromino() {

    Tetromino newTetromino = this.factory.getNext().shiftedToTopCenterOf(board);

    return newTetromino;
  }

  /**
   * Glues the current falling tetromino to the board, and then
   * gets a new falling tetromino 
   */
  private void glueAndNewFalling() {
    this.board.glueToBoard(this.fallingTet);
    this.board.clearRows();

    Tetromino candidate = this.getNewTetromino(); 

    if (!(this.isLegalPosition(candidate))) {
      this.gameState = GameState.GAME_OVER;
    }
    else{
      this.fallingTet = candidate; 
    }
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public int getTimerDelay() {
    return 1000;
  }

  @Override
  public void clockTick() {
    boolean move = this.moveTetromino(1, 0);

    if (!move) {
      this.glueAndNewFalling();
    }
  }

}
