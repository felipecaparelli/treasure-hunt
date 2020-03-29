package org.cct.cp2019a.treasurehunt.exception;

import org.cct.cp2019a.treasurehunt.constant.GameMessages;

/**
 * This exception is thrown when there is no more point for the pirate to dig.
 */
public class NoMoreDigPointsException extends DigException {
    public NoMoreDigPointsException() {
        super(GameMessages.EMPTY_DIG_POINTS_MESSAGE);
    }
}
