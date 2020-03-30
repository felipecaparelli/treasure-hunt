package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.exception.AlreadyDugException;

import java.util.Collection;
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

    /**
     * The action to dig the position on the game board.
     * @param column a letter from A to J
     * @param row a number from 1 to 10
     * @return
     */
    public int dig(String column, int row) {

        if(isDug(column, row)) {
            throw new AlreadyDugException();
        }

        grid.get(column).get(row-1).setDug(true);

        return hasTreasure(column, row) ? grid.get(column).get(row-1).getTreasure().getValue() : 0;
    }

    /**
     * Return true if the treasure was found in the position where the player has dug.
     * @param column
     * @param row
     * @return
     */
    public boolean hasTreasure(String column, int row) {
        return grid.get(column).get(row-1).hasTreasure();
    }

    /**
     * Returns the value stores on the game board position
     * @param column
     * @param row
     * @return
     */
    public int getTreasureValue(String column, int row) {
        return grid.get(column).get(row-1).getTreasure().getValue();
    }

    /**
     * Returns if the board square is already dug
     * @param column
     * @param row
     * @return
     */
    public boolean isDug(String column, int row) {
        return grid.get(column).get(row-1).isDug();
    }

    /**
     * Validates if there is still any treasure to be found hidden on the game board.
     * @return true if there is at least one hidden treasure
     */
    public boolean isThereAnyTreasureMissing() {
        return this.grid.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(boardSquare -> !boardSquare.isDug() && boardSquare.hasTreasure());
    }
}
