package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.GodActivationChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {

    Model model;
    Controller controllerUnderTest;

    @Before
    public void modelSetUp()
    {
        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model=new Model(listaNomi);
        model.getTurn().getBoardInstance().newBoard();
    }

    @Before
    public void controllerSetUp()
    {
        try {
            controllerUnderTest=new Controller(model,2);
        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        }
    }

    /**Short simulation for turnFlow**/
    @Test
    public void normalTurnFlowTest()
    {
        //Players place setup workers
        Choice c= new SelectWorkerCellChoice(0,0);
        c.setId(0);
        controllerUnderTest.update(c);
        c= new SelectWorkerCellChoice(1,1);
        c.setId(0);
        controllerUnderTest.update(c);
        c= new SelectWorkerCellChoice(2,2);
        c.setId(1);
        controllerUnderTest.update(c);
        c= new SelectWorkerCellChoice(3,3);
        c.setId(1);
        controllerUnderTest.update(c);

        //Now the standard turn should begin for Pippo (P1)

        //Tries a choice from Pluto(P2), should be ignored
        c= new SelectWorkerCellChoice(0,0);
        c.setId(1);
        controllerUnderTest.update(c);

        c= new GodActivationChoice(false);
        c.setId(0);
        controllerUnderTest.update(c);

        c= new GodActivationChoice(false);
        c.setId(1);
        controllerUnderTest.update(c);

    }




}