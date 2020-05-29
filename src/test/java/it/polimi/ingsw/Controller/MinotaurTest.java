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

import static org.junit.Assert.assertEquals;

public class MinotaurTest {
    private Model model;
    private Context context;

    /**
     * MODEL SETUP:
     * Player0: Pippo       Player1: Pluto
     * <p>
     * 0     1     2     3     4
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
}
