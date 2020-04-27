package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class ActivationGodState implements State {
    int stateID;
    private boolean hasFinished;

    public ActivationGodState()
    {
        stateID = 2;
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
