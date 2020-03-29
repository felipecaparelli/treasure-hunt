package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.GameBoard;

import java.util.*;

/**
 * Generates data for the game.
 */
public class GameUtils {

    /**
     * Returns the labels for the game board columns;
     * @return
     */
    public static List<String> gameBoardColumnLabels() {
        String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        return Arrays.asList(columns);
    }

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
        for(String column : gameBoardColumnLabels()) {
            grid.put(column, buildGridRow(column));
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
        List<BoardSquare> treasuresMap = TreasureMapUtils.readPiratePetesSecretJournal();
        for (int i=0; i < 10; i++) {
            BoardSquare boardSquare = new BoardSquare(columnName, i+1, null);
            if (treasuresMap != null && treasuresMap.contains(boardSquare)) {
                rows.add(treasuresMap.get(treasuresMap.indexOf(boardSquare)));
            } else {
                rows.add(new BoardSquare(columnName, i+1, null));
            }

        }
        return rows;
    }

    public static void main(String[] args) {
        buildGameBoard().getGrid().forEach((s, boardSquares) -> {
            System.out.println("column: " + s + " rows: " + boardSquares.size());
        });
    }
}
