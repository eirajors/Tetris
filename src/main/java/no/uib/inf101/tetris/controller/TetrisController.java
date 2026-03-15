package no.uib.inf101.tetris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

/**
 * Handles user input and connects the Tetris model with the view
 * 
 * The controller listens to keyboard input and controls the game by calling
 * methods in the model
 */
public class TetrisController implements KeyListener {

  private final ControllableTetrisModel model;
  private final TetrisView tetrisView;
  private final Timer timer;
  private final TetrisSong song;

  /**
   * Creates a controller for the Tetris game
   * 
   * @param model      the controllable game model
   * @param tetrisView the view used to display the game
   */
  public TetrisController(ControllableTetrisModel model, TetrisView tetrisView) {
    this.model = model;
    this.tetrisView = tetrisView;
    this.timer = new Timer(model.getTimerDelay(), this::clockTick);
    this.song = new TetrisSong();

    this.tetrisView.addKeyListener(this);
    this.tetrisView.setFocusable(true);

    this.timer.start();
    this.song.run();

  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {

    if (this.model.getGameState() == GameState.ACTIVE_GAME) {
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        this.model.moveTetromino(0, -1);
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        this.model.moveTetromino(0, 1);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        // Ned piltast ble trykket
        this.model.moveTetromino(1, 0);
        this.timer.restart();
      } else if (e.getKeyCode() == KeyEvent.VK_UP) {
        // Opp piltast ble trykket
        this.model.rotateTetromino();
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        // Mellemromstasten ble trykket
        // while(model.moveTetromino(1, 0));
        this.model.dropTetromino();
        this.timer.restart();
      } else if (e.getKeyCode() == KeyEvent.VK_P) {
        this.model.pauseGame();
      }
      this.tetrisView.repaint();
    } else if (this.model.getGameState() == GameState.HOME_SCREEN) {
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        this.model.startGame();
        this.tetrisView.repaint();
      }
    } else if (this.model.getGameState() == GameState.PAUSED) {
      if (e.getKeyCode() == KeyEvent.VK_P) {
        this.model.pauseGame();
        this.tetrisView.repaint();
      } else if (e.getKeyCode() == KeyEvent.VK_H) {
        this.model.goToHomeScreen();
        this.tetrisView.repaint();
      }
    } else if (this.model.getGameState() == GameState.GAME_OVER) {
      if (e.getKeyCode() == KeyEvent.VK_H) {
        this.model.goToHomeScreen();
        this.tetrisView.repaint();
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        this.model.startGame();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  /**
   * Called by the timer on each tick to update the game state
   * 
   * @param a
   */
  void clockTick(ActionEvent a) {

    if (model.getGameState() == GameState.ACTIVE_GAME) {
      this.model.clockTick();
      this.updateTimerDelay();
      this.tetrisView.repaint();
    }
  }

  /**
   * Updates the timer delay based on the model's timer setting
   */
  void updateTimerDelay() {
    int delay = this.model.getTimerDelay();
    this.timer.setDelay(delay);
    this.timer.setInitialDelay(delay);
  }

}
