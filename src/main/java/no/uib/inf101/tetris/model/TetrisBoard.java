package no.uib.inf101.tetris.model;

import java.util.Iterator;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;

public class TetrisBoard implements Iterable<GridCell>, GridDimension{

    private IGrid grid; 

    //konstruktør
    public TetrisBoard(IGrid grid){
        this.grid = grid;
    }

    public TetrisBoard(int rows, int cols){
        this(new Grid(rows, cols, '-'));
    }

    @Override
    public int rows() {
        return grid.rows();
    }

    @Override
    public int cols() {
        return grid.cols();
    }

    @Override
    public Iterator<GridCell> iterator() {
        return grid.iterator();
    }

}
