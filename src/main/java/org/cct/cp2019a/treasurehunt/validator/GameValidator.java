package org.cct.cp2019a.treasurehunt.validator;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.exception.AlreadyDugException;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.exception.InvalidDigPositionException;
import org.cct.cp2019a.treasurehunt.model.BoardSquare;
import org.cct.cp2019a.treasurehunt.model.Game;
import org.cct.cp2019a.treasurehunt.model.GameBoard;
import org.cct.cp2019a.treasurehunt.model.Player;
import org.cct.cp2019a.treasurehunt.util.GameUtils;
import org.cct.cp2019a.treasurehunt.util.NumberUtils;

import java.util.List;

/**
 * This class is responsible for applying the game rules validation.
 */
public class GameValidator {

    public static final int MIN_NUM_OF_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYER = 4;

    /*
    At the start of the game, the user should be asked to enter the number of players in the game.
    Next, the user should be prompted to enter the full name and age of each player in the game (one player at a time)
    Next, the game should (briefly) outline the rules of the game for the players. These are explained below (See “Game Rules”).
    Each player should be awarded 100 “Pirate Points” to start the game.
    Then the game should randomly give each player a number of “Dig Points”


    Now subtract “Pirate Points” from each player based on their “Dig Points” x 5 – for example, if Player
    One gets 3 Dig Points, then subtract 3 x 5 = 15 from their “Pirate Points”, so Player One now has 85
    “Pirate Points” at the start of the game.

    The game should then create a “Treasure Map” and display it on the screen. This will be a 10 x 10
    grid of squares. The grid must be labelled so that the rows are numbered 1 to 10 and the columns
    are labelled A to J. This is so that the player can easily select a square.

     */

    /**
     * Checks if the number os players is valid (between 2 and 4).
     * VALIDATION RULE: There has to be a minimum of 2 players and a maximum of 4 Players
     *
     * @param numberOfPlayers
     * @return
     * @throws GameRuleException
     */
    public boolean isValidPlayerNum(int numberOfPlayers) throws GameRuleException {
        if(!NumberUtils.isBetween(numberOfPlayers, MIN_NUM_OF_PLAYER, MAX_NUM_OF_PLAYER)) {
            throw new GameRuleException(GameRuleMessages.NUM_OF_PLAYERS_RULE);
        }
        return true;
    }

    /**
     * Validates all the rules for player.
     * @param player player to be validated
     * @return return true if all rules are OK,
     * and throws a {@link org.cct.cp2019a.treasurehunt.exception.GameRuleException} if not.
     */
    public boolean isValidPlayer(Player player) throws GameRuleException {
        if(!isValidPlayerName(player.getFullName())) {
            throw new GameRuleException(GameRuleMessages.PLAYER_NAME_RULE);
        }
        if(!isValidPlayerAge(player.getAge())) {
            throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
        }
        return true;
    }

    /**
     * Validates the player's name.
     * VALIDATION RULE: The player has to enter their name in one line and must enter a first name and surname
     * @param playerName the player's name
     * @return return true if all rules are OK,
     * and throws a {@link org.cct.cp2019a.treasurehunt.exception.GameRuleException} if not.
     */
    public boolean isValidPlayerName(String playerName) throws GameRuleException {
        if (playerName == null || !playerName.trim().contains(" ")) {
            throw new GameRuleException(GameRuleMessages.PLAYER_NAME_RULE);
        }
        return true;
    }

    /**
     * Validates the player's age.
     * VALIDATION RULE: The player’s age must be 12 or over
     * @param playerAge player's age
     * @return return true if all rules are OK,
     * and throws a {@link org.cct.cp2019a.treasurehunt.exception.GameRuleException} if not.
     */
    public boolean isValidPlayerAge(int playerAge) throws GameRuleException {
        if (playerAge < 12) {
            throw new GameRuleException(GameRuleMessages.PLAYER_AGE_RULE);
        }
        return true;
    }

    /**
     * This method validates if the game is over or not.
     * @return
     */
    public boolean isGameOver(Game game) {
        boolean playersHasRemainingDigPoints = isAnyPlayerWithDigPoints(game.getPlayers());
        boolean isAnySquareAvailable = isAnySquareAvailable(game.getGameBoard());
        return !playersHasRemainingDigPoints && !isAnySquareAvailable;
    }

    private boolean isAnyPlayerWithDigPoints(List<Player> players) {
        return players.stream().anyMatch(player -> player.getShovel().getDigPoints() > 0);
    }

    private boolean isAnySquareAvailable(GameBoard gameBoard) {
        return gameBoard.getGrid().values().stream().anyMatch(this::hasSquareToDug);
    }

    private boolean hasSquareToDug(List<BoardSquare> boardSquares) {
        return boardSquares.stream().anyMatch(boardSquare -> !boardSquare.isDug());
    }

    /**
     * This method validates the input for the dig action.
     * @param params
     * @return
     * @throws InvalidDigPositionException
     */
    public boolean isDigValid(String... params) throws InvalidDigPositionException {
        if (params.length != 2) {
            throw new InvalidDigPositionException();
        }

        try {

            String column = params[0];
            int row = Integer.parseInt(params[1]);

            if (!GameUtils.gameBoardColumnLabels().contains(column) || !NumberUtils.isBetween(row, 1, 10)) {
                throw new InvalidDigPositionException();
            }
        } catch (Exception e) {
            throw new InvalidDigPositionException();
        }

        return true;
    }

    /**
     * This method validates the input for the dig action (OPTION WITH 1 INPUT).
     * @param param
     * @return
     * @throws InvalidDigPositionException
     */
    public boolean isDigValid(Game game, String param) throws InvalidDigPositionException {
        if (param == null || param.length() != 2 || !isDigValid(param.split("(?!^)"))) {
            throw new InvalidDigPositionException();
        }
        String[] params = param.split("(?!^)");
        try {
            String col = params[0];
            int row = Integer.parseInt(params[1]);
            if (game.getGameBoard().isDug(col, row)) {
                throw new AlreadyDugException();
            }
        } catch (Exception e) {
            throw new InvalidDigPositionException();
        }

        return true;
    }
}
