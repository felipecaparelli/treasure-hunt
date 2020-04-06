package org.cct.cp2019a.treasurehunt.util;

import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.GameBoard;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Class the should be used to create the view elements
 */
public class ViewUtils {

    /**
     * Prints the Game Rules.
     */
    public static void printGameRules() {
        System.out.println("\u2620 ==================================== GAME RULES ===================================== \u2620");
        try {
            GameRulesUtils.loadGameRulesFileContent().forEach(System.out::println);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("\u2620 ===================================================================================== \u2620");
    }

    /**
     * Prints the game title.
     */
    public static void printGameTitle() {
        String welcomeText = "" +
                "\u2620 ============================================================================================== \u2620\n" +
                "████████╗██████╗░███████╗░█████╗░░██████╗██╗░░░██╗██████╗░███████╗  ██╗░░██╗██╗░░░██╗███╗░░██╗████████╗\n" +
                "╚══██╔══╝██╔══██╗██╔════╝██╔══██╗██╔════╝██║░░░██║██╔══██╗██╔════╝  ██║░░██║██║░░░██║████╗░██║╚══██╔══╝\n" +
                "░░░██║░░░██████╔╝█████╗░░███████║╚█████╗░██║░░░██║██████╔╝█████╗░░  ███████║██║░░░██║██╔██╗██║░░░██║░░░\n" +
                "░░░██║░░░██╔══██╗██╔══╝░░██╔══██║░╚═══██╗██║░░░██║██╔══██╗██╔══╝░░  ██╔══██║██║░░░██║██║╚████║░░░██║░░░\n" +
                "░░░██║░░░██║░░██║███████╗██║░░██║██████╔╝╚██████╔╝██║░░██║███████╗  ██║░░██║╚██████╔╝██║░╚███║░░░██║░░░\n" +
                "░░░╚═╝░░░╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝╚═════╝░░╚═════╝░╚═╝░░╚═╝╚══════╝  ╚═╝░░╚═╝░╚═════╝░╚═╝░░╚══╝░░░╚═╝░░░\n" +
                "\u2620 ============================================================================================== \u2620";
        System.out.println(welcomeText);
    }

    /**
     * Print the message in the console.
     * @param message game message
     */
    public static void printMessage(String message) {
        System.out.println();
        System.out.println("\u2620 ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ \u2620\n");
        System.out.println("    " + message + "    \n");
        System.out.println("\u2620 ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ \u2620\n");
    }

    /**
     * Prints the game board in the console output.
     * @param gameBoard the game board object
     */
    public static void printGameBoard(GameBoard gameBoard) {
        System.out.println(" ________________________ TREASURE MAP ________________________");
        Map<String, List<BoardSquare>> grid = gameBoard.getGrid();
        System.out.print("   ");
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
        System.out.println(" ==============================================================");
    }

    /**
     * Print the game over message in the console.
     */
    public static void printGameOver() {
        System.out.println();
        System.out.println(
                "\u2620 ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ GAME OVER ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ \u2620\n" +
                "                       ▒█▀▀█ ░█▀▀█ ▒█▀▄▀█ ▒█▀▀▀ 　 ▒█▀▀▀█ ▒█░░▒█ ▒█▀▀▀ ▒█▀▀█ \n" +
                "                       ▒█░▄▄ ▒█▄▄█ ▒█▒█▒█ ▒█▀▀▀ 　 ▒█░░▒█ ░▒█▒█░ ▒█▀▀▀ ▒█▄▄▀ \n" +
                "                       ▒█▄▄█ ▒█░▒█ ▒█░░▒█ ▒█▄▄▄ 　 ▒█▄▄▄█ ░░▀▄▀░ ▒█▄▄▄ ▒█░▒█ \n" +
                "\u2620 ¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ \u2620\n");
    }

    /**
     * Print the success message in the console.
     * @param message game message
     */
    public static void printSuccessMessage(String message) {
        System.out.println();
        System.out.println("\u2620 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒ \u2620\n");
        System.out.println("    " + message + "    \n");
        System.out.println("\u2620 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒ \u2620\n");
    }

    /**
     * Print the bad message in the console.
     * @param message game message
     */
    public static void printBadMessage(String message) {
        System.out.println();
        System.out.println("\u2620 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ \u2620\n");
        System.out.println("    " + message + "    \n");
        System.out.println("\u2620 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ \u2620\n");
    }

}
