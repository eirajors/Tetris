package no.uib.inf101.grid;

import java.util.Iterator;

public class Grid implements IGrid {

  // Character [][] grid;
  Character[][] grid;
  int rows;
  int cols;

  // konstruktør
  public Grid(int rows, int cols) {
    if (rows < 0 || cols < 0) {
      throw new IllegalArgumentException();
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new Character[rows][cols];
  }

  public Grid(int rows, int cols, Character defaultValue) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Character[rows][cols];

    // sette inn default value i alle plasser i grid

    for (int r = 0; r < (grid.length); r++) {
      for (int c = 0; c < (grid[0].length); c++) {
        grid[r][c] = defaultValue;
      }
    }
  }

  @Override
  public int rows() {
    return rows;
  }

  @Override
  public int cols() {
    return cols;
  }

  // public Iterable<CellPosition> position(){
  // return new Positions(this);
  // }

  @Override
  public Iterator<GridCell> iterator() {
    return new RowWiseGridIterator(this);
  }

  @Override
  public void set(CellPosition pos, Character symbol) {
    if (pos == null) {
      throw new IllegalArgumentException();
    }
    if (pos.row() >= grid.length || pos.row() < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (pos.col() >= grid[0].length || pos.col() < 0) {
      throw new IndexOutOfBoundsException();
    }

    grid[pos.row()][pos.col()] = symbol;
  }

  @Override
  public Character get(CellPosition pos) {
    if (pos == null) {
      throw new IllegalArgumentException();
    }
    return grid[pos.row()][pos.col()];

  }

  @Override
  public boolean positionIsOnGrid(CellPosition pos) {
    if (pos == null) {
      return false;
    }
    if (pos.row() >= grid.length || pos.row() < 0) {
      return false;
    }
    if (pos.col() >= grid[0].length || pos.col() < 0) {
      return false;
    }

    return true;

  }

}
