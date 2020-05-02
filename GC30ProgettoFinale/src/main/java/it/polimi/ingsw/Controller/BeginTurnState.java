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
    public void startup(Model model) {

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
