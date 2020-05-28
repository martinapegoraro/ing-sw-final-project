package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
        Board.newBoard();
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
        /*SETUP:
         * Player0: Pippo       Player1: Pluto
         *
         *    0     1     2     3     4
         *       |     |     |     |
         * 0  W0 |  -  |  -  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 1  -  |  W0 |  -  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 2  -  |  -  |  W1 |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 3  -  |  -  |  -  |  W1 |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 4  -  |  -  |  -  |  -  |  -
         *       |     |     |     |
         *
         *       */

        c= new GodActivationChoice(false);
        c.setId(0);
        controllerUnderTest.update(c);

        c= new GodActivationChoice(false);
        c.setId(1);
        controllerUnderTest.update(c);

        //Now the standard turn should begin for Pippo (P1)
        //assertEquals(StateEnum.ActivationGod, model.getModelRep().currentState);
        //Tries a choice from Pluto(P2), should be ignored
        c= new SelectWorkerCellChoice(0,0);
        c.setId(1);
        controllerUnderTest.update(c);

        //assertEquals(StateEnum.ActivationGod, model.getModelRep().currentState);



        //Pippo performs a move
        /*MOVE BY PIPPO:
         * Player0: Pippo       Player1: Pluto
         *
         *    0     1     2     3     4
         *       |     |     |     |
         * 0  ----> W0 |  -  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 1  -  |  W0 |  -  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 2  -  |  -  |  W1 |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 3  -  |  -  |  -  |  W1 |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 4  -  |  -  |  -  |  -  |  -
         *       |     |     |     |
         *
         *       */

        assertEquals(StateEnum.Move,model.getModelRep().currentState);
        c= new SelectWorkerCellChoice(0,0);
        c.setId(0);
        controllerUnderTest.update(c);

        //A wrong move is passed, should be rejected
        c= new MoveChoice(1,1);
        c.setId(0);
        controllerUnderTest.update(c);

        //Valid move is now passed
        c = new MoveChoice(0,1);
        controllerUnderTest.update(c);

        //worker has moved
        assertFalse(model.getTurn().getBoardInstance().getBox(0,0).isOccupied());
        assertTrue(model.getTurn().getBoardInstance().getBox(0,1).isOccupied());

        //the worker now builds
        /*MOVE BY PIPPO:
         * Player0: Pippo       Player1: Pluto
         *
         *    0     1     2     3     4
         *       |     | L1  |     |
         * 0  ----> W0 |  ^  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 1  -  |  W0 |  -  |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 2  -  |  -  |  W1 |  -  |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 3  -  |  -  |  -  |  W1 |  -
         *  _____|_____|_____|_____|_____
         *       |     |     |     |
         * 4  -  |  -  |  -  |  -  |  -
         *       |     |     |     |
         *
         *       */

        assertEquals(model.getModelRep().currentState, StateEnum.Build);
        //First a wrong worker cell is passed
        c= new SelectWorkerCellChoice(0,0);
        c.setId(0);
        controllerUnderTest.update(c);

        //Now the correct one
        c= new SelectWorkerCellChoice(0,1);
        c.setId(0);
        controllerUnderTest.update(c);

        //then a wrong build cell is passed
        c= new BuildChoice(1,1);
        c.setId(0);
        controllerUnderTest.update(c);

        //finally the right build cell is passed
        c= new BuildChoice(0,2);
        c.setId(0);
        controllerUnderTest.update(c);

        assertEquals(1, model.getTurn().getBoardInstance().getBox(0, 2).getTower().getHeight());
        //have to look at getTower usages and fix
        assertEquals(0, model.getTurn().getBoardInstance().getBox(1, 1).getTower().getHeight());


    }




}