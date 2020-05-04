package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    private Box testBox;

    @Before
    public void initializeBox()
    {
        testBox=new Box(0,1);
    }

    //given an empty box it have to be unoccupied
    @Test
    public void unoccupiedBoxTest()
    {
        assertFalse(testBox.isOccupied());
    }

    // given an empty box it should not have any tower
    @Test
    public void nullTowerInBoxTest()
    {
        assertNotNull(testBox.getTower());
    }

    @Test
    public void getCoordinatesTest()
    {
        assertEquals(0,testBox.getCoord()[0]);
        assertEquals(1,testBox.getCoord()[1]);
    }

    //given an empty box and built a block I should have a one level tower
    @Test
    public void buildTest()
    {
        testBox.build();
        assertEquals(1,testBox.getTower().getHeight());
    }

    //given an empty box and called 5 times the build method
    //I should have occupied true due the exception handling
    @Test
    public void towerCompleteExceptionTest()
    {
        testBox.build();
        testBox.build();
        testBox.build();
        testBox.build();
        testBox.build();
        assertTrue(testBox.isOccupied());
    }

    @Test
    public void sameHeightReachableTest()
    {
        Box testBox2=new Box(1,1);
        assertTrue(testBox.isReachable(testBox2));
    }

    @Test
    public void oneLevelUpHeightReachableTest()
    {
        Box testBox2=new Box(1,1);
        testBox2.build();
        assertTrue(testBox.isReachable(testBox2));
    }

    @Test
    public void towLevelsUpHeightReachableTest()
    {
        Box testBox2=new Box(1,1);
        testBox2.build();
        testBox2.build();
        assertFalse(testBox.isReachable(testBox2));
    }
    @Test
    public void towLevelsDownHeightReachableTest()
    {
        Box testBox2=new Box(1,1);
        testBox.build();
        testBox.build();
        assertTrue(testBox.isReachable(testBox2));
    }

    @Test
    public void equalsTest()
    {
        Box b2=new Box(0,0);
        assertFalse(testBox.equals(b2));
    }

}