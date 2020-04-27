package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class BeginTurnState implements State {
    private int stateID;
    private boolean hasFinished;

    public BeginTurnState()
    {
        stateID = 1;
    }

    @Override
    public int getID()
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
