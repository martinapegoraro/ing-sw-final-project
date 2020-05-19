package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BeginTurnStateTest {
    Model model;
    BeginTurnState beginTurnStateTest;
    @Before
    public void initializeState()
    {
        List<String> listaNomi=new ArrayList<String>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add ("paperino");
        model=new Model(listaNomi);
        model.getTurn().getBoardInstance().newBoard();
        beginTurnStateTest = new BeginTurnState();
    }

    @Test
    public void getID()
    {
        assertEquals(StateEnum.BeginTurn, beginTurnStateTest.getID());
    }



    @Test
    public void hasFinished()
    {
        assertFalse(beginTurnStateTest.hasFinished());
    }

    @Test
     public void startup()
    {
        List<Player> players = model.getTurn().getPlayersList();
        assertEquals(players.get(0), model.getTurn().getCurrentPlayer());
        beginTurnStateTest.startup(model);
        assertEquals(players.get(1), model.getTurn().getCurrentPlayer());
        model.getTurn().getPlayersList().get(2).setHasLost();
        beginTurnStateTest.startup(model);
        assertEquals (players.get(0), model.getTurn().getCurrentPlayer());
        beginTurnStateTest.startup(model);
        assertEquals(true, beginTurnStateTest.hasFinished());

    }
}