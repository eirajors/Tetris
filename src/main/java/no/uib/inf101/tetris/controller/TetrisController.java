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
 * The controller listens to keyboard input and controls the game
 * by calling methods in the model
 */
public class TetrisController implements KeyListener{

    ControllableTetrisModel model; 
    TetrisView tetrisView;
    Timer timer; 
    TetrisSong song; 

    /**
     * Creates a controller for the Tetris game
     * 
     * @param model the controllable game model
     * @param tetrisView the view used to display the game
     */
    public TetrisController(ControllableTetrisModel model, TetrisView tetrisView){
        this.model = model;
        this.tetrisView = tetrisView;
        this.timer = new Timer(model.getTimerDelay(), this::clockTick);
        this.song = new TetrisSong(); 


        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);

        timer.start();
        song.run();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            model.moveTetromino(0, -1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            model.moveTetromino(0, 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Ned piltast ble trykket
            model.moveTetromino(1, 0);
            timer.restart();
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Opp piltast ble trykket
            model.rotateTetromino();
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Mellemromstasten ble trykket  
            //while(model.moveTetromino(1, 0));
            model.dropTetromino();
            timer.restart();
        }
        tetrisView.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    /**
     * Called by the timer to update the game state
     * @param a
     */
    void clockTick(ActionEvent a){
        GameState gameState = model.getGameState();

        if(gameState == GameState.ACTIVE_GAME){
            model.clockTick();
            updateTimerDelay();
            tetrisView.repaint();
        }
    }

    /**
     * Updates the timer delay based on the model's timer setting
     */
    void updateTimerDelay(){
        int delay = model.getTimerDelay(); 
        timer.setDelay(delay);
        timer.setInitialDelay(delay);
    }

}
