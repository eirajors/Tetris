package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class TetrisModel{

    private TetrisBoard board;
    
    public TetrisModel(){
        this.board = new TetrisBoard(15, 10);
    }

    public GridDimension getDimension() {
        return board;
    }

    public Iterable<GridCell> getTilesOnBoard() {
        return board;
    }

}
