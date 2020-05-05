package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class BeginTurnState implements State {
    private StateEnum stateID;
    private boolean hasFinished;

    public BeginTurnState()
    {

        stateID = StateEnum.BeginTurn;
        hasFinished = false;
    }

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

    @Override
    public void startup(Model model)
    {
        model.getTurn().setNextPlayer();
        //Athena condition is resetted at the start of a players turn
        model.getTurn().getCurrentPlayer().changeAthenaCondition(false);

        //setNextPlayer should already skip players who have lost
        if(model.getTurn().getCurrentPlayer().getHasLost())
        {
            model.getTurn().setNextPlayer();
        }
        else
            {
                model.updateModelRep();
                hasFinished = true;
            }


    }

    @Override
    public void update(Choice userChoice, Model model)
    {

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
