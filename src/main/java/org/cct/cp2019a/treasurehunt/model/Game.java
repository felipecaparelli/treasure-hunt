package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private GameStatus gameStatus = GameStatus.NOT_INITIALIZED;
    private List<Player> players = new ArrayList<>(2);
    private GameBoard gameBoard;

    public Game (GameBoard gameBoard) {
        this.gameStatus = GameStatus.STARTED;
        this.gameBoard = gameBoard;
    }

    public boolean addPlayer(Player player) throws GameRuleException {
        if(GameValidator.MAX_NUM_OF_PLAYER <= this.players.size()) {
            throw new GameRuleException(GameRuleMessages.NUM_OF_PLAYERS_RULE);
        }
        return this.players.add(player);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
