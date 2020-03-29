package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.util.StringUtils;

/**
 * This model represents the square on the board game (grid). It has a column name, a row number, an optional treasure,
 * and the control to know if has been dug ir not.
 */
public class BoardSquare {

    private String column;
    private int row;
    private boolean dug;
    private Treasure treasure;

    public BoardSquare(String column, int row, Treasure treasure) {
        this.column = column;
        this.row = row;
        this.treasure = treasure;
    }

    public String getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isDug() {
        return dug;
    }

    public void setDug(boolean dug) {
        this.dug = dug;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public boolean hasTreasure() {
        return treasure != null;
    }

    private String noneOrValue(Treasure treasure) {
        String treasureValue = hasTreasure() && treasure.getValue() > 0 ? String.valueOf(treasure.getValue()) : null;
        return StringUtils.zeroOrValue(treasureValue);
    }

    @Override
    public String toString() {
        String content = dug ? noneOrValue(treasure) : "   ";
        return '{' + content + '}';
    }
}
