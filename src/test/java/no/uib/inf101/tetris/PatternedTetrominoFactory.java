package no.uib.inf101.tetris;

import java.util.ArrayList;
import java.util.Arrays;

import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;

public class PatternedTetrominoFactory implements TetrominoFactory{

    ArrayList<Character> symbols;
    TetrominoFactory factory;

    //konstruktør
    public PatternedTetrominoFactory(){
        this.symbols = new ArrayList<>(Arrays.asList('J', 'S', 'T', 'I'));
        this.factory = new RandomTetrominoFactory();
    }

    @Override
    public Tetromino getNext() {
        return factory.getNext();
    }
}
