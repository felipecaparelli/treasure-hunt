package org.cct.cp2019a.treasurehunt.controller;

import org.cct.cp2019a.treasurehunt.constant.GameMessages;
import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.DigException;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.model.Game;
import org.cct.cp2019a.treasurehunt.model.Player;
import org.cct.cp2019a.treasurehunt.util.GameUtils;
import org.cct.cp2019a.treasurehunt.util.ViewUtils;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;

import java.util.Scanner;

/**
 * The game controller responsible for managing its status/stages.
 */
public class GameController {

    private GameValidator validator;
    private Game game;

    /**
     * Load the game.
     */
    public GameController() {
        this.validator = new GameValidator();
        this.game = new Game();
        ViewUtils.printGameTitle();
        this.goToPlayersSelection();
    }

    /**
     * Executes the logic for the game state control.
     * @param scanner to get the user input
     * @throws GameRuleException
     */
    public void execute(Scanner scanner) throws GameRuleException {
        switch (game.getGameStatus()) {
            case NOT_INITIALIZED:
                goToPlayersSelection();
                break;
            case PLAYER_SELECTION:
                goToPlayersProfile(scanner);
                break;
            case ADDING_PLAYER_NAME:
                goToFillUserAge(scanner);
                break;
            case ADDING_PLAYER_AGE:
                goToCompleteProfile(scanner);
                break;
            case STARTED:
                goToPlayerTurn();
                break;
            case PLAYER_ROUND:
                goToDig(scanner);
                break;
            case GAME_OVER:
                goToGameOver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + game.getGameStatus());
        }
    }

    /**
     * Moves the game to the step 1: Requires the number of players
     */
    public void goToPlayersSelection() {
        System.out.println("ADD THE NUMBER OF PLAYERS:");
        game.setGameStatus(GameStatus.PLAYER_SELECTION);
    }

    /**
     * Moves the game to the step 2: Start the process of adding user's profiles
     * @param scanner to get the user input
     * @throws GameRuleException
     */
    public void goToPlayersProfile(Scanner scanner) throws GameRuleException {

        String input = scanner.nextLine();
        try {
            int numberOfPlayers = Integer.parseInt(input);
            if (validator.isValidPlayerNum(numberOfPlayers)) {
                game.init(GameUtils.buildGameBoard(), numberOfPlayers);
                goToFillUserName();
            }
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            scanner.next();
        }

    }

    /**
     * Moves the game to the step 3: Filling the user's name.
     * @throws GameRuleException
     */
    public void goToFillUserName() {
        game.setGameStatus(GameStatus.ADDING_PLAYER_NAME);
        System.out.println("SET THE NAME OF PLAYER'S FULL NAME:");
    }

    /**
     * Moves the game to the step 4: Filling the user's age
     * @param scanner to get the user input
     * @throws GameRuleException
     */
    public void goToFillUserAge(Scanner scanner) throws GameRuleException {

        String username = scanner.nextLine();

        if (username.isEmpty()) return;

        if (validator.isValidPlayerName(username)) {
            game.getActiveProfile().setFullName(username.toUpperCase());
            game.setGameStatus(GameStatus.ADDING_PLAYER_AGE);
            System.out.println(String.format("HOW OLD ARE YOU %s?", game.getActiveProfile().getFullName()));
        } else {
            throw new GameRuleException(GameRuleMessages.PLAYER_NAME_RULE);
        }
    }

    /**
     * Moves the game to the step 5: Complete the user profile
     * @param scanner to get the user input
     * @throws GameRuleException
     */
    public void goToCompleteProfile(Scanner scanner) throws GameRuleException {
        try {
            int age = Integer.parseInt(scanner.nextLine());
            if (validator.isValidPlayerAge(age)) {
                game.getActiveProfile().setAge(age);
            } else {
                throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
            }

            game.moveSlot();

            if (game.hasAnySlotToFill()) {
                goToFillUserName();
            } else {
                goToGameStart();
            }
        } catch (Exception e) {
            throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
        }
    }

    /**
     * Moves the game to the initial position (start).
     */
    public void goToGameStart() {
        ViewUtils.printGameRules();
        game.start();
        ViewUtils.printGameBoard(game.getGameBoard());
        goToPlayerTurn();
    }

    /**
     * Moves the game to the player turn.
     */
    public void goToPlayerTurn() {
        game.setGameStatus(GameStatus.PLAYER_ROUND);
        Player player = game.getActivePlayer();
        String digMessage = GameMessages.DIG_MESSAGE + " (Pirate points: %d)";
        String msg = String.format(digMessage, player.getFullName(), player.getPiratePoints());
        ViewUtils.printMessage(msg);
    }

    /**
     * Executes the dig operation (validates)
     * @param scanner to get the user input
     */
    private void goToDig(Scanner scanner) {
        String input = scanner.nextLine();

        if (validator.isValidDigPosition(game, input)) {

            String column = input.substring(0, 1).toUpperCase();
            int row = Integer.parseInt(input.substring(1));

            try {

                Player activePlayer = game.getActivePlayer();
                activePlayer.dig();
                int reward = game.getGameBoard().dig(column, row);
                if (reward > 0) {
                    // was the treasure found?
                    activePlayer.addReward(reward);
                    String msg = String.format("Pirate %s got %d Pirate Points!", activePlayer.getFullName(), reward).toUpperCase();
                    String celebrate = GameMessages.CELEBRATE_MESSAGE + "\n  " + msg;
                    ViewUtils.printSuccessMessage(celebrate);
                } else {
                    ViewUtils.printBadMessage(GameMessages.SUNK_MESSAGE);
                }

                Thread.sleep(3000);

            } catch (DigException de) {
                ViewUtils.printMessage(de.getMessage());
                game.changeTurn();
                game.setGameStatus(GameStatus.PLAYER_ROUND);
                goToPlayerTurn();
            } catch (Exception e) {
                ViewUtils.printMessage(e.getMessage());
            }

            if (validator.isGameOver(game)) {
                goToGameOver();
            } else {
                ViewUtils.printGameBoard(game.getGameBoard());
                game.changeTurn();
                game.setGameStatus(GameStatus.PLAYER_ROUND);
                goToPlayerTurn();
            }
        }
    }

    /**
     * Moves the game to the end.
     */
    public void goToGameOver() {
        game.setGameStatus(GameStatus.GAME_OVER);
        Player winner = game.getWinner();
        ViewUtils.printMessage(String.format(GameMessages.WINNER_MESSAGE, winner.getFullName()));

        ViewUtils.printGameOver();
        System.exit(0);
    }
}
