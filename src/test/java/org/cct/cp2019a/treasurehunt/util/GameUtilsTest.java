package org.cct.cp2019a.treasurehunt.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    @Test
    void gameBoardColumnLabels() {
        List<String> columnLabels = GameUtils.gameBoardColumnLabels();
        assertNotNull(columnLabels);
        assertEquals(columnLabels.size(), 10);
        assertEquals(columnLabels.get(0), "A");
        assertEquals(columnLabels.get(9), "J");
    }

    @Test
    void buildGameBoard() {
        assertNotNull(GameUtils.buildGameBoard());
    }
}