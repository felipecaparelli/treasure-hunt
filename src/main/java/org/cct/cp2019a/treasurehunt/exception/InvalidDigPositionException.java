package org.cct.cp2019a.treasurehunt.exception;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;

/**
 * This exception is throw when the player set invalid params for the dig position.
 */
public class InvalidDigPositionException extends DigException {

    public InvalidDigPositionException() {
        super(GameRuleMessages.INVALID_DIG_POSITION);
    }
}
