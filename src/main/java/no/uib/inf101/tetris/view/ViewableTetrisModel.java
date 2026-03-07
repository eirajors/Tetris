package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {
    GridDimension getDimension();

    Iterable<GridCell> getTilesOnBoard();

    Iterable<GridCell> getFallingTetrominoCells();

    GameState getGameState();
}
