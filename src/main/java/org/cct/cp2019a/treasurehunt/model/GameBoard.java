package org.cct.cp2019a.treasurehunt.model;

import java.util.List;
import java.util.Map;

/**
 * This Class represents the game board where the players should dig to find their treasures.
 */
public class GameBoard {
    private Map<String, List<BoardSquare>> grid;

    public GameBoard(Map<String, List<BoardSquare>> grid) {
        this.grid = grid;
    }

    /**
     * This game board grid (columns and rows).
     * @return
     */
    public Map<String, List<BoardSquare>> getGrid() {
        return grid;
    }
}
