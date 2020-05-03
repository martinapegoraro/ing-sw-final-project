package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class CheckWinConditionState implements State{
    private boolean hasFinished;
    StateEnum stateId;

    public CheckWinConditionState()
    {
        stateId = StateEnum.CheckWinCondition;
        hasFinished = false;
    }

    @Override
    public StateEnum getID() {
        return stateId;
    }

    @Override
    public void startup(Model model) {

    }

    @Override
    public void update(Choice userChoice, Model model) {

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
