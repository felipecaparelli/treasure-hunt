package org.cct.cp2019a.treasurehunt;

import org.cct.cp2019a.treasurehunt.controller.GameController;

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
        
        System.setProperty("console.encoding", "UTF-8");

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\s+");

        GameController gameController = new GameController();

        while (scanner.hasNextLine()) {
            try {
                gameController.execute(scanner);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        scanner.close();
    }

}
