package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;


import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.tetris.model.tetromino.Test;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public class tt {

    //test hashcode, equals, iterator metoen, flytting fungerer som det skal 
    @Test
    void testHashCode(){
        boolean [][] testMatrise = new boolean[][]{{ false, false, false },
            { true,  true,  true },
            { false, true,  false }};
        Tetromino obj = new Tetromino('T', testMatrise, new CellPosition(1,2));
        int initialHash = obj.hashCode();
        assertEquals(initialHash, obj.hashCode());
    }

    @Test
    void gridTestGetRowsAndCols() {
        IGrid grid = new Grid(3, 2);
        assertEquals(3, grid.rows());
        assertEquals(2, grid.cols());
    }
  

}
