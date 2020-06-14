package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.GodActivationChoice;
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

    /**MODEL SETUP:
     * Player0: Pippo       Player1: Pluto
     *
     *    0     1     2     3     4
     *       |     |     |     |
     * 0  -  |  -  |  -  |  -  |  -
     *  _____|_____|_____|_____|_____
     *       |     |     |     |
     * 1  -  |  W0 |  -  |  -  |  -
     *  _____|_____|_____|_____|_____
     *       |     |     |     |
     * 2  -  |  -  |  -  |  W1 |  -
     *  _____|_____|_____|_____|_____
     *       |     |     |     |
     * 3  -  |  -  |  -  |  -  |  -
     *  _____|_____|_____|_____|_____
     *       |     |     |     |
     * 4  W1 |  W0 |  -  |  -  |  -
     *       |     |     |     |
     *
     *       **/
    @Before
    public void modelSetUp()
    {
        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model=new Model(listaNomi);
        Board.newBoard();
        Box b1=model.getTurn().getBoardInstance().getBox(1,1);
        Box b2=model.getTurn().getBoardInstance().getBox(4,1);
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1,b2);
        Box b3=model.getTurn().getBoardInstance().getBox(2,3);
        Box b4=model.getTurn().getBoardInstance().getBox(4,0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        model.updateModelRep(StateEnum.Move);
    }

    @Before
    public void correctMoveStateSetUp()
    {
        //TODO: Test Gods special conditions are correctly applied
       stateUnderTest=new MoveState(
               (ArrayList<Box>)model.getTurn().getPossibleMoves( model.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition()),
               (ArrayList<Box>)model.getTurn().getPossibleMoves( model.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition()),
               false,false, false, model,true);
    }

    @Test
    public void emptyPossibleMovesListTest(){
        ArrayList<Box> listaVuota=new ArrayList<>();
        State s=new MoveState(listaVuota,listaVuota,false,false, false, model,true);
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

    @Test
    public void heraWinConditionTest()
    {
        //Moves a worker from 2nd level to 3rd (on board edge), but since Hera is active player shouldn't win

        Player actingPlayer = model.getTurn().getCurrentPlayer();
        State s = new MoveState(
                (ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(0).getPosition()),
                (ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(1).getPosition()),
                false,false, true, model,true);

        //Builds a two floor tower where the player's worker stands
        Box moveFromBox = model.getTurn().getBoardInstance().getBox(1,1);
        moveFromBox.build();
        moveFromBox.build();

        //Builds a three floor tower where the player's worker moves
        Box moveToBox = model.getTurn().getBoardInstance().getBox(1,0);
        moveToBox.build();
        moveToBox.build();
        moveToBox.build();


        Choice c = new SelectWorkerCellChoice(1,1);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        c = new MoveChoice(1,0);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        assertFalse(actingPlayer.getHasWon());
    }

    @Test
    public void normalWinConditionTest()
    {
        //Moves a worker from 2nd level to 3rd (on board edge), since Hera isn't active player should win

        Player actingPlayer = model.getTurn().getCurrentPlayer();
        State s = new MoveState(
                (ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(0).getPosition()),
                (ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(1).getPosition()),
                false,false, false, model,true);

        //Builds a two floor tower where the player's worker stands
        Box moveFromBox = model.getTurn().getBoardInstance().getBox(1,1);
        moveFromBox.build();
        moveFromBox.build();

        //Builds a three floor tower where the player's worker moves
        Box moveToBox = model.getTurn().getBoardInstance().getBox(1,0);
        moveToBox.build();
        moveToBox.build();
        moveToBox.build();


        Choice c = new SelectWorkerCellChoice(1,1);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }
        c = new MoveChoice(1,0);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        assertTrue(actingPlayer.getHasWon());
    }

    @Test

    public void apolloSwapTest()
    {
        //Test to check if Apollo correctly swaps two workers
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        ArrayList<Box> possibleMoveList1 = (ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(1).getPosition());

        //Adds Worker box to possibleMoveList, this is done because getPossibleMoves does not add worker boxes
        Box addedBox = model.getTurn().getBoardInstance().getBox(4,0);
        possibleMoveList1.add(addedBox);

        State s = new MoveState((ArrayList<Box>)model.getTurn().getPossibleMoves( actingPlayer.getWorkerList().get(0).getPosition())
                , possibleMoveList1,
                false,true, false, model,true);

        Choice c = new SelectWorkerCellChoice(4,1);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        //selectedWorker is turned to null after player move, so I have to retreive it before
        Worker workerToCheck = actingPlayer.getSelectedWorker();
        c = new MoveChoice(4,0);
        try {
            s.update(c, model);
        }
        catch (BuildErrorException | BoxAlreadyOccupiedException | WrongChoiceException |
                MoveErrorException | GodConditionNotSatisfiedException e) {
            e.printStackTrace();
        }

        //worker box after swap
        Box playerWorkerBox = model.getTurn().getBoardInstance().getBox(4,0);

        assertSame(workerToCheck.getPosition(), playerWorkerBox);

    }

    //TODO: Add Minotaur test, add Persephone test
}