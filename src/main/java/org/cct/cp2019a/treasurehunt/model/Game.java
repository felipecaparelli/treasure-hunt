package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;
import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;
import org.cct.cp2019a.treasurehunt.exception.GameRuleException;
import org.cct.cp2019a.treasurehunt.validator.GameValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the game
 */
public class Game {

    private GameStatus gameStatus = GameStatus.NOT_INITIALIZED;
    private int playerSlots = 0;
    private List<PlayerSlot> slots = null;
    private List<Player> players = new ArrayList<>(2);
    private GameBoard gameBoard;
    private Player activePlayer;
    private PlayerSlot activeProfile = new PlayerSlot();

    /** game not initialized */
    public Game () {}

    public void init(GameBoard gameBoard, int numberOfPlayer) {
        this.gameBoard = gameBoard;
        this.playerSlots = numberOfPlayer;
        this.slots = new ArrayList<>(numberOfPlayer);
    }

    @Deprecated
    public boolean addPlayer(Player player) throws GameRuleException {
        if(GameValidator.MAX_NUM_OF_PLAYER <= this.players.size()) {
            throw new GameRuleException(GameRuleMessages.NUM_OF_PLAYERS_RULE);
        }
        this.playerSlots -= 1;
        return this.players.add(player);
    }

    public void start() {
        this.getSlots().forEach(slot -> this.players.add(new Player(slot.getFullName(), slot.getAge())));
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

    public boolean hasAnySlotToFill() {
        return this.playerSlots != this.slots.size();
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

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<PlayerSlot> getSlots() {
        return slots;
    }

    public int getPlayerSlots() {
        return playerSlots;
    }

    public PlayerSlot getActiveProfile() {
        return activeProfile;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void moveSlot() {
        PlayerSlot slot = this.activeProfile;
        this.slots.add(slot);
        this.activeProfile = new PlayerSlot();
    }
}
