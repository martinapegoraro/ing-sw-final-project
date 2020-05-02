package it.polimi.ingsw.Controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivationGodStateTest {
    ActivationGodState activationGodStateTest;
    @Before
    public void initializeState () { activationGodStateTest = new ActivationGodState();}

    @Test
    public void getID()
    {
        assertEquals(2, activationGodStateTest.getID());
    }


    @Test
    public void hasFinished()
    {
        assertFalse(activationGodStateTest.hasFinished());
    }
}