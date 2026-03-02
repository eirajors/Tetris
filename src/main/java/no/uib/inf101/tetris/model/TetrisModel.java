package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel{

    private TetrisBoard board;
    private TetrominoFactory factory; 
    private Tetromino fallingTet;


    public TetrisModel(){
        this.board = new TetrisBoard(15, 10);
        this.factory = new RandomTetrominoFactory();

        this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(board);
        
    }

    public TetrisModel(IGrid grid){
        this.board = new TetrisBoard(grid);
    }

    @Override
    public GridDimension getDimension() {
        return board;
    }

    @Override
    public Iterable<GridCell> getTilesOnBoard() {
        return board;
    }

    @Override
    public Iterable<GridCell> getFallingTetrominoCells() {
        return fallingTet;
    }


}
