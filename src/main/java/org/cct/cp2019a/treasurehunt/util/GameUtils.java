package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates data for the game.
 */
public class GameUtils {

    /**
     * Build the game board.
     * @return
     */
    public static GameBoard buildGameBoard() {
        return new GameBoard(buildGameBoardGrid());
    }

    /**
     * Build the game grid
     * @return
     */
    private static Map<String, List<BoardSquare>> buildGameBoardGrid() {
        Map<String, List<BoardSquare>> grid = new HashMap<>();
        String[] columnNames = {"A","B","C","D","E","F","G","H","I","J"};
        for(String columnName : columnNames) {
            grid.put(columnName, buildGridRow(columnName));
        }
        
        return grid;
    }

    /**
     * Build each row with the containing board squares elements.
     * @param columnName
     * @return
     */
    private static List<BoardSquare> buildGridRow(String columnName) {
        List<BoardSquare> rows = new ArrayList<>(10);
        for (int i=0; i < 10; i++) {
            rows.add(new BoardSquare(columnName, i+1, null));
        }
        return rows;
    }
}
