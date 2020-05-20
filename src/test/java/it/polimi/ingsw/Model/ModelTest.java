package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.BeginTurnState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {
    Model modelTest;
    BeginTurnState beginTurnState;
    Box b1, b2, b3, b4, b5;
    Board instanceBoard;

    @Before

    public void initializeModel ()
    {
        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add("paperino");
        modelTest =new Model(listaNomi);
        instanceBoard = modelTest.getTurn().getBoardInstance();
        //FIXME: This could be a problem of dangling reference! Careful when declaring board objects
        instanceBoard.newBoard();
        beginTurnState = new BeginTurnState();
        b1 = instanceBoard.getBox(1,2);
        b2 = instanceBoard.getBox(0,3);
        b3 = instanceBoard.getBox(2,2);
        b4 = instanceBoard.getBox(3,1);
        b5 = instanceBoard.getBox(4,1);
    }

    @Test
    public void getTurn()
    {

        assertNotNull(modelTest.getTurn());

    }

    @Test
    public void hasWonHasLostTest()
    {
        modelTest.getTurn().getPlayersList().get(2).setHasLost();
        modelTest.updateModelRep();
        ModelRepresentation modelRep=modelTest.getModelRep();
        assertTrue(modelRep.getHasLost()[2]);

        modelTest.getTurn().getPlayersList().get(0).setHasWon();
        modelTest.updateModelRep();
        modelRep=modelTest.getModelRep();
        assertTrue(modelRep.getHasWon()[0]);
    }

    @Test
    public void createModelRep()
    {
        assertNotNull(modelTest.getModelRep());

        assertEquals(3, modelTest.getModelRep().getPlayerNum());
    }


    @Test
    public void updateModelRep()
    {
        beginTurnState.startup(modelTest);
        assertEquals(1, modelTest.getModelRep().getActivePlayer());
        beginTurnState.startup(modelTest);
        assertEquals (2, modelTest.getModelRep().getActivePlayer());


        modelTest.getTurn().getPlayersList().get(2).setWorkersPosition(b1,b2);
        modelTest.updateModelRep();

        int [][] workers = modelTest.getModelRep().getWorkerPosition();

        assertEquals(2, workers[1][2]);
        assertEquals(-1, workers[0][2]);
        assertEquals(2, workers[0][3]);

        modelTest.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        modelTest.updateModelRep();
        int [][] workers1 = modelTest.getModelRep().getWorkerPosition();

        assertEquals(1, workers1[2][2]);
        assertEquals(1, workers1 [3][1]);
        assertEquals(-1, workers1 [1][1]);

        b5.build();
        modelTest.updateModelRep();
        int [][] towers = modelTest.getModelRep().getTowerPosition();
        assertEquals(1, towers[4][1]);



    }
}
