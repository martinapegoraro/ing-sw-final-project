package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EndTurnStateTest {

    State stateUnderTest;
    Model model;


    @Before
    public void modelSetUp()
    {
        List<String> listaNomi= new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model=new Model(listaNomi);
        stateUnderTest=new EndTurnState(model);
        Board.newBoard();
        Box b1=model.getTurn().getBoardInstance().getBox(1,1);
        Box b2=model.getTurn().getBoardInstance().getBox(4,1);
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1,b2);
        Box b3=model.getTurn().getBoardInstance().getBox(2,3);
        Box b4=model.getTurn().getBoardInstance().getBox(4,0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        model.updateModelRep(StateEnum.EndTurn);
    }

    @Test
    public void getID() {
        assertEquals(StateEnum.EndTurn,stateUnderTest.getID());
    }

    @Test
    public void startup() {
        stateUnderTest.startup(model);
        assertTrue(stateUnderTest.hasFinished());
    }



    @Test
    public void hasFinished() {
        assertTrue(stateUnderTest.hasFinished());
    }
}