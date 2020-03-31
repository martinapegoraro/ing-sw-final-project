package it.polimi.ingsw.Module;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {
    //Modificabili per controllare proprietà specifiche di una box
    Box testBox=new Box(1,1);
    @Test
    public void isOccupied() {
        assertFalse(testBox.isOccupied());
    }

    @Test
    public void getTowerEmpty() {
        assertNull(testBox.getTower());
    }

    @Test
    public void build() {
        assertNull(testBox.getTower());
        testBox.build();
        assertNotEquals(0,testBox.getTower().getHeight());
    }
}