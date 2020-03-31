package it.polimi.ingsw.Module;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    Player giocatore1 = new  Player(1, "Mario");
    Player giocatore2 = new Player(2, "Luca");
    Player giocatore3 = new Player(3, "Giovanni");
    @Test
    public void getActive() {
        assertTrue(giocatore1.getActive());
        assertFalse(giocatore2.getActive());
        assertFalse(giocatore3.getActive());
    }

    @Test
    public void getWorkerList() {
        assertNull(giocatore1.getWorkerList());
    }
}