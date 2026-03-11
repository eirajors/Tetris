package no.uib.inf101.grid;

/** Represents a single cell in a grid
 * 
 * A GridCell contains a position in the grid and the callue stored
 * at that position
 * 
 * @param pos the position of the cell in the grid
 * @param value the value stored in the cell
 */
public record GridCell(CellPosition pos, Character value) {
}
