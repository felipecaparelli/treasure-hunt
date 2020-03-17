package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private GameStatus gameStatus = GameStatus.NOT_INITIALIZED;
    private int playerSlots = 0;
    private List<Player> players = new ArrayList<>(2);
    private GameBoard gameBoard;
    private Player activePlayer;

    public Game (GameBoard gameBoard, int numberOfPlayer) {
        this.gameBoard = gameBoard;
        this.playerSlots = numberOfPlayer;
    }

    public boolean addPlayer(Player player) throws GameRuleException {
        if(GameValidator.MAX_NUM_OF_PLAYER <= this.players.size()) {
            throw new GameRuleException(GameRuleMessages.NUM_OF_PLAYERS_RULE);
        }
        this.playerSlots -= 1;
        return this.players.add(player);
    }

    public void start() throws GameRuleException {
        if(this.playerSlots > 0) {
            throw new GameRuleException("You need to select all player before starting");
        }
        this.activePlayer = this.players.get(0);
        this.gameStatus = GameStatus.STARTED;
    }

    public void changeTurn() {
        if (this.players.indexOf(this.activePlayer) == this.players.size() - 1) {
            this.activePlayer = this.players.get(0);
        } else {
            this.activePlayer = this.players.get(this.players.indexOf(this.activePlayer) + 1);
        }
    }

    public boolean hasAnyPlayerSlotAvailable() {
        return this.playerSlots > 0;
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
