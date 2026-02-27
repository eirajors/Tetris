package no.uib.inf101.tetris.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.tetris.model.TetrisBoard;

public class TetrisBoardTest {
	@Test
	void testConstructStartsWithEmpty() {
		int rows = 3;
		int cols = 5;
		Grid grid = new Grid(rows,cols,'-');
		TetrisBoard board = new TetrisBoard(grid);
		int countFree=0;
		int countTaken=0;
		
		assertEquals(rows*cols, countFree);
		assertEquals(0, countTaken);
	}
}
