package no.uib.inf101.tetris.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.TetrisModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Graphical view of the Tetris game
 * 
 * TetrisView is responsible for drawing the game board, the falling
 * tetromino, and the game on the screen.
 */
public class TetrisView extends JPanel {

  public static final int OUTERMARGIN = 15; //Margin between the board and the edge of the panel
  public static final int CELLMARGIN = 1; //Margin betweenindividual cells
  public static final int PREFERREDSIDESIZE = 30; //Preferred pixel size of each cell

  private final ColorTheme colorTheme;
  private final TetrisModel tetrisModel;
  private GameState gameState;

  /**
   * Creates a new view for the given TetrisModel
   * 
   * @param tetrisModel the model containing the current gameState
   */
  public TetrisView(TetrisModel tetrisModel) {

    this.tetrisModel = tetrisModel;

    this.colorTheme = new DefaultColorTheme();
    this.setBackground(colorTheme.getBackgroundColor());

    this.setFocusable(true);
    this.setPreferredSize(getDefaultSize(tetrisModel.getDimension()));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    drawGame(g2);
  }

  /**
   * Draws the entire game in its current GameState.
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void drawGame(Graphics2D g2) {
    Rectangle2D box = new Rectangle2D.Double(OUTERMARGIN, OUTERMARGIN, this.getWidth() - OUTERMARGIN * 2,
        this.getHeight() - OUTERMARGIN * 2);
    g2.setColor(this.colorTheme.getFrameColor());
    g2.fill(box);

    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box, this.tetrisModel.getDimension(),
        CELLMARGIN);

    drawCells(g2, this.tetrisModel.getTilesOnBoard(), converter, this.colorTheme);

    if (this.tetrisModel.getGameState() == GameState.ACTIVE_GAME) {
      drawCells(g2, this.tetrisModel.getFallingTetrominoCells(), converter, colorTheme);
    } 
    else if (this.tetrisModel.getGameState() == GameState.GAME_OVER) {
      Rectangle2D box2 = new Rectangle2D.Double(OUTERMARGIN, OUTERMARGIN, this.getWidth() - OUTERMARGIN * 2,
          this.getHeight() - OUTERMARGIN * 2);
      g2.setColor(new Color(0, 0, 0, 128));
      g2.fill(box2);

      g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
      g2.setColor(Color.RED);

      g2.drawString("Game Over!", this.getWidth() / 3, this.getHeight() / 2);
    }

  }

  /**
   * Draws a collection of GridCells using the provided converter and colortheme
   * 
   * @param g2 the graphics object used to draw the game
   * @param iterable the cells to draw
   * @param converter converts cells posistions to pixel coordinates
   * @param colorTheme provides colors for cells
   */
  private static void drawCells(Graphics2D g2, Iterable<GridCell> iterable, CellPositionToPixelConverter converter,
      ColorTheme colorTheme) {
    for (GridCell cell : iterable) {

      CellPosition cellPos = cell.pos();
      Rectangle2D cellArea = converter.getBoundsForCell(cellPos);

      Character colorSymbol = cell.value();

      Color colorCell = colorTheme.getCellColor(colorSymbol);

      g2.setColor(colorCell);
      g2.fill(cellArea);
    }
  }

  /**
   * Calculates the preferred size of the game panel given the grid dimensions
   * @param gd the grid dimensions of the game board
   * @return the preferred panel size
   */
  private static Dimension getDefaultSize(GridDimension gd) {
    int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
    int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
    return new Dimension(width, height);
  }

  private void drawHomeScreen(Graphics2D g2){
    
  }
  
}
