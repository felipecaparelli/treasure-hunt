package org.cct.cp2019a.treasurehunt.enumeration;

/**
 * The enum that represents the {@link org.cct.cp2019a.treasurehunt.model.Game} status.
 */
public enum GameStatus {
    NOT_INITIALIZED,
    PLAYER_SELECTION,
    ADDING_PLAYER_NAME,
    ADDING_PLAYER_AGE,
    STARTED,
    PLAYER_ROUND,
    GAME_OVER;
}
