package no.uib.inf101.tetris;

import javax.swing.JFrame;

import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.controller.TetrisController;
//import no.uib.inf101.grid.Grid;
import no.uib.inf101.tetris.model.TetrisModel;

import no.uib.inf101.tetris.view.TetrisView;

public class TetrisMain {

  public static final String WINDOW_TITLE = "INF101 Tetris";

  public static void main(String[] args) {

    //Grid grid = new Grid(15,10,'-');
    // grid.set(new CellPosition(0, 0), 'r'); // Rød i øvre venstre hjørne
    // grid.set(new CellPosition(grid.rows()-1, 0), 'b'); // Blå i nedre venstre hjørne
    // grid.set(new CellPosition(0, grid.cols()-1), 'y'); // Gul i øvre høyre hjørne
    // grid.set(new CellPosition(grid.rows()-1, grid.cols()-1), 'w');   // Hvit i nedre høyre hjørne		

    
    //TetrominoFactory firstTetromino = new RandomTetrominoFactory();

    //TetrisModel model = new TetrisModel(grid);

    TetrisModel model = new TetrisModel();
    TetrisView view = new TetrisView(model);
    new TetrisController(model, view);
    

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(view);

    frame.pack();
    frame.setVisible(true);
  
}
}
