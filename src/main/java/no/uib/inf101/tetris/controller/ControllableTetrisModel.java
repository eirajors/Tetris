package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

/**
 * Interface defining the actions that can control a Tetris game
 */
public interface ControllableTetrisModel {

  /**
   * Attempts to move the falling tetromino
   * 
   * @param deltaRow number of rows to move
   * @param deltaCol number of columns to move
   * @return true if the move was successful, false otherwise
   */
  public boolean moveTetromino(int deltaRow, int deltaCol);

  /**
   * Attempts to rotate the falling tetromino
   * 
   * @return true if the rotation was successful, false otherwise
   */
  public boolean rotateTetromino();

  /**
   * Drops the falling tetromino to the lowest possible position
   * and gets a new tetromino
   */
  public void dropTetromino();

  /**
   * Returns the current state of the game
   * 
   * @return the current GameState
   */
  GameState getGameState();

  /**
   * Returns the delay used by the game timer
   * 
   * @return timer delay in milliseconds
   */
  public int getTimerDelay();

  /**
   * Updates the game state for each timer tick
   */
  public void clockTick();

  /**
   * Starts the game 
   */
  public void startGame();

  /** If the game state is active, it will pause the game. If it is paused, it will unpause it */
  public void pauseGame();

  /** Can go to homescreen, either from paused game or from game over
   * Will reset the game as well
   */
  public void goToHomeScreen();

}
