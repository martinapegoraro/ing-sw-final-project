package it.polimi.ingsw.Controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeginTurnStateTest {
    BeginTurnState beginTurnStateTest;
    @Before
    public void initializeState() {beginTurnStateTest = new BeginTurnState()}

    @Test
    public void getID()
    {

    }



    @Test
    public void hasFinished()
    {
        beginTurnStateTest.hasFinished() = false;
        assertFalse(beginTurnStateTest.hasFinished());
    }
}