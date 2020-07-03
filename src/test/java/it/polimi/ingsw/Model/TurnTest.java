package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnTest {

    Turn turnTest;

    @Before
    public void instantiateTurn()
    {

        ArrayList<String> l =new ArrayList<String>();
        l.add("pippo");
        l.add("pluto");
        turnTest=new Turn(l);
        Board.newBoard();
    }

    @Test
    public void getCurrentPlayerTest()
    {
        assertEquals(turnTest.getPlayersList().get(0),turnTest.getCurrentPlayer());
    }

    @Test
    public void getBoardInstanceTest()
    {
        assertNotNull(turnTest.getBoardInstance());
    }

    @Test(expected = ImpossibleAddAnotherPlayerException.class)
    public void addPlayerTest()throws ImpossibleAddAnotherPlayerException
    {
        turnTest.addPlayer("paperino");
        assertEquals(3,turnTest.getPlayersList().size());

        turnTest.addPlayer("tizio");
    }

    @Test
    public void getPossibleMovesTest()
    {
        assertEquals(8,turnTest.getPossibleMoves(new Box(2,3)).size());
        //TODO: bisogna aggiungere torri e ostacoli così da testare meglio la lista
    }

    @Test
    public void getPossibleBuildLocationTest()
    {
        assertEquals(3,turnTest.getPossibleBuildLocations(new Box(4,4)).size());
        //TODO: bisogna aggiungere torri e ostacoli così da testare meglio la lista
    }

    @Test
    public void setNextPlayerTest() throws ImpossibleAddAnotherPlayerException {
        turnTest.addPlayer("paperino");
        assertEquals(turnTest.getPlayersList().get(0),turnTest.getCurrentPlayer());
        turnTest.setNextPlayer();
        assertEquals(turnTest.getPlayersList().get(1),turnTest.getCurrentPlayer());
        turnTest.setNextPlayer();
        assertEquals(turnTest.getPlayersList().get(2),turnTest.getCurrentPlayer());
        turnTest.getPlayersList().get(0).setHasLost();
        turnTest.setNextPlayer();
        assertEquals(turnTest.getPlayersList().get(1),turnTest.getCurrentPlayer());
        turnTest.getPlayersList().get(2).setHasLost();
        turnTest.setNextPlayer();
        assertEquals(turnTest.getPlayersList().get(1),turnTest.getCurrentPlayer());


    }


}