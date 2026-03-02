package no.uib.inf101.tetris.model;

import java.util.Iterator;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

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

    public boolean isFree(CellPosition pos){
        //hvis fargesymbolet i cellen er '-'(tom), returner true, hvis ikke false
        if(grid.get(pos) == '-'){
            return true;
        }
        return false;
    }

    //en hjelpemetode for å lime fast den fallende tetrominoen til brettet(kan være i tetrisboard)
    public final void glueToBoard(Tetromino tetromino){

        for(GridCell cell : tetromino){

            CellPosition pos = cell.pos();
            grid.set(pos, cell.value());
        }
    }

}
