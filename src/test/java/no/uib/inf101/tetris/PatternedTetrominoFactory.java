package no.uib.inf101.tetris;

import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;

public class PatternedTetrominoFactory implements TetrominoFactory{

    String pattern;
    int currPosition;

    //konstruktør
    public PatternedTetrominoFactory(String pattern){
        if(pattern == null || pattern.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.currPosition = 0; 
    }

    @Override
    public Tetromino getNext() {
        char symbol = pattern.charAt(currPosition);

        currPosition ++;
        if(currPosition >= pattern.length()){
            currPosition = 0;
        }

        return Tetromino.newTetromino(symbol);
    }
}
