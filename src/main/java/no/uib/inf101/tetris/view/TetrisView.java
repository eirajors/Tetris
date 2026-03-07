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

public class TetrisView extends JPanel {

    public static final int OUTERMARGIN = 15;
    public static final int CELLMARGIN = 1;
    public static final int PREFERREDSIDESIZE = 30;
    

    private ColorTheme colorTheme;
    private TetrisModel tetrisModel;
    private GameState gameState;
    
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

    private void drawGame(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN,
                this.getWidth() - OUTERMARGIN * 2,
                this.getHeight() - OUTERMARGIN * 2);
        g2.setColor(colorTheme.getFrameColor());
        g2.fill(box);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                tetrisModel.getDimension(), CELLMARGIN);
                
        drawCells(g2, tetrisModel.getTilesOnBoard(), converter, colorTheme);
        if(tetrisModel.getGameState() == GameState.ACTIVE_GAME){
            drawCells(g2, tetrisModel.getFallingTetrominoCells(), converter, colorTheme);
        }
        else if(tetrisModel.getGameState() == GameState.GAME_OVER){
            Rectangle2D box2 = new Rectangle2D.Double(
                    OUTERMARGIN,
                    OUTERMARGIN,
                    this.getWidth() - OUTERMARGIN * 2,
                    this.getHeight() - OUTERMARGIN * 2);
            g2.setColor(new Color(0, 0, 0, 128));
            g2.fill(box2);

            g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
            g2.setColor(Color.RED);

            g2.drawString("Game Over!", this.getWidth()/3, this.getHeight()/2);
        }

    }

    private static void drawCells(Graphics2D g2, Iterable<GridCell> iterable,
            CellPositionToPixelConverter converter, ColorTheme colorTheme) {
        for(GridCell cell : iterable){
            
            CellPosition cellPos = cell.pos();
            Rectangle2D cellArea = converter.getBoundsForCell(cellPos);

            Character colorSymbol = cell.value();

            Color colorCell = null;
            if(colorSymbol=='-'){
                colorCell = Color.WHITE;
            }
            
                colorCell = colorTheme.getCellColor(colorSymbol);
            

            g2.setColor(colorCell);
            g2.fill(cellArea);
        }
    }
    

    private static Dimension getDefaultSize(GridDimension gd) {
        int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        return new Dimension(width, height);
    }

}
