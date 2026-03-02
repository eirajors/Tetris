package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel{

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
        this.factory = new RandomTetrominoFactory();

        this.fallingTet = (factory.getNext()).shiftedToTopCenterOf(board);
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

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {

        Tetromino candidateTetromino = fallingTet.shiftedBy(deltaRow, deltaCol);

        for(GridCell cell : candidateTetromino){
            CellPosition pos = cell.pos();

            if(!isLegalPosition(board, pos)){
                return false; 
            }
        }
        fallingTet = candidateTetromino;
        return true;
    }

    public boolean isLegalPosition(TetrisBoard board, CellPosition position){
            if(!board.positionIsOnGrid(position)){
                return false; 
            }
            if(!(board.isFree(position))){
                return false;
            }
        return true; 
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino candTetromino = fallingTet.rotatedTetromino(fallingTet);

        for(GridCell cell : candTetromino){
            CellPosition pos = cell.pos();

            if(!isLegalPosition(board, pos)){
                return false; 
            }
        }
        fallingTet = candTetromino;

        return true;
    }

    /**
     * selve metoden som flytter en brikke
     * flytter ned så lenge den gir true, deretter lager en ny Tetromino, 
     * og limer den andre fast
     */
    @Override
    public void dropTetromino() {
        while(moveTetromino(1, 0)){}
        glueAndNewFalling();
        
    }

    //en hjelpemetode for å hente en ny fallende tetromino
    public Tetromino getNewTetromino(){
        Tetromino newTetromino = factory.getNext();

        return newTetromino.shiftedToTopCenterOf(board);
    }

    //limer først tetromino til brettet, og deretter endrer fallingTet til en ny tetromino
    public void glueAndNewFalling(){
        board.glueToBoard(fallingTet);
        fallingTet = getNewTetromino();
    }

    

}
