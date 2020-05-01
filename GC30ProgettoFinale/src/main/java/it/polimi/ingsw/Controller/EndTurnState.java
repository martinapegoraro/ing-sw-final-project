package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class EndTurnState implements State {
    @Override
    public StateEnum getID() {
        return null;
    }

    @Override
    public void startup(Model model) {

    }

    @Override
    public void update(Choice userChoice, Model model) {

    }

    @Override
    public boolean hasFinished() {
        return false;
    }
}
