package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.MoveChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveStateTest {
    Model model;
    State stateUnderTest;

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
        model.updateModelRep();
    }

    @Before
    public void correctMoveStateSetUp()
    {
        //TODO: Test Gods special conditions are correctly applied
       stateUnderTest=new MoveState(
               (ArrayList<Box>)model.getTurn().getPossibleMoves( model.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition()),
               (ArrayList<Box>)model.getTurn().getPossibleMoves( model.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition()),
               false,false, false, model);
    }

    @Test
    public void emptyPossibleMovesListTest(){
        ArrayList<Box> listaVuota=new ArrayList<>();
        State s=new MoveState(listaVuota,listaVuota,false,false, false, model);
        assertTrue(model.getTurn().getCurrentPlayer().getHasLost());
    }

    @Test
    public void getIdTest()
    {
        assertEquals(StateEnum.Move,stateUnderTest.getID());
    }

    @Test
    public void updateSelectWorkerCellChoiceTest()
    {
        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest.update(c,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | BoxAlreadyOccupiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = MoveErrorException.class)
    public void updateWithMoveErrorWorkerNotBelongingTest() throws MoveErrorException, WrongChoiceException, BuildErrorException, BoxAlreadyOccupiedException, GodConditionNotSatisfiedException {
        Choice c=new SelectWorkerCellChoice(2,3);
        stateUnderTest.update(c,model);
    }

    @Test(expected = MoveErrorException.class)
    public void updateWithMoveErrorWorkerNotPresentTest() throws MoveErrorException, WrongChoiceException, BuildErrorException, BoxAlreadyOccupiedException, GodConditionNotSatisfiedException {
        Choice c=new SelectWorkerCellChoice(2,2);
        stateUnderTest.update(c,model);
    }

    @Test
    public void updateMoveChoiceTest()
    {
        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest.update(c,model);
        } catch (WrongChoiceException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }
        Choice c1=new MoveChoice(2,2);
        try {
            stateUnderTest.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest.hasFinished());
    }


}