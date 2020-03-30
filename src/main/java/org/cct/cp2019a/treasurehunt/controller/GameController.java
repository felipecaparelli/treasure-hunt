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

/**
 * The game controller responsible for managing its status/stages.
 */
public class GameController {

    private static GameValidator gameValidator = new GameValidator();

    /**
     * Executes the logic for the game state control.
     * @param game the game data
     * @param input the user input
     * @throws GameRuleException
     */
    public static void execute(Game game, String input) throws GameRuleException {
        switch (game.getGameStatus()) {
            case NOT_INITIALIZED:
                goToPlayersSelection(game);
                break;
            case PLAYER_SELECTION:
                goToPlayersProfile(game, input);
                break;
            case ADDING_PLAYER_NAME:
                goToFillUserAge(game, input);
                break;
            case ADDING_PLAYER_AGE:
                goToCompleteProfile(game, input);
                break;
            case STARTED:
                goToPlayerTurn(game);
                break;
            case PLAYER_ROUND:
                goToDig(game, input);
                break;
            case GAME_OVER:
                goToGameOver(game);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + game.getGameStatus());
        }
    }

    /**
     * Moves the game to the step 1: Requires the number of players
     * @param game the game data
     */
    public static void goToPlayersSelection(Game game) {
        System.out.println("ADD THE NUMBER OF PLAYERS:");
        game.setGameStatus(GameStatus.PLAYER_SELECTION);
    }

    /**
     * Moves the game to the step 2: Start the process of adding user's profiles
     * @param game the game data
     * @param input the user input
     * @throws GameRuleException
     */
    public static void goToPlayersProfile(Game game, String input) throws GameRuleException {
        int numberOfPlayers = Integer.parseInt(input);
        if (gameValidator.isValidPlayerNum(numberOfPlayers)) {
            game.init(GameUtils.buildGameBoard(), numberOfPlayers);
            goToFillUserName(game);
        }
    }

    /**
     * Moves the game to the step 3: Filling the user's name
     * @param game the game data
     * @throws GameRuleException
     */
    public static void goToFillUserName(Game game) {
        System.out.println("SET THE NAME OF PLAYER'S FULL NAME:");
        game.setGameStatus(GameStatus.ADDING_PLAYER_NAME);
    }

    /**
     * Moves the game to the step 4: Filling the user's age
     * @param game the game data
     * @param input the user input
     * @throws GameRuleException
     */
    public static void goToFillUserAge(Game game, String input) throws GameRuleException {
        if (gameValidator.isValidPlayerName(input)) {
            game.getActiveProfile().setFullName(input);
            System.out.println("SET THE PLAYER'S AGE:");
            game.setGameStatus(GameStatus.ADDING_PLAYER_AGE);
        } else {
            throw new GameRuleException(GameRuleMessages.PLAYER_NAME_RULE);
        }
    }

    /**
     * Moves the game to the step 5: Complete the user profile
     * @param game the game data
     * @param input the user input
     * @throws GameRuleException
     */
    public static void goToCompleteProfile(Game game, String input) throws GameRuleException {
        try {
            int age = Integer.parseInt(input);
            if (gameValidator.isValidPlayerAge(age)) {
                game.getActiveProfile().setAge(age);
            } else {
                throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
            }

            game.moveSlot();

            if (game.hasAnySlotToFill()) {
                goToFillUserName(game);
            } else {
                goToGameStart(game);
            }
        } catch (Exception e) {
            throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
        }
    }

    /**
     * Moves the game to the initial position (start).
     * @param game the game data
     */
    public static void goToGameStart(Game game) {
        ViewUtils.printGameRules();
        game.start();
        ViewUtils.printGameBoard(game.getGameBoard());
        goToPlayerTurn(game);
    }

    /**
     * Moves the game to the player turn
     * @param game the game data
     */
    public static void goToPlayerTurn(Game game) {
        game.setGameStatus(GameStatus.PLAYER_ROUND);
        Player player = game.getActivePlayer();
        String digMessage = GameMessages.DIG_MESSAGE + " (Pirate points: %d)";
        String msg = String.format(digMessage, player.getFullName(), player.getPiratePoints());
        ViewUtils.printMessage(msg);
    }

    /**
     * Executes the dig operation (validates)
     * @param game the game data
     * @param input the user input
     */
    private static void goToDig(Game game, String input) {
        if (gameValidator.isValidDigPosition(game, input)) {

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
                    ViewUtils.printMessage(celebrate);
                } else {
                    ViewUtils.printMessage(GameMessages.SUNK_MESSAGE);
                }

                Thread.sleep(3000);

            } catch (DigException de) {
                ViewUtils.printMessage(de.getMessage());
                game.changeTurn();
                game.setGameStatus(GameStatus.PLAYER_ROUND);
                goToPlayerTurn(game);
            } catch (Exception e) {
                ViewUtils.printMessage(e.getMessage());
            }

            if (gameValidator.isGameOver(game)) {
                goToGameOver(game);
            } else {
                ViewUtils.printGameBoard(game.getGameBoard());
                game.changeTurn();
                game.setGameStatus(GameStatus.PLAYER_ROUND);
                goToPlayerTurn(game);
            }
        }
    }

    /**
     * Moves the game to the end.
     * @param game the game data
     */
    public static void goToGameOver(Game game) {
        game.setGameStatus(GameStatus.GAME_OVER);
        Player winner = game.getWinner();
        String winnerMessage = String.format(GameMessages.WINNER_MESSAGE, winner.getFullName());
        ViewUtils.printMessage(winnerMessage);
        ViewUtils.printMessage(GameMessages.GAME_OVER_MESSAGE);
        System.exit(0);
    }
}
