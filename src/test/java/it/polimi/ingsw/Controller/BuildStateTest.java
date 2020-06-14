package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Block;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.BuildChoice;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.MoveChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BuildStateTest {

    Model model;
    State stateUnderTest1,stateUnderTest2;

    @Before
    public void modelSetUp()
    {
        List<String> listaNomi= new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model=new Model(listaNomi);
        Board.newBoard();
        Box b1=model.getTurn().getBoardInstance().getBox(1,1);
        Box b2=model.getTurn().getBoardInstance().getBox(4,1);
        model.getTurn().getBoardInstance().getBox(2,2).build();
        model.getTurn().getBoardInstance().getBox(2,2).build();
        try {
            //model.getTurn().getBoardInstance().getBox(2, 2).getTower().build(Block.DOME);
            model.getTurn().getBoardInstance().getBox(0, 1).getTower().build(Block.LEVEL2);
            model.getTurn().getBoardInstance().getBox(0, 2).getTower().build(Block.LEVEL3);
        } catch (TowerCompleteException e) {
            e.printStackTrace();
        }
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1,b2);
        Box b3=model.getTurn().getBoardInstance().getBox(2,3);
        Box b4=model.getTurn().getBoardInstance().getBox(4,0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        model.updateModelRep(StateEnum.Build);

    }

    @Before
    public void setUpBuildStates()
    {
        stateUnderTest1=new BuildState(new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition())),
                new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition())),
                false,false,model,true);

        stateUnderTest2=new BuildState(
                new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition())),
                new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition())),
                true,false,model,true);
    }

    @Test
    public void getIdTest()
    {
        assertEquals(StateEnum.Build,stateUnderTest1.getID());
        assertEquals(StateEnum.Build,stateUnderTest2.getID());
    }

    @Test
    public void hasFinishedTest()
    {
        assertFalse(stateUnderTest1.hasFinished());
        assertFalse(stateUnderTest2.hasFinished());
    }

    @Test
    public void updateCorrectBuildChoiceForStateUnderTest1Level1Tower()
    {

        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest1.update(c,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | MoveErrorException | BoxAlreadyOccupiedException | BuildErrorException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(0,1);
        try {
            stateUnderTest1.update(c1,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | BuildErrorException | BoxAlreadyOccupiedException | MoveErrorException e) {
            e.printStackTrace();
        }

        assertTrue(stateUnderTest1.hasFinished());

    }

    @Test
    public void updateCorrectBuildChoiceForStateUnderTest1Level2Tower()
    {

        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest1.update(c,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | BoxAlreadyOccupiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(2,2);
        try {
            stateUnderTest1.update(c1,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | BoxAlreadyOccupiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest1.hasFinished());

    }

    @Test
    public void updateCorrectBuildChoiceForStateUnderTest1Level3Tower()
    {

        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest1.update(c,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | BoxAlreadyOccupiedException | BuildErrorException | MoveErrorException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(0,1);
        try {
            stateUnderTest1.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest1.hasFinished());

    }

    @Test
    public void updateCorrectBuildChoiceForStateUnderTest1DomeTower()
    {

        Choice c=new SelectWorkerCellChoice(1,1);
        try {
            stateUnderTest1.update(c,model);
        } catch (WrongChoiceException | GodConditionNotSatisfiedException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(0,2);
        try {
            stateUnderTest1.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest1.hasFinished());

    }

    @Test
    public void updateCorrectBuildChoiceForStateUnderTest2()
    {

        Choice c=new SelectWorkerCellChoice(4,1);
        try {
            stateUnderTest2.update(c,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(3,0);
        try {
            stateUnderTest2.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest2.hasFinished());

    }

    @Test(expected = WrongChoiceException.class)
    public void wrongChoiceTest() throws WrongChoiceException, BuildErrorException, BoxAlreadyOccupiedException, MoveErrorException, GodConditionNotSatisfiedException {
        Choice c=new MoveChoice(1,1);
        stateUnderTest1.update(c,model);
    }
    @Test(expected=BuildErrorException.class)
    public void workerNotBelongingTest() throws BuildErrorException, BoxAlreadyOccupiedException, WrongChoiceException, MoveErrorException, GodConditionNotSatisfiedException {
        Choice c=new SelectWorkerCellChoice(0,0);
        stateUnderTest1.update(c,model);
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void SelectWorkeroutOfBoundChoiceTest() throws IndexOutOfBoundsException, MoveErrorException, BuildErrorException, GodConditionNotSatisfiedException, WrongChoiceException, BoxAlreadyOccupiedException {
        Choice c=new SelectWorkerCellChoice(-1,-1);
        stateUnderTest1.update(c,model);
    }

    @Test (expected = BuildErrorException.class)
    public void notBelongingWorkerTest() throws BuildErrorException, MoveErrorException, BoxAlreadyOccupiedException, GodConditionNotSatisfiedException, WrongChoiceException {
        Choice c =new SelectWorkerCellChoice(4,0);
        stateUnderTest1.update(c,model);
    }

    @Test (expected=IndexOutOfBoundsException.class)
    public void buildOutOfBoundChoiceTest() throws IndexOutOfBoundsException, MoveErrorException, BuildErrorException, GodConditionNotSatisfiedException, WrongChoiceException, BoxAlreadyOccupiedException {
        Choice c=new BuildChoice(-1,-1);
        stateUnderTest1.update(c,model);
    }


    @Test
    public void updateCorrectBuildChoiceHephaestusCondition()
    {

        stateUnderTest2=new BuildState(
                new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition())),
                new ArrayList<>(model.getTurn().getPossibleBuildLocations(model.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition())),
                true,true,model,true);
        Choice c=new SelectWorkerCellChoice(4,1);
        try {
            stateUnderTest2.update(c,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        Choice c1=new BuildChoice(3,0);
        try {
            stateUnderTest2.update(c1,model);
        } catch (WrongChoiceException | MoveErrorException | BuildErrorException | BoxAlreadyOccupiedException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        assertTrue(stateUnderTest2.hasFinished());

    }




}