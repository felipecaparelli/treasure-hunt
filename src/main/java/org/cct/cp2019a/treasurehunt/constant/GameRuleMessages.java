package org.cct.cp2019a.treasurehunt.constant;

/**
 * The game rule messages.
 */
public final class GameRuleMessages {
    // validation rules messages
    public static final String NUM_OF_PLAYERS_RULE = "There has to be a minimum of 2 players and a maximum of 4 players";
    public static final String PLAYER_NAME_RULE = "Please enter your full name (first name and surname)";
    public static final String PLAYER_AGE_RULE = "The player’s age must be 12 or over";
    // warning messages
    public static final String CANNOT_DIG_TWICE_RULE = "This position has already been dug";
    public static final String INVALID_DIG_POSITION = "Invalid dig position (Please use A-J for column and 1-10 for row)";
}
