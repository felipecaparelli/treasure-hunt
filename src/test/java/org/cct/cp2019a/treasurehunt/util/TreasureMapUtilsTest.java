package org.cct.cp2019a.treasurehunt.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreasureMapUtilsTest {

    @Test
    void readPiratePetesSecretJournal() {
        assertNotNull(TreasureMapUtils.readPiratePetesSecretJournal());
    }
}