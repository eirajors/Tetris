package no.uib.inf101.tetris;

import javax.swing.JFrame;

import no.uib.inf101.tetris.model.TetrisModel;

import no.uib.inf101.tetris.view.TetrisView;

public class TetrisMain {

  public static final String WINDOW_TITLE = "INF101 Tetris";

  public static void main(String[] args) {
    TetrisModel model = new TetrisModel();
    TetrisView view = new TetrisView(model);

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setContentPane(view);

    frame.pack();
    frame.setVisible(true);
  }

}
