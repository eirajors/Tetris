package no.uib.inf101.tetris.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {

  private final Rectangle2D box;
  private final GridDimension gd;
  private final double margin;

  /**
   * Constructor
   * Creates a pixel from a given cellposition
   * 
   * @param box the graphics tool used to draw the cell
   * @param gd the grid dimensions in which where the cells are being drawn
   * @param margin the margin between each drawn cell
   */
  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /**
   * Draws a cell for a given cellposition
   * 
   * @param cellPosition the cellposition where the box is to be drawn
   * @return the cell that is being drawn
   */
  public Rectangle2D getBoundsForCell(CellPosition cellPosition) {
    double cellW = (this.box.getWidth() - this.margin * this.gd.cols() - this.margin) / this.gd.cols();
    double cellH = (this.box.getHeight() - this.margin * this.gd.rows() - this.margin) / this.gd.rows();
    double cellX = this.box.getX() + this.margin + (cellW + this.margin) * cellPosition.col();
    double cellY = this.box.getY() + this.margin + (cellH + this.margin) * cellPosition.row();
    
    return new Rectangle2D.Double(cellX, cellY, cellW, cellH);
  }

}
