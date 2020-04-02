package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.enumeration.GameStatus;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * Inits the game structure (game board and slots for players)
     * @param gameBoard game board (treasure map)
     * @param numberOfPlayer the number of players
     */
    public void init(GameBoard gameBoard, int numberOfPlayer) {
        this.setGameStatus(GameStatus.PLAYER_SELECTION);
        this.gameBoard = gameBoard;
        this.playerSlots = numberOfPlayer;
        this.slots = new ArrayList<>(numberOfPlayer);
    }

    /**
     * Set the game as started.
     */
    public void start() {
        this.getSlots().forEach(slot -> this.players.add(new Player(slot.getFullName(), slot.getAge())));
        this.activePlayer = this.players.get(0);
        this.gameStatus = GameStatus.STARTED;
    }

    /**
     * Change the active player for the next one.
     */
    public void changeTurn() {
        if (this.players.indexOf(this.activePlayer) == this.players.size() - 1) {
            this.activePlayer = this.players.get(0);
        } else {
            this.activePlayer = this.players.get(this.players.indexOf(this.activePlayer) + 1);
        }
    }

    /**
     * Returns if there is any slot to fill with a player
     * @return true if there are slots pending to be filled
     */
    public boolean hasAnySlotToFill() {
        return this.playerSlots != this.slots.size();
    }

    /**
     * Returns the actual game status
     * @return The game status. See {@link GameStatus}
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Updates the game status
     * @param gameStatus the new game status
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Returns the game players
     * @return a list with all players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the actual active player. See {@link Player}
     * @return the active player
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Returns a list of slots to be used by the profiling
     * @return a list of {@link PlayerSlot}
     */
    public List<PlayerSlot> getSlots() {
        return slots;
    }

    /**
     * Get the number os slots to be filled.
     * @return
     */
    public int getPlayerSlots() {
        return playerSlots;
    }

    /**
     * Returns the active profile (to be filled with name and age).
     * @return {@link PlayerSlot}
     */
    public PlayerSlot getActiveProfile() {
        return activeProfile;
    }

    /**
     * Returns the game board object.
     * @return
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Move to the next profile data to be filled.
     */
    public void moveSlot() {
        PlayerSlot slot = this.activeProfile;
        this.slots.add(slot);
        this.activeProfile = new PlayerSlot();
    }

    /**
     * Returns the player with more points
     * @return the winner player
     */
    public Player getWinner() {
        return this.players.stream().max(Comparator.comparingInt(Player::getPiratePoints)).get();
    }
}
