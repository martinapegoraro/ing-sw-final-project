package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class BeginTurnState implements State {
    private int stateID;

    public BeginTurnState()
    {
        stateID = 1;
    }

    public int getID()
    {
        return stateID;
    }

    public void update(Choice userChoice, Model model)
    {

    }
}
