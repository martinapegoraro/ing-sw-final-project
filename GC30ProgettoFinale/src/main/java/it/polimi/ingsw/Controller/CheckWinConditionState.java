package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class CheckWinConditionState implements State{
    private boolean hasFinished;

    public CheckWinConditionState()
    {
        hasFinished = false;
    }
    @Override
    public int getID() {
        return 0;
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
