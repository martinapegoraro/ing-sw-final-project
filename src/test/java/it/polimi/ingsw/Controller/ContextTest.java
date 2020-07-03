package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Utils.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ContextTest {


    Model model;
    Context contextUnderTest;


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
        model.getTurn().getPlayersList().get(0).setWorkersPosition(b1,b2);
        Box b3=model.getTurn().getBoardInstance().getBox(2,3);
        Box b4=model.getTurn().getBoardInstance().getBox(4,0);
        model.getTurn().getPlayersList().get(1).setWorkersPosition(b3,b4);
        model.updateModelRep(StateEnum.BeginTurn);
    }

    @Before
    public void setUp() {
        contextUnderTest=new Context(model);
    }

    @Test(expected = NullPointerException.class)
    public void updateWithNullChoice() throws NullPointerException
    {
        contextUnderTest.update(null);
    }


    @Test
    public void basicTurnFlowTest()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.APOLLO);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ARTEMIS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(false);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);
        //I'm in the move state
        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(2,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        assertEquals(1,model.getTurn().getBoardInstance().getBox(2,1).getTower().getHeight());
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
    }

    @Test
    public void prometheusTurnFlow()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.PROMETHEUS);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ARTEMIS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(true);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(1,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(2,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        assertEquals(1,model.getTurn().getBoardInstance().getBox(2,1).getTower().getHeight());
        assertEquals(1,model.getTurn().getBoardInstance().getBox(1,2).getTower().getHeight());
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[1]);
    }

    @Test
    public void prometheusTurnFlowwithMoveErrorException()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.PROMETHEUS);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ARTEMIS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(true);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(1,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(1,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);


    }


    @Test
    public void ArtemisTurnFlow()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.ARTEMIS);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ATLAS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(true);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        assertEquals(1,model.getTurn().getBoardInstance().getBox(2,2).getTower().getHeight());
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
        assertEquals(1,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[1]);
    }

    @Test
    public void demeterTurnFlow()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.DEMETER);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ATLAS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(true);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(3,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(3,3);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        assertEquals(1,model.getTurn().getBoardInstance().getBox(3,3).getTower().getHeight());
        assertEquals(1,model.getTurn().getBoardInstance().getBox(3,1).getTower().getHeight());
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[1]);

    }

    @Test
    public void hestiaTurnFlowTest()
    {
        Choice c;

        model.getTurn().getPlayersList().get(0).setGodCard(GodsList.HESTIA);
        model.getTurn().getPlayersList().get(1).setGodCard(GodsList.ATLAS);
        contextUnderTest.switchState(new BeginTurnState(model));
        contextUnderTest.switchState(new ActivationGodState(model));
        Player actingPlayer = model.getTurn().getCurrentPlayer();
        //I'm in the activation God State
        c = new GodActivationChoice(true);
        c.setId(0);
        contextUnderTest.update(c);
        c = new GodActivationChoice(false);
        c.setId(1);
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(1,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new MoveChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(3,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new SelectWorkerCellChoice(2,2);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        c = new BuildChoice(3,1);
        c.setId(actingPlayer.getNumber());
        contextUnderTest.update(c);

        assertEquals(2,model.getTurn().getBoardInstance().getBox(3,1).getTower().getHeight());
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[0]);
        assertEquals(2,model.getTurn().getPlayersList().get(0).getWorkerList().get(0).getPosition().getCoord()[1]);
    }



}