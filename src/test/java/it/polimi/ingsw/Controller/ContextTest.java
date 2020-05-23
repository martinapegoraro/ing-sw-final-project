package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.InitialPositionChoice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ContextTest {


    Model model;
    Context contextUnderTest;


    @Before
    public void modelSetUp()
    {
        List<String> listaNomi=new ArrayList<String>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model=new Model(listaNomi);
        model.getTurn().getBoardInstance().newBoard();
        Box b1=model.getTurn().getBoardInstance().getBox(1,1);
        Box b2=model.getTurn().getBoardInstance().getBox(4,1);
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1,b2);
        Box b3=model.getTurn().getBoardInstance().getBox(2,3);
        Box b4=model.getTurn().getBoardInstance().getBox(4,0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        model.updateModelRep(model.getModelRep().currentState);
    }

    @Before
    public void setUp() throws Exception {
        contextUnderTest=new Context(model);
    }

    @Test(expected = NullPointerException.class)
    public void updateWithNullChoice() throws NullPointerException
    {
        contextUnderTest.update(null);
    }






}