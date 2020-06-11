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
     * the builder is called to initialize the state
     * @param model
     */

    public BeginTurnState(Model model)
    {
        stateID = StateEnum.BeginTurn;
        hasFinished = false;
        startup(model);
    }

    /**
     * returns the ID of the state
     * @return
     */

    @Override
    public StateEnum getID()
    {
        return stateID;
    }


    /**
     * sets the next player skipping the players who have lost,
     * resets the Athena condition and updates the ModelRepresentation
     * with the current state
     * @param model
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
        else
            {
                model.updateModelRep(stateID);

                hasFinished = true;
            }


    }

    /**
     * prints an error message if a choice is received during this state
     * @param userChoice
     * @param model
     */

    @Override
    public void update(Choice userChoice, Model model)
    {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in BeginTurnState! RECEIVED: " + userChoice.toString());
    }

    /**
     * returns the boolean variable that is used to check if the state has finished
     * @return
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
