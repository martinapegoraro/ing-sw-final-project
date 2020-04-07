package it.polimi.ingsw.Module;

import org.junit.Test;

import static org.junit.Assert.*;

public class GodsListTest {
    GodsList god;
    @Test
    public void gettype() {
        god = GodsList.APOLLO;
        assertEquals(1, god.gettype());
        god = GodsList.ARTEMIS;
        assertEquals(1, god.gettype());
        god = GodsList.ATHENA;
        assertEquals(2, god.gettype());
        god = GodsList.ATLAS;
        assertEquals(1, god.gettype());
        god = GodsList.DEMETER;
        assertEquals(1, god.gettype());
        god = GodsList.HEPHAESTUS;
        assertEquals(1, god.gettype());
        god = GodsList.MINOTAUR;
        assertEquals(1, god.gettype());
        god = GodsList.PAN;
        assertEquals(0, god.gettype());
        god = GodsList.PROMETHEUS;
        assertEquals(1, god.gettype());
    }

    @Test
    public void getEffect() {
    }
}