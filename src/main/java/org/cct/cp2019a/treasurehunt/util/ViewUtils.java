package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.GameBoard;
import org.cct.cp2019a.treasurehunt.model.Treasure;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Class the should be used to create the view elements
 */
public class ViewUtils {

    /**
     * Build the view grid headers
     * @param gameBoardGrid
     * @return
     */
    public static Set<String> buildViewGridHeaders(Map<String, List<BoardSquare>> gameBoardGrid) {
        return gameBoardGrid.keySet().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Build the view rows
     * @param gameBoardGrid
     * @return
     */
    public static List<List<Integer>> buildViewGridRows(Map<String, List<BoardSquare>> gameBoardGrid) {
        List<List<Integer>> rows = new ArrayList<>(gameBoardGrid.size());
        for (int cols = 0; cols < gameBoardGrid.size(); cols++) {
            rows.add(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        }
        return rows;
    }

    /**
     * Prints the game board in the console output.
     * TODO VALIDATE THE OUTPUT
     * @param gameBoard
     */
    public static void printGameBoard(GameBoard gameBoard) {
        Map<String, List<BoardSquare>> grid = gameBoard.getGrid();
        System.out.print(" __");
        grid.keySet().iterator().forEachRemaining(column -> System.out.print("   " + column + "  "));
        System.out.println("");
        int actualRow = 1;
        grid.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(BoardSquare::getRow))
                .forEach(boardSquare -> {
                    String row = String.format("%02d", boardSquare.getRow());
                    if ("A".equalsIgnoreCase(boardSquare.getColumn())) {
                        System.out.print(" " + row );
                    }
                    System.out.print(" " + boardSquare);
                    if ("J".equalsIgnoreCase(boardSquare.getColumn())) {
                        System.out.println();
                    }
                });
    }

    public static void main(String[] args) {
        GameBoard gameBoard = GameUtils.buildGameBoard();
        gameBoard.getGrid().get("A").get(0).setDug(true);
        gameBoard.getGrid().get("A").get(6).setDug(true);
        gameBoard.getGrid().get("B").remove(2);
        gameBoard.getGrid().get("B").add(2, new BoardSquare("B", 3, new Treasure(20)));
        gameBoard.getGrid().get("B").get(2).setDug(true);
        printGameBoard(gameBoard);
    }
}
