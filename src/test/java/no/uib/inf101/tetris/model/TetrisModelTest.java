package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class TetrisModelTest {

    private TetrisModel model = new TetrisModel();
    private Grid grid = new Grid(15, 10, '-');
    //teste
    //vellykket flytting returnerer true
    @Test
    void testMoveTetrominoReturnValue(){
        assertTrue(model.moveTetromino(1, 0));
        assertTrue(model.moveTetromino(5, 0));
        assertTrue(model.moveTetromino(0, 3));
        assertTrue(model.moveTetromino(0, -3));
        assertFalse(model.moveTetromino(15, 0));
        assertFalse(model.moveTetromino(0, 5));
        assertFalse(model.moveTetromino(0, -5));
    }
    // vellykket flytting endrer hva man får om man itererer gjennom den fallende brikken
    @Test
    void testIteratorOfMovedTetromino(){
        ArrayList<GridCell> before = new ArrayList<>();

        for(GridCell cell : model.getFallingTetrominoCells()){
            before.add(cell);
        }
        model.moveTetromino(1, 0);

        ArrayList<GridCell> after = new ArrayList<>();
        for(GridCell cell : model.getFallingTetrominoCells()){
            after.add(cell);
        }

        assertNotEquals(before, after);
    }
    //det er ikke mulig å flytte en brikke ut av brettet
        //returnerer false og ingen endring i den fallende brikken sin posisjon

    @Test
    void testNotMoveOutsideBoard(){
        ArrayList<CellPosition> positions = new ArrayList<>();

        for(GridCell cell : model.getFallingTetrominoCells()){
            CellPosition currCellPosition = cell.pos();

            positions.add(currCellPosition);
        }
        assertFalse(model.moveTetromino(0, 5));
        
        ArrayList<CellPosition> positionsAfter = new ArrayList<>();

        for(GridCell cell : model.getFallingTetrominoCells()){
            CellPosition currCellPosition = cell.pos();

            positionsAfter.add(currCellPosition);
        }

        assertEquals(positions, positionsAfter);

    }   
    
    @Test
    void testIfCellIsOccupied(){
        TetrisModel modelWithGrid = new TetrisModel(grid);
        ArrayList<GridCell> cells = new ArrayList<>();
        for(GridCell cell : modelWithGrid.getFallingTetrominoCells()){
            cells.add(cell);
        }
        GridCell firstCell = cells.get(0);
        CellPosition firstPos = firstCell.pos();

        CellPosition blockCell = new CellPosition(firstPos.row(), firstPos.col()+1); 
        grid.set(new CellPosition(blockCell), 'r');

        assertFalse(modelWithGrid.moveTetromino(0,1));
    }
}
