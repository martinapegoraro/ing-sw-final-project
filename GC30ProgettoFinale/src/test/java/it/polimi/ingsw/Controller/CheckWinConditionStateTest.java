package it.polimi.ingsw.Controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckWinConditionStateTest {
    CheckWinConditionState checkWinConditionStateTest;
    @Before
    public void initializeState () {checkWinConditionStateTest = new CheckWinConditionState();}
    @Test
    public void getID()
    {
        assertEquals(0, checkWinConditionStateTest.getID());
    }


    @Test
    public void hasFinished()
    {
        assertFalse(checkWinConditionStateTest.hasFinished());
    }
}