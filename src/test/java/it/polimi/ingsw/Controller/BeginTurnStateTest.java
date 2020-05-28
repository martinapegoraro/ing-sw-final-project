package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
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
        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add ("paperino");
        model=new Model(listaNomi);
        Board.newBoard();
        beginTurnStateTest = new BeginTurnState(model);
    }

    @Test
    public void getID()
    {
        assertEquals(StateEnum.BeginTurn, beginTurnStateTest.getID());
    }



    @Test
    public void hasFinished()
    {
        assertTrue(beginTurnStateTest.hasFinished());
    }

    @Test
     public void startup()
    {
        State endTurnState=null;
        List<Player> players = model.getTurn().getPlayersList();
        assertEquals(players.get(0), model.getTurn().getCurrentPlayer());
        beginTurnStateTest.startup(model);
        endTurnState=new EndTurnState(model);
        assertEquals(players.get(1), model.getTurn().getCurrentPlayer());

        model.getTurn().getPlayersList().get(2).setHasLost();
        endTurnState=new EndTurnState(model);
        beginTurnStateTest.startup(model);
        assertEquals (players.get(0), model.getTurn().getCurrentPlayer());
        //beginTurnStateTest.startup(model);
        assertTrue(beginTurnStateTest.hasFinished());

    }
}