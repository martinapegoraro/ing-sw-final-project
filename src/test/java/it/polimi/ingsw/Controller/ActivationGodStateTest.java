package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActivationGodStateTest {
    ActivationGodState activationGodStateTest;

    @Before
    public void initializeState ()
    {
        ArrayList<String> nomi = new ArrayList<>();
        nomi.add("Mario");
        nomi.add("luigi");
        Model model = new Model(nomi);
        activationGodStateTest = new ActivationGodState(model);
    }

    @Test
    public void getID()
    {
        assertEquals(StateEnum.ActivationGod, activationGodStateTest.getID());
    }


    @Test
    public void hasFinished()
    {
        assertFalse(activationGodStateTest.hasFinished());
    }
}