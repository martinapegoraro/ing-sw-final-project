package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public interface State {

    int getID();
    void update(Choice userChoice, Model model);

}
