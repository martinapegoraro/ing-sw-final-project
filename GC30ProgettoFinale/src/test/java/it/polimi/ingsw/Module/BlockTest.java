package it.polimi.ingsw.Module;

import it.polimi.ingsw.Module.Exceptions.TowerCompleteException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {
    Block testblock;

    @Test
    public void getNextBlock() throws TowerCompleteException {
        assertEquals(testblock.LEVEL2, testblock.getNextBlock(testblock.LEVEL1));
        assertEquals(testblock.LEVEL3, testblock.getNextBlock(testblock.LEVEL2));
        assertEquals(testblock.DOME, testblock.getNextBlock(testblock.LEVEL3));

    }
}