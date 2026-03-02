package no.uib.inf101.tetris.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.tetris.view.TetrisView;

public class TetrisController implements KeyListener{

    ControllableTetrisModel model; 
    TetrisView tetrisView;

    public TetrisController(ControllableTetrisModel model, TetrisView tetrisView){
        this.model = model;
        this.tetrisView = tetrisView;

        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);

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
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Opp piltast ble trykket
            model.rotateTetromino();
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Mellemromstasten ble trykket  
            //while(model.moveTetromino(1, 0));
            model.dropTetromino();
        }
        tetrisView.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
