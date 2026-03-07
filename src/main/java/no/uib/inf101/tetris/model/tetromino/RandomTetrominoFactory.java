package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory {

  private final Random random = new Random();

  public Tetromino getNext() {

    List<Character> symboler = new ArrayList<>(List.of('L', 'J', 'S', 'Z', 'T', 'I', 'O'));

    int index = random.nextInt(symboler.size());

    char c = symboler.get(index);

    return Tetromino.newTetromino(c);
  }

}
