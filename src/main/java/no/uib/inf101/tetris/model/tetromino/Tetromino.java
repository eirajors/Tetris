package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;

public class Tetromino {

    private Tetromino(char symbol, boolean [][] matrise, CellPosition pos){

    }
    public static Tetromino newTetromino(){
        char symbol = 'T';
        boolean[][] newMatrise = new boolean[][]{
            { false, false, false },
            {  true,  true,  true },
            { false,  true, false }
        };
        return new Tetromino(symbol, newMatrise, new CellPosition(0,0));
    }
}
