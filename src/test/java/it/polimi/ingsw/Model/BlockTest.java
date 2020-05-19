package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {
    @Test
    public void getNextBlock() throws TowerCompleteException{
        assertEquals(Block.LEVEL2, Block.getNextBlock(Block.LEVEL1));
        assertEquals(Block.LEVEL3, Block.getNextBlock(Block.LEVEL2));
        assertEquals(Block.DOME, Block.getNextBlock(Block.LEVEL3));
        try{
            Block.getNextBlock(Block.DOME);
        } catch (TowerCompleteException e){
            e.printStackTrace();
        }
    }

    @Test(expected =  TowerCompleteException.class)
    public void testTowerCompleteException() throws TowerCompleteException{
            Block.getNextBlock(Block.DOME);
        }


}