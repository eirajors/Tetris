package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;

public class RandomTetrominoFactory implements TetrominoFactory{
    
    public Tetromino getNext(){

        List<Character> symboler = new ArrayList<>(List.of('L', 'J', 'S', 'Z', 'T', 'I', 'O'));

        int min = 0;
        int max = 6;
        int randomNumber = min + (int)(Math.random() * ((max - min) + 1));//https://medium.com/@generativeai.saif/java-random-number-generator-5-methods-explained-with-examples-bf9e53abed3c

        char c = symboler.get(randomNumber);

        return Tetromino.newTetromino(c);
    }

}
