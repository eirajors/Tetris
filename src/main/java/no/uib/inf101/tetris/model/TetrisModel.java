package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;

public class TetrisModel{

    private TetrisBoard board;
    private TetrominoFactory factory; 
    private Tetromino fallingTet;

    fallingTet.getNext();

    shiftedToTopCenterOf(GridDimension grid);

    public TetrisModel(){
        this.board = new TetrisBoard(15, 10);
    }
    public TetrisModel(IGrid grid){
        this.board = new TetrisBoard(grid);
    }

    public GridDimension getDimension() {
        return board;
    }

    public Iterable<GridCell> getTilesOnBoard() {
        return board;
    }

}
