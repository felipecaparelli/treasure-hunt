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

    /**
     * Checks if the number os players is valid (between 2 and 4).
     * VALIDATION RULE: There has to be a minimum of 2 players and a maximum of 4 Players
     *
     * @param numberOfPlayers the number of players
     * @return return true if all rules are OK,
     * and throws a {@link org.cct.cp2019a.treasurehunt.exception.GameRuleException} if not.
     */
    public boolean isValidPlayerNum(int numberOfPlayers) throws GameRuleException {
        if(!NumberUtils.isBetween(numberOfPlayers, MIN_NUM_OF_PLAYER, MAX_NUM_OF_PLAYER)) {
            throw new GameRuleException(GameRuleMessages.NUM_OF_PLAYERS_RULE);
        }
        return true;
    }

    /**
     * Validates the player's name.
     * VALIDATION RULE: The player has to enter their name in one line and must enter a first name and surname
     *
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
     * @return true if the game is over
     */
    public boolean isGameOver(Game game) {
        return hasNoTreasureMissing(game)
            || hasNoSquareToDig(game.getGameBoard())
            || hasNoPlayerWithDigPoints(game.getPlayers());
    }

    private boolean hasNoTreasureMissing(Game game) {
        return !game.getGameBoard().isThereAnyTreasureMissing();
    }

    private boolean hasNoPlayerWithDigPoints(List<Player> players) {
        return players.stream().noneMatch(player -> player.getShovel().getDigPoints() > 0);
    }

    private boolean hasNoSquareToDig(GameBoard gameBoard) {
        return gameBoard.getGrid().values().stream().noneMatch(this::hasSquareToDug);
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
    public boolean isValidDigPosition(String... params) throws InvalidDigPositionException {
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
    public boolean isValidDigPosition(Game game, String param) throws InvalidDigPositionException {
        if (param == null || !NumberUtils.isBetween(param.length(), 2, 3)) {
            throw new InvalidDigPositionException();
        }

        try {

            String col = param.substring(0, 1).toUpperCase();
            int row = Integer.parseInt(param.substring(1));

            if (!GameUtils.gameBoardColumnLabels().contains(col)) {
                throw new InvalidDigPositionException();
            }

            if(!NumberUtils.isBetween(row, 1, 10)) {
                throw new InvalidDigPositionException();
            }

            if (game.getGameBoard().isDug(col, row)) {
                throw new AlreadyDugException();
            }
        } catch (Exception e) {
            throw e;
        }

        return true;
    }
}
