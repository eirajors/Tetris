package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

/** 
 * Represents a Tetris piece, Tetromino
 * 
 * A Tetromino consits of a symbol, a boolean matrix, describing its shape
 * and a position representing the top-left corner of the matrix
 * 
 * The class is immutable: operations such as shiting or rotating return
 * new Tetromino objects
 */
public class Tetromino implements Iterable<GridCell> {

  private static final List<Character> LEGAL_SYMBOLS = 
    List.of('L', 'J', 'S', 'Z', 'T', 'I', 'O');
  private final char symbol;
  private final boolean[][] matrise;
  public final CellPosition pos;

  private static final HashMap<Character, boolean[][]> MATRISER = new HashMap<>();

  static {
    MATRISER.put('L', new boolean[][] { { false, false, false }, { true, true, true }, { true, false, false } });
    MATRISER.put('J', new boolean[][] { { false, false, false }, { true, true, true }, { false, false, true } });
    MATRISER.put('S', new boolean[][] { { false, false, false }, { false, true, true }, { true, true, false } });
    MATRISER.put('Z', new boolean[][] { { false, false, false }, { true, true, false }, { false, true, true } });
    MATRISER.put('T', new boolean[][] { { false, false, false }, { true, true, true }, { false, true, false } });
    MATRISER.put('I', new boolean[][] { { false, false, false, false }, { true, true, true, true },
        { false, false, false, false }, { false, false, false, false } });
    MATRISER.put('O', new boolean[][] { { false, false, false, false }, { false, true, true, false },
        { false, true, true, false }, { false, false, false, false } });
  }

  // Constructor
  private Tetromino(char symbol, boolean[][] matrise, CellPosition pos) {
    this.symbol = symbol;
    this.matrise = matrise;
    this.pos = pos;
  }

  /**
   * Creates a new Tetromino with the given symbol
   * 
   * @param symbol the type of tetromino (L, J, S, Z, T, I, O)
   * @return a new Tetromino
   * @throws IlleagalArgumentException if the symbol is not valid
   */
  public static Tetromino newTetromino(char symbol) {

    if (!LEGAL_SYMBOLS.contains(symbol)) {
      throw new IllegalArgumentException();
    }
    boolean[][] newMatrise = MATRISER.get(symbol);

    return new Tetromino(symbol, newMatrise, new CellPosition(0, 0));
  }

  /** 
   * Returns a new Tetromino shifted by the given row and column
   * 
   * @param deltaRow number of rows to move
   * @param deltaCol number of columns to move
   * @return a new shifted Tetromino
   */
  public Tetromino shiftedBy(int deltaRow, int deltaCol) {
    int newRow = pos.row() + deltaRow;
    int newCol = pos.col() + deltaCol;

    return new Tetromino(symbol, matrise, new CellPosition(newRow, newCol));
  }

  /**
   * Returns a new tetromino positioned at the top center of the grid
   * 
   * @param grid the grid where the tetromino should be placed
   * @return a new Tetromino positioned at the top center
   */
  public Tetromino shiftedToTopCenterOf(GridDimension grid) {
    int centerCol = (grid.cols() / 2) - (matrise[0].length / 2);

    return new Tetromino(symbol, matrise, new CellPosition(0, centerCol));
  }

  @Override
  public Iterator<GridCell> iterator() {

    ArrayList<GridCell> partOfTet = new ArrayList<>();
    // for-loop to find the GridCells that are a part of the Tetromino
    for (int r = 0; r < matrise.length; r++) {
      for (int c = 0; c < matrise[r].length; c++) {
        if (matrise[r][c] == true) {
          int row = pos.row() + r;
          int col = pos.col() + c;
          partOfTet.add(new GridCell(new CellPosition(row, col), symbol));
        }
      }
    }

    return partOfTet.iterator();
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false; // stackOverflow, link under

    Tetromino tetrObject = (Tetromino) obj;

    return (Arrays.deepEquals(tetrObject.matrise, this.matrise) && (tetrObject.symbol == this.symbol))
        && (tetrObject.pos).equals(this.pos);
  }
  // kilde:
  // https://medium.com/@niketl16/equals-and-hashcode-in-java-understanding-overriding-and-best-practices-7775c8fc279c

  @Override
  public int hashCode() {
    return Objects.hash(symbol, Arrays.deepHashCode(matrise), pos);
  }

  /**
   * Returns a new Tetromino rotated 90 degrees clockwise
   * 
   * @param tetromino the tetromino to rotate
   * @return a rotated Tetromino
   */
  public Tetromino rotatedTetromino(Tetromino tetromino) {
    boolean[][] rotatedTet = new boolean[matrise.length][matrise.length];

    for (int r = 0; r < matrise.length; r++) {
      for (int c = 0; c < matrise[0].length; c++) {
        boolean oldCell = matrise[r][c];

        int newRowIndex = c;
        int newColIndex = (matrise.length - 1) - r;

        rotatedTet[newRowIndex][newColIndex] = oldCell;
      }
    }

    return new Tetromino(symbol, rotatedTet, pos);
  }

  /**
   * Gets a matrix so that they are comparable in test
   * 
   * @return the matrix of the tetromino
   */
  public boolean[][] getMatrise() {
    return matrise;
  }

}
