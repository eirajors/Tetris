package no.uib.inf101.tetris.view;

import javax.swing.ImageIcon;
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
import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * Graphical view of the Tetris game
 * 
 * TetrisView is responsible for drawing the game board, the falling tetromino,
 * and the game on the screen.
 */
public class TetrisView extends JPanel {

  public static final int TOPMARGIN = 10; // Margin between the board and the edge of the panel
  public static final int SIDEMARGIN = 150; // Margin between the side of the board and the edge of the panel
  public static final int CELLMARGIN = 1; // Margin betweenindividual cells
  public static final int PREFERREDSIDESIZE = 30; // Preferred pixel size of each cell

  private final ColorTheme colorTheme;
  private final TetrisModel tetrisModel;
  private GameState gameState;

  private final Image logo;

  /**
   * Creates a new view for the given TetrisModel
   * 
   * @param tetrisModel the model containing the current gameState
   */
  public TetrisView(TetrisModel tetrisModel) {

    this.tetrisModel = tetrisModel;
    this.logo = new ImageIcon(getClass().getResource("Tetris_logo.png")).getImage(); // https://stackoverflow.com/questions/29580664/java-how-to-add-an-image-to-resource

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
    Rectangle2D box = new Rectangle2D.Double(SIDEMARGIN, TOPMARGIN, this.getWidth() - SIDEMARGIN * 2,
        this.getHeight() - TOPMARGIN * 2);
    g2.setColor(this.colorTheme.getFrameColor());
    g2.fill(box);

    CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box, this.tetrisModel.getDimension(),
        CELLMARGIN);

    drawCells(g2, this.tetrisModel.getTilesOnBoard(), converter, this.colorTheme);

    if (this.tetrisModel.getGameState() == GameState.ACTIVE_GAME) {
      drawCellsShadow(g2, this.tetrisModel.shadowTetromino(), converter, colorTheme);
      drawCells(g2, this.tetrisModel.getFallingTetrominoCells(), converter, colorTheme);
      drawScore(g2);
      drawLevel(g2);
      drawLines(g2);

      g2.setColor(Color.WHITE);
      String pauseString = "press 'P' to pause";
      g2.setFont(new Font("Dialoginput", Font.BOLD, 15));
      int pauseWidth = g2.getFontMetrics().stringWidth(pauseString);

      g2.drawString(pauseString, this.getWidth() - pauseWidth - 8, this.getHeight() / 2 + 100);
    }

    else if (this.tetrisModel.getGameState() == GameState.GAME_OVER) {
      Rectangle2D box2 = new Rectangle2D.Double(SIDEMARGIN, TOPMARGIN, this.getWidth() - SIDEMARGIN * 2,
          this.getHeight() - TOPMARGIN * 2);
      g2.setColor(new Color(0, 0, 0, 128));
      g2.fill(box2);

      g2.setColor(Color.WHITE);
      String string = "Game Over!";
      g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
      int textWidth = g2.getFontMetrics().stringWidth(string);

      g2.drawString(string, this.getWidth() / 2 - textWidth / 2, this.getHeight() / 2);

      g2.setColor(Color.WHITE);
      int score = this.tetrisModel.getScore();
      String scoreString = "Your score was: " + String.valueOf(score);
      g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
      int scoreStringWidth = g2.getFontMetrics().stringWidth(scoreString);

      g2.drawString(scoreString, (this.getWidth() / 2) - (scoreStringWidth / 2), this.getHeight() / 2 + 40);

      goToHomeScreen(g2);
      g2.setColor(Color.white);
      String playAgain = "press 'DOWN' to go to play again";
      g2.setFont(new Font("Dialoginput", Font.BOLD, 15));
      int textWidth2 = g2.getFontMetrics().stringWidth(playAgain);

      g2.drawString(playAgain, this.getWidth() / 2 - textWidth2 / 2, this.getHeight() / 2 + 100);
    }

    else if (this.tetrisModel.getGameState() == GameState.HOME_SCREEN) {
      drawHomeScreen(g2);
    }

    else if (this.tetrisModel.getGameState() == GameState.PAUSED) {

      drawScore(g2);
      drawLevel(g2);
      Rectangle2D paused = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
      g2.setColor(new Color(0, 0, 0, 128));
      g2.fill(paused);

      g2.setColor(Color.RED);
      String pausedString = "PAUSED";
      g2.setFont(new Font("Dialoginput", Font.BOLD, 25));
      int textWidth = g2.getFontMetrics().stringWidth(pausedString);

      g2.drawString(pausedString, this.getWidth() / 2 - textWidth / 2, this.getHeight() / 2);

      g2.setColor(Color.white);
      String unpause = "press 'P' to resume";
      g2.setFont(new Font("Dialoginput", Font.BOLD, 15));
      int textWidth2 = g2.getFontMetrics().stringWidth(unpause);

      g2.drawString(unpause, this.getWidth() / 2 - textWidth2 / 2, this.getHeight() / 2 + 40);

      goToHomeScreen(g2);

    }

  }

  /**
   * Draws a collection of GridCells using the provided converter and colortheme
   * 
   * @param g2         the graphics object used to draw the game
   * @param iterable   the cells to draw
   * @param converter  converts cells posistions to pixel coordinates
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
  private static void drawCellsShadow(Graphics2D g2, Iterable<GridCell> iterable, CellPositionToPixelConverter converter,
      ColorTheme colorTheme) {
    for (GridCell cell : iterable) {

      CellPosition cellPos = cell.pos();
      Rectangle2D cellArea = converter.getBoundsForCell(cellPos);

      g2.setColor(Color.DARK_GRAY);
      g2.fill(cellArea);
    }
  }

  /**
   * Calculates the preferred size of the game panel given the grid dimensions
   * 
   * @param gd the grid dimensions of the game board
   * @return the preferred panel size
   */
  private static Dimension getDefaultSize(GridDimension gd) {
    int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * SIDEMARGIN);
    int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.cols() + 1) + 2 * TOPMARGIN);
    return new Dimension(width, height);
  }

  /**
   * Help method to draw the screen
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void drawHomeScreen(Graphics2D g2) {
    Rectangle2D box3 = new Rectangle2D.Double(TOPMARGIN, TOPMARGIN, this.getWidth() - TOPMARGIN * 2,
        this.getHeight() - TOPMARGIN * 2);
    g2.setColor(Color.DARK_GRAY);
    g2.fill(box3);

    int logoWidth = 300;
    int logoHeight = 150;

    int x = (this.getWidth() - logoWidth) / 2;
    int y = (this.getHeight() - logoHeight) / 2;

    g2.drawImage(this.logo, x, y, logoWidth, logoHeight, this);

    Rectangle2D clickToPlay = new Rectangle2D.Double(SIDEMARGIN + 90, 140, TOPMARGIN - 40, 180);

    g2.setColor(Color.GREEN);
    g2.fill(clickToPlay);

    g2.setColor(Color.WHITE);
    String string = "PRESS DOWN TO PLAY";
    g2.setFont(new Font("Dialoginput", Font.BOLD, 22));
    int textWidth = g2.getFontMetrics().stringWidth(string);

    g2.drawString(string, this.getWidth() / 2 - textWidth / 2, y + logoHeight + 30);

  }

  /**
   * Help method to draw the score when the game is active
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void drawScore(Graphics2D g2) {
    Rectangle2D scoreBoxFrame = new Rectangle2D.Double(20, this.getHeight() / 2 - 10, 120, 220);
    g2.setColor(Color.WHITE);
    g2.fill(scoreBoxFrame);

    Rectangle2D scoreBox = new Rectangle2D.Double(30, this.getHeight() / 2, 100, 200);
    g2.setColor(Color.DARK_GRAY);
    g2.fill(scoreBox);

    g2.setColor(Color.WHITE);
    String scoreString = "SCORE";
    g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
    g2.drawString(scoreString, 46, this.getHeight() / 2 + 20);

    int currentScoreInt = this.tetrisModel.getScore();
    String currentScore = String.valueOf(currentScoreInt);
    g2.setFont(new Font("Dialoginput", Font.BOLD, 18));
    g2.drawString(currentScore, 65, this.getHeight() / 2 + 50);
  }

  /**
   * Help method to draw the level when the game is active
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void drawLevel(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    String scoreString = "LEVEL";
    g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
    g2.drawString(scoreString, 46, this.getHeight() / 2 + 80);

    int currentLevelInt = this.tetrisModel.getLevel();
    String currentLevel = String.valueOf(currentLevelInt);
    g2.setFont(new Font("Dialoginput", Font.BOLD, 18));
    g2.drawString(currentLevel, 65, this.getHeight() / 2 + 100);
  }

  /**
   * Help method to draw the number of lines cleared when the game is active
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void drawLines(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    String scoreString = "LINES";
    g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
    g2.drawString(scoreString, 46, this.getHeight() / 2 + 140);

    int currentClearedLinesInt = this.tetrisModel.getLinesCleared();
    String currentClearedLines = String.valueOf(currentClearedLinesInt);
    g2.setFont(new Font("Dialoginput", Font.BOLD, 18));
    g2.drawString(currentClearedLines, 65, this.getHeight() / 2 + 160);
  }

  /**
   * Help method to draw how to go to homescreen
   * 
   * @param g2 the graphics object used to draw the game
   */
  private void goToHomeScreen(Graphics2D g2) {
    g2.setColor(Color.white);
    String homescreen = "press 'H' to go to homescreen";
    g2.setFont(new Font("Dialoginput", Font.BOLD, 15));
    int textWidth2 = g2.getFontMetrics().stringWidth(homescreen);

    g2.drawString(homescreen, this.getWidth() / 2 - textWidth2 / 2, this.getHeight() / 2 + 80);
  }

}