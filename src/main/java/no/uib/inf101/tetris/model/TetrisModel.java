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
 * This class controls the game logic including: movement of tetrominos,
 * rotation, dropping pieces, clearing rows, detecting game over
 */
public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

  private final TetrisBoard board;
  private final TetrominoFactory factory;
  private Tetromino fallingTet;
  private GameState gameState = GameState.HOME_SCREEN;
  private int score;
  private int level;
  private int tetrominosPlaced;
  private int clearedRows;

  /**
   * Creates a new TetrisModel with default board size of 15 rows and 10 columns
   * 
   * A new random tetromino is generated and positioned at the top center of the
   * board The game state is initialized to HOME_SCREEN
   */
  public TetrisModel() {
    this.board = new TetrisBoard(15, 10);
    this.factory = new RandomTetrominoFactory();

    this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(this.board);   

    this.score = 0;
    this.level = 1;
    this.tetrominosPlaced = 0;
    this.clearedRows = 0;
  }

  /**
   * Creates a new TetrisModel using an existing grid as the underlying board
   * 
   * @param grid an existing grid defining the board layout, cannot be null
   * 
   *             A new random tetromino is generated and positioned at the top
   *             center of the board The game state is initialized to HOME_SCREEN
   */
  public TetrisModel(IGrid grid) {
    this.board = new TetrisBoard(grid);
    this.factory = new RandomTetrominoFactory();

    this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(this.board);

    this.score = 0;

    this.level = 1;
    this.tetrominosPlaced = 0;
    this.clearedRows = 0;
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
  public void startGame() {
    this.gameState = GameState.ACTIVE_GAME;
    this.fallingTet = this.factory.getNext().shiftedToTopCenterOf(this.board);
    this.score = 0;
    this.level = 1;
    this.tetrominosPlaced = 0;
    this.clearedRows = 0;
    this.board.clearAll();
  }

  @Override
  public boolean moveTetromino(int deltaRow, int deltaCol) {
    if (this.gameState != GameState.ACTIVE_GAME) {
      return false;
    }
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
   * A help method that makes a new Tetromino, and centers it at the top of the
   * board
   * 
   * @return a new falling tetromino
   */
  private Tetromino getNewTetromino() {

    Tetromino newTetromino = this.factory.getNext().shiftedToTopCenterOf(this.board);

    return newTetromino;
  }

  /**
   * Glues the current falling tetromino to the board, and then gets a new falling
   * tetromino. Updates score and level
   */
  private void glueAndNewFalling() {

    this.board.glueToBoard(this.fallingTet);
    int clearedRowsCurrent = this.board.clearRows();
    int baseScore = 0;

    if (clearedRowsCurrent > clearedRows) {
      int linesCleared = clearedRowsCurrent - clearedRows;
      if (linesCleared == 1){
        baseScore = 100;
      }
      else if (linesCleared == 2){
        baseScore = 300;
      }
      else if (linesCleared == 3){
        baseScore = 500;
      }
      else if (linesCleared == 4){
        baseScore = 800;
      }
      this.score += baseScore * this.level;
      clearedRows = clearedRowsCurrent;
    }

    this.shouldLevelUp();

    Tetromino candidate = this.getNewTetromino();
    this.score += 30;
    this.tetrominosPlaced++;

    if (!(this.isLegalPosition(candidate))) {
      this.gameState = GameState.GAME_OVER;
    } else {
      this.fallingTet = candidate;
    }
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public int getTimerDelay() {
    if (level >= 9){
      return 200;
    }
    return 800 - (level * 75);
  }

  @Override
  public void clockTick() {
    boolean move = this.moveTetromino(1, 0);

    if (!move) {
      this.glueAndNewFalling();
    }
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public int getLinesCleared() {
    return this.board.clearRows();
  }

  /**
   * Checks if there are enough cleared lines to level up
   * 
   * @return true if there are enough cleared rows, false otherwise
   */
  private void shouldLevelUp() {
    if (this.board.clearRows() >= level * 10) {
      level++;
    }
  }

  @Override
  public void pauseGame() {
    if (this.gameState == GameState.ACTIVE_GAME) {
      this.gameState = GameState.PAUSED;
    } else if (this.gameState == GameState.PAUSED) {
      this.gameState = GameState.ACTIVE_GAME;
    }
  }

  /**
   * Resets the game when you go to homescreen
   */
  public void goToHomeScreen() {
    this.gameState = GameState.HOME_SCREEN;
    this.board.clearAll();
    this.score = 0;
    this.level = 1;
    this.tetrominosPlaced = 0;

    this.clearedRows = 0;

    this.fallingTet = this.factory.getNext().shiftedToTopCenterOf(this.board);
  }

  /**
   * Creates a shadow of the Tetromino that is current falling tetromino
   * 
   * @return a shadow of the tetromino at the place where it would fall
   */
  @Override
  public Tetromino shadowTetromino() {
    Tetromino shadowTetromino = this.fallingTet;

    while (true) {
      Tetromino candidateShadow = shadowTetromino.shiftedBy(1, 0);

      if (isLegalPosition(candidateShadow)) {
        shadowTetromino = candidateShadow;
      } else {
        break;
      }
    }
    return shadowTetromino;
  }

}
