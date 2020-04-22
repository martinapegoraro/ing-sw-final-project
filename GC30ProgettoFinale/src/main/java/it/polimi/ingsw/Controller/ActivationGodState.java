package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class ActivationGodState implements State {
    int stateID;

    public ActivationGodState()
    {
        stateID = 2;
    }

    public int getID()
    {
        return stateID;
    }

    public void update(Choice userChoice, Model model)
    {

    }
}
