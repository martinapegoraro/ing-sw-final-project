package it.polimi.ingsw.Module;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {
    Box testBox=new Box();
    @Test
    public void isOccupied() {
        assertEquals(false,testBox.isOccupied());
    }

    @Test
    public void getTowerEmpty() {
        assertEquals(null,testBox.getTower());
    }

    @Test
    public void build() {
        assertEquals(null,testBox.getTower());
        testBox.build();
        assertNotEquals(0,testBox.getTower().getHeight());
    }
}