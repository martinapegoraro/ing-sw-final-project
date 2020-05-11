package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
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
    @Before
    public void initializeState ()
    {
        board = Board.getInstance();
        List<String> listaNomi=new ArrayList<String>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add ("paperino");
        model=new Model(listaNomi);
        checkWinConditionStateTest1 = new CheckWinConditionState(1);
        checkWinConditionStateTest2 = new CheckWinConditionState(2);
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
        assertFalse(checkWinConditionStateTest1.hasFinished());
        assertFalse(checkWinConditionStateTest2.hasFinished());
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
    public  void checkChronusConditionTest ()
    {
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.CHRONUS);
        checkWinConditionStateTest2.checkChronusCondition(model, board);
        assertTrue(model.getTurn().getPlayersList().get(1).getHasWon());
    }
}