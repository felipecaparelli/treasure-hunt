package org.cct.cp2019a.treasurehunt.exception;

import org.cct.cp2019a.treasurehunt.constant.GameRuleMessages;

/**
 * Exception throw when a dig was already dug by another player.
 */
public class AlreadyDugException extends DigException {

    public AlreadyDugException() {
        super(GameRuleMessages.CANNOT_DIG_TWICE_RULE);
    }
}
