package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.BuildErrorException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceTypeException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public interface State {

    //Integer used to identify the state in the Context, to avoid using instanceof
    StateEnum getID();

    //Method called after initialization to adjust model
    void startup(Model model);

    //Called at every update of Context
    void update(Choice userChoice, Model model) throws WrongChoiceTypeException,
            MoveErrorException, BuildErrorException, BoxAlreadyOccupiedException;

    //Method which tells if the state has completed it's operations
    boolean hasFinished();

}
