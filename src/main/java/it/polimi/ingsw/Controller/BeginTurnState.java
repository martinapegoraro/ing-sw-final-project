package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

/**
 * the BeginTurnState class is built every time the turn is changed
 */

public class BeginTurnState implements State {
    private StateEnum stateID;
    private boolean hasFinished;

    /**
     * the constructor is called to initialize the state,
     * it skips the player if it has lost
     */

    public BeginTurnState(Model model)
    {
        stateID = StateEnum.BeginTurn;
        hasFinished = false;
        startup(model);
        if(model.getTurn().getCurrentPlayer().getHasLost())
        {
            hasFinished=true;
        }
    }

    /**
     * @return the ID of the state
     */

    @Override
    public StateEnum getID()
    {
        return stateID;
    }


    /**
     * sets the next player skipping the player who has lost (game with 3 players),
     * resets the Athena condition and updates the ModelRepresentation
     * with the current state
     */

    @Override
    public void startup(Model model)
    {

        //Athena condition is resetted at the start of a players turn
        model.getTurn().getCurrentPlayer().changeAthenaCondition(false);

        //setNextPlayer should already skip players who have lost
        if(model.getTurn().getCurrentPlayer().getHasLost())
        {
            model.getTurn().setNextPlayer();
        }
        model.updateModelRep(stateID);
        hasFinished = true;


    }

    /**
     * prints an error message if a choice is received during this state,
     * the method is overridden from the State interface and performs no action
     */

    @Override
    public void update(Choice userChoice, Model model)
    {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in BeginTurnState! RECEIVED: " + userChoice.toString());
    }

    /**
     * @return the boolean variable that is used to check if the state has finished
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
