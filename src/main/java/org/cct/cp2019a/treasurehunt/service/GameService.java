package org.cct.cp2019a.treasurehunt.service;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.exception.InvalidDigPositionException;
import org.cct.cp2019a.treasurehunt.model.Game;
import org.cct.cp2019a.treasurehunt.model.Player;
import org.cct.cp2019a.treasurehunt.util.GameUtils;
import org.cct.cp2019a.treasurehunt.util.ViewUtils;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class GameService {

    private static GameValidator gameValidator = new GameValidator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        System.out.println("----- \u2620 TREASURE HUNT \u2620 -----");
        goToPlayersSelection(game);

        while (scanner.hasNextLine()) {

            List<String> tokens = new ArrayList<>();
            Scanner lineScanner = new Scanner(scanner.nextLine());

            while (lineScanner.hasNext()) {
                try {
                    execute(game, lineScanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            lineScanner.close();
            System.out.println(tokens);
        }

        scanner.close();
    }

    private static void execute(Game game, String input) throws GameRuleException {
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
            default:
                throw new IllegalStateException("Unexpected value: " + game.getGameStatus());
        }
    }

    // step 1
    public static void goToPlayersSelection(Game game) {
        System.out.println("ADD THE NUMBER OF PLAYERS");
        game.setGameStatus(GameStatus.PLAYER_SELECTION);
    }

    // step 2
    public static void goToPlayersProfile(Game game, String input) throws GameRuleException {
        int numberOfPlayers = Integer.parseInt(input);
        if (gameValidator.isValidPlayerNum(numberOfPlayers)) {
            game.init(GameUtils.buildGameBoard(), numberOfPlayers);
            goToFillUserName(game);
        }
    }

    public static void goToFillUserName(Game game) {
        System.out.println("SET THE NAME OF PLAYER'S FULL NAME ");
        game.setGameStatus(GameStatus.ADDING_PLAYER_NAME);
    }

    public static void goToFillUserAge(Game game, String input) throws GameRuleException {
        if (gameValidator.isValidPlayerName(input)) {
            game.getActiveProfile().setFullName(input);
            System.out.println("SET THE AGE OF PLAYER'S AGE ");
            game.setGameStatus(GameStatus.ADDING_PLAYER_AGE);
        } else {
            throw new GameRuleException(GameRuleMessages.PLAYER_NAME_RULE);
        }
    }

    public static void goToCompleteProfile(Game game, String input) throws GameRuleException {
        try {
            int age = Integer.parseInt(input);
            if (gameValidator.isValidPlayerAge(age)) {
                game.getActiveProfile().setAge(age);
            } else {
                throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
            }

            // TODO VALIDATE MOVING SLOTS...
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

    public static void goToGameStart(Game game) {
        game.start();
        ViewUtils.printGameBoard(game.getGameBoard());
    }

    public void addNewPlayer(Player player) {

    }

    public void dig(String... params) throws InvalidDigPositionException {
        if (gameValidator.isDigValid(params)) {

        }
    }
}
