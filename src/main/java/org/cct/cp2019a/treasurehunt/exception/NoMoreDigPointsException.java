package org.cct.cp2019a.treasurehunt.exception;

public class NoMoreDigPointsException extends IllegalStateException {
    public NoMoreDigPointsException() {
        super("No more dig points");
    }
}
