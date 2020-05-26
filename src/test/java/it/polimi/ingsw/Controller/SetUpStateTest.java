package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Block;
import it.polimi.ingsw.Model.Board;
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

public class SetUpStateTest {

    State stateUnderTest;
    Model model;


    @Before
    public void stateSetUp()
    {

        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add("paperino");
        model=new Model(listaNomi);
        Board.newBoard();
        stateUnderTest=new SetUpState(model);
    }

    @Test
    public void getIDTest()
    {
        assertEquals(StateEnum.SetUp,stateUnderTest.getID());
    }

    @Test
    public void startUpTest()
    {
        stateUnderTest.startup(model);
        assertEquals(model.getTurn().getCurrentPlayer().getPlayerName(),model.getTurn().getPlayersList().get(0).getPlayerName());

    }

    @Test(expected = BoxAlreadyOccupiedException.class)
    public void updateTestWithOccupiedBox() throws BoxAlreadyOccupiedException, BuildErrorException, WrongChoiceException, MoveErrorException, TowerCompleteException, GodConditionNotSatisfiedException {
        Choice c= new SelectWorkerCellChoice(1,1);
        model.getTurn().getBoardInstance().getBox(1,1).getTower().build(Block.DOME);
        model.getTurn().getBoardInstance().getBox(1,1).setOccupied();
        stateUnderTest.update(c,model);
    }


    @Test(expected = WrongChoiceException.class)
    public void updateTestWithWrongChoice() throws BoxAlreadyOccupiedException, BuildErrorException, WrongChoiceException, MoveErrorException, GodConditionNotSatisfiedException {
        Choice c= new MoveChoice(1,1);
        stateUnderTest.update(c,model);
    }

    @Test
    public void updateTest()
    {
        Choice c= new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest.update(c,model);
        } catch (WrongChoiceException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }
        Choice c1= new SelectWorkerCellChoice(3,1);
        try {
            stateUnderTest.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException | BuildErrorException e) {
            e.printStackTrace();
        }

    }
}