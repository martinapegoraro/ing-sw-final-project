package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

public class BeginTurnState implements State {
    private StateEnum stateID;
    private boolean hasFinished;

    public BeginTurnState(Model model)
    {
        stateID = StateEnum.BeginTurn;
        hasFinished = false;
        startup(model);
    }

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

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

    @Override
    public void update(Choice userChoice, Model model)
    {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in BeginTurnState! RECEIVED: " + userChoice.toString());
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
