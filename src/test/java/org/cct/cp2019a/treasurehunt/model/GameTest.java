package org.cct.cp2019a.treasurehunt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getWinner() {
        Game game = new Game();
        game.getPlayers().add(new Player("Carlos Cardoso", 19));
        game.getPlayers().add(new Player("John Morris", 16));
        game.getPlayers().add(new Player("Janis Joplin", 20));
        game.getPlayers().get(0).setPiratePoints(80);
        game.getPlayers().get(1).setPiratePoints(60);
        game.getPlayers().get(2).setPiratePoints(40);

        assertEquals(game.getWinner().getFullName(), "Carlos Cardoso");
        assertEquals(game.getWinner().getPiratePoints(), 80);
    }
}