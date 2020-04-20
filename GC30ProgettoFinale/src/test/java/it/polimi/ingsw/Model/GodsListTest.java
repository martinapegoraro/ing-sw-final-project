package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GodsListTest {
    GodsList god;
    /** @Test
    public void getType() {
        god = GodsList.APOLLO;
        assertEquals(1, god.getType());
        god = GodsList.ARTEMIS;
        assertEquals(1, god.getType());
        god = GodsList.ATHENA;
        assertEquals(2, god.getType());
        god = GodsList.ATLAS;
        assertEquals(1, god.getType());
        god = GodsList.DEMETER;
        assertEquals(1, god.getType());
        god = GodsList.HEPHAESTUS;
        assertEquals(1, god.getType());
        god = GodsList.MINOTAUR;
        assertEquals(1, god.getType());
        god = GodsList.PAN;
        assertEquals(0, god.getType());
        god = GodsList.PROMETHEUS;
        assertEquals(1, god.getType());
    }**/

    @Test
    public void getName() {
        god = GodsList.APOLLO;
        assertEquals("Apollo", god.getName());
        god = GodsList.ARTEMIS;
        assertEquals("Artemis", god.getName());
        god = GodsList.ATHENA;
        assertEquals("Athena", god.getName());
        god = GodsList.ATLAS;
        assertEquals("Atlas", god.getName());
        god = GodsList.DEMETER;
        assertEquals("Demeter", god.getName());
        god = GodsList.HEPHAESTUS;
        assertEquals("Hephaestus", god.getName());
        god = GodsList.MINOTAUR;
        assertEquals("Minotaur", god.getName());
        god = GodsList.PAN;
        assertEquals("Pan", god.getName());
        god = GodsList.PROMETHEUS;
        assertEquals("Prometheus", god.getName());
    }
}