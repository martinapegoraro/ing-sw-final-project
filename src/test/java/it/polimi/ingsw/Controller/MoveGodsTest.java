package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Utils.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveGodsTest {
    private Model model;
    private Context context;

    /**
     * MODEL SETUP:
     * Player0: Pippo       Player1: Pluto
     * <p>
     *    0     1     2     3     4
     * |     |     |     |
     * 0  -  |  -  |  -  |  -  |  -
     * _____|_____|_____|_____|_____
     * |     |     |     |
     * 1  -  |  W0 |  -  |  -  |  -
     * _____|_____|_____|_____|_____
     * |     |     |     |
     * 2  -  |  -  |  -  |  W1 |  -
     * _____|_____|_____|_____|_____
     * |     |     |     |
     * 3  -  |  -  |  -  |  -  |  -
     * _____|_____|_____|_____|_____
     * |     |     |     |
     * 4  W1 |  W0 |  -  |  -  |  -
     * |     |     |     |
     **/
    @Before
    public void modelSetUp() {
        List<String> listaNomi = new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        model = new Model(listaNomi);
        Board.newBoard();
        Box b1 = model.getTurn().getBoardInstance().getBox(1, 1);
        Box b2 = model.getTurn().getBoardInstance().getBox(4, 1);
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1, b2);
        Box b3 = model.getTurn().getBoardInstance().getBox(2, 3);
        Box b4 = model.getTurn().getBoardInstance().getBox(4, 0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3, b4);
        model.updateModelRep(StateEnum.Move);
    }

    @Before
    public void contextSetUp()
    {
        context=new Context(model);
    }

    @Test
    public void minotaurTest() {

        //checks if the minotaur effect is properly applied
        //player 0 "pippo" can move his workers but applying the minotaur effect there is no difference
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.MINOTAUR);
        context.switchState(new EndTurnState(model));
        context.switchState(new BeginTurnState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        context.switchState(new ActivationGodState(model));
        Choice c = new GodActivationChoice(true);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new GodActivationChoice(false);
        c.setId(0);
        context.update(c);
        //player1 "pluto" should can move the worker in the (4;0)box into the (4;1) pushing back the player0 worker due the minotaur effect
        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new SelectWorkerCellChoice(4, 0);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new MoveChoice(4, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        assertEquals(4,model.getTurn().getPlayersList().get(1).getWorkerList().get(1).getPosition().getCoord()[0]);
        assertEquals(1,model.getTurn().getPlayersList().get(1).getWorkerList().get(1).getPosition().getCoord()[1]);
        assertEquals(4,model.getTurn().getPlayersList().get(0).getWorkerList().get(1).getPosition().getCoord()[0]);
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(1).getPosition().getCoord()[1]);

    }

    @Test
    public void athenaTest() {

        model.getTurn().getBoardInstance().getBox(2, 1).build();
        model.getTurn().getBoardInstance().getBox(3, 3).build();
        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.ATHENA);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.MINOTAUR);
        context.switchState(new BeginTurnState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        context.switchState(new ActivationGodState(model));
        Choice c = new GodActivationChoice(false);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        context.update(c);
        //player1 "pluto" should can move the worker in the (4;0)box into the (4;1) pushing back the player0 worker due the minotaur effect
        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new SelectWorkerCellChoice(1, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new MoveChoice(2, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        c = new SelectWorkerCellChoice(2, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new BuildChoice(2, 2);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new GodActivationChoice(false);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new GodActivationChoice(true);
        c.setId(0);
        context.update(c);

        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new SelectWorkerCellChoice(2, 3);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        //i have 2 towers of 1 level in the (2,2) and (3,3) boxes so the player 1 worker
        //in the (2,3)position should have 6 possible moves but not in the (2,2) and (3,3)

        assertEquals(1,model.getModelRep().activeCells[1][2]);
        assertEquals(1,model.getModelRep().activeCells[1][3]);
        assertEquals(1,model.getModelRep().activeCells[1][4]);
        assertEquals(1,model.getModelRep().activeCells[2][4]);
        assertEquals(1,model.getModelRep().activeCells[3][2]);
        assertEquals(1,model.getModelRep().activeCells[3][4]);
        assertEquals(0,model.getModelRep().activeCells[2][2]);
        assertEquals(0,model.getModelRep().activeCells[3][3]);
    }

    @Test
    public void persephoneTest()
    {
        model.getTurn().getBoardInstance().getBox(1, 2).build();
        model.getTurn().getBoardInstance().getBox(2, 4).build();
        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.PERSEPHONE);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.MINOTAUR);
        context.switchState(new BeginTurnState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        context.switchState(new ActivationGodState(model));
        Choice c = new GodActivationChoice(false);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        context.update(c);
        //player1 "pluto" should can move the worker in the (4;0)box into the (4;1) pushing back the player0 worker due the minotaur effect
        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new SelectWorkerCellChoice(1, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new MoveChoice(2, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        c = new SelectWorkerCellChoice(2, 1);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new BuildChoice(2, 2);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new GodActivationChoice(false);
        c.setId(actingPlayer.getNumber());
        context.update(c);
        c = new GodActivationChoice(true);
        c.setId(0);
        context.update(c);

        actingPlayer = model.getTurn().getCurrentPlayer();
        c = new SelectWorkerCellChoice(2, 3);
        c.setId(actingPlayer.getNumber());
        context.update(c);

        assertEquals(1,model.getModelRep().activeCells[1][2]);
        assertEquals(0,model.getModelRep().activeCells[1][3]);
        assertEquals(0,model.getModelRep().activeCells[1][4]);
        assertEquals(1,model.getModelRep().activeCells[2][2]);
        assertEquals(1,model.getModelRep().activeCells[2][4]);
        assertEquals(0,model.getModelRep().activeCells[3][2]);
        assertEquals(0,model.getModelRep().activeCells[3][3]);
        assertEquals(0,model.getModelRep().activeCells[3][4]);
    }

    @Test
    public void artemisTest()
    {
        Choice c;
        //Testing Artemis possible moves
        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.ARTEMIS);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.APOLLO);
        context.switchState(new BeginTurnState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        context.switchState(new ActivationGodState(model));

        //Activation
        c = new GodActivationChoice(true);
        c.setId(0);
        context.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        context.update(c);

        //Now P0 moves
        c = new SelectWorkerCellChoice(1, 1);
        c.setId(0);
        context.update(c);
        c = new MoveChoice(2, 1);
        c.setId(0);
        context.update(c);

        //Artemis is active, P0 moves again
        c= new SelectWorkerCellChoice(2,1);
        c.setId(0);
        context.update(c);

        c= new MoveChoice(1,1);
        c.setId(0);
        context.update(c); //This instruction should not be accepted by the MoveState

        assertFalse(model.getTurn().getBoardInstance().getBox(1,1).isOccupied());

        c= new MoveChoice(2,0);
        c.setId(0);
        context.update(c);
        assertTrue(model.getTurn().getBoardInstance().getBox(2,0).isOccupied());
    }
}
