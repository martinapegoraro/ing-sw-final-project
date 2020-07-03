package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckWinConditionStateTest {
    Model model;
    Board board;
    CheckWinConditionState checkWinConditionStateTest1;
    CheckWinConditionState checkWinConditionStateTest2;

   @After
    public void clean()
    {
        model=null;
        board = null;
        checkWinConditionStateTest1=null;
        checkWinConditionStateTest2=null;
    }
    @Before
    public void initializeState ()
    {
        board = Board.getInstance();
        Board.newBoard();
        List<String> listaNomi=new ArrayList<String>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add ("paperino");
        model=new Model(listaNomi);
        checkWinConditionStateTest1 = new CheckWinConditionState(1, model);
        checkWinConditionStateTest2 = new CheckWinConditionState(2, model);
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(3,0).build();
        board.getBox(3,0).build();
        board.getBox(3,0).build();
        board.getBox(3,0).build();
        board.getBox(0,1).build();
        board.getBox(0,1).build();
        board.getBox(0,1).build();
        board.getBox(0,1).build();
        board.getBox(2,3).build();
        board.getBox(2,3).build();
        board.getBox(2,3).build();
        board.getBox(2,3).build();
        board.getBox(2,3).build();
        board.getBox(4,2).build();
        board.getBox(4,2).build();
        board.getBox(4,2).build();

    }
    @Test
    public void getID()
    {
        assertEquals(StateEnum.FirstCheckWinCondition, checkWinConditionStateTest1.getID());
        assertEquals(StateEnum.SecondCheckWinCondition, checkWinConditionStateTest2.getID());

    }


    @Test
    public void hasFinished()
    {
        assertTrue(checkWinConditionStateTest1.hasFinished());
        assertTrue(checkWinConditionStateTest2.hasFinished());
    }


    @Test
    public void checkPanConditionTest ()
    {
        model.getTurn().getCurrentPlayer().setGodCard(GodsList.PAN);
        model.getTurn().getCurrentPlayer().changePanCondition(true);
        checkWinConditionStateTest1.checkPanCondition(model);
        assertTrue(model.getTurn().getCurrentPlayer().isPanConditionTrue());
        assertTrue(model.getTurn().getCurrentPlayer().getHasWon());

    }

    @Test
    public  void checkChronusConditionTestPositive ()
    {
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.CHRONUS);
        board.getBox(4,2).build();
        checkWinConditionStateTest2.checkChronusCondition(model, board);
        assertTrue(model.getTurn().getPlayersList().get(1).getHasWon());
    }

    @Test
    public  void checkChronusConditionTestNegative ()
    {
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.CHRONUS);
        checkWinConditionStateTest2.checkChronusCondition(model, board);
        assertFalse(model.getTurn().getPlayersList().get(1).getHasWon());
    }
}