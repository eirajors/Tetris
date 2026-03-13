package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {
  /** @return the board dimensions */
  GridDimension getDimension();

  /** @return all fixed tiles currently on the board */
  Iterable<GridCell> getTilesOnBoard();

  /** @return the cells of the currently falling tetromino */
  Iterable<GridCell> getFallingTetrominoCells();

  /** @return the current game state */
  GameState getGameState();

  /** @return the current score in the active game */
  int getScore();

  /** @return the current level that is played at */
  int getLevel();
}
