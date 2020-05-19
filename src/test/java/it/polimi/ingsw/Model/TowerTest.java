package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TowerTest {

    Tower testTower;

    @Before
    public void instantiateTower()
    {
        testTower=new Tower();
    }

    @Test
    public void getTowerHeightTest()
    {
        assertEquals(0,testTower.getHeight());
    }

    @Test
    public void getTowerPiecesTest()
    {
        assertNotNull(testTower.getPieces());
    }

    @Test
    public void twoLevelsOrdinaryBuildTest() throws TowerCompleteException
    {
        testTower.build();
        testTower.build();
        assertEquals(2,testTower.getHeight());
    }

    @Test(expected = TowerCompleteException.class)
    public void fiveLevelsTowerTest() throws TowerCompleteException
    {
        testTower.build();
        testTower.build();
        testTower.build();
        testTower.build();
        testTower.build();
    }

    @Test
    public void domeInsteadOfLevel2Test() throws TowerCompleteException
    {
        testTower.build();
        testTower.build(Block.DOME);
        assertEquals(2,testTower.getHeight());
        assertEquals(Block.DOME,testTower.getPieces().get(1));
    }

    @Test(expected = TowerCompleteException.class)
    public void buildOverADomeTest() throws TowerCompleteException
    {
        testTower.build(Block.DOME);
        testTower.build();

    }


}