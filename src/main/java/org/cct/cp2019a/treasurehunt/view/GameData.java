package org.cct.cp2019a.treasurehunt.view;

import org.cct.cp2019a.treasurehunt.model.Player;

public class GameData {

    private int numberOfPlayer;
    private Player winner;

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
