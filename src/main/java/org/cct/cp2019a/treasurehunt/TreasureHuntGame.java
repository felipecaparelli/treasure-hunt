package org.cct.cp2019a.treasurehunt;

import org.cct.cp2019a.treasurehunt.controller.GameController;
import org.cct.cp2019a.treasurehunt.model.Game;
import org.cct.cp2019a.treasurehunt.util.ViewUtils;

import java.util.Scanner;

/**
 * This is the main class for the game Treasure Hunt.
 */
public class TreasureHuntGame {

    /**
     * Main method / entry point
     * @param args initial parameters
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        ViewUtils.printGameTitle();

        GameController.goToPlayersSelection(game);

        while (scanner.hasNextLine()) {
            Scanner lineScanner = new Scanner(scanner.nextLine());
            while (lineScanner.hasNext()) {
                try {
                    GameController.execute(game, lineScanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            lineScanner.close();
        }

        scanner.close();
    }


}
