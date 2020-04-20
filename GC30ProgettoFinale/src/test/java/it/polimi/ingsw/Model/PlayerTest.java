package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player giocatore1 = new  Player(1, "Mario");
    Player giocatore2 = new Player(2, "Luca");
    Player giocatore3 = new Player(3, "Giovanni");
    @Test
    public void getActive() {
        assertTrue(giocatore1.isPlayerActive());
        assertFalse(giocatore2.isPlayerActive());
        assertFalse(giocatore3.isPlayerActive());
    }

    @Test
    public void getWorkerList() {
        assertNull(giocatore1.getWorkerList());
    }
}