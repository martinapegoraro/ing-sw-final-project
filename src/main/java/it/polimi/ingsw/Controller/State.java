package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

/**
 * this interface contains the general information and methods used by a State
 */

public interface State {

    /**
     *     Integer used to identify the state in the Context, to avoid using instanceof
     */

    StateEnum getID();

    /**
     *     Method called after initialization to adjust model
     */

    void startup(Model model);

    /**
     *     Called at every update of Context
     */

    void update(Choice userChoice, Model model) throws WrongChoiceException,
            MoveErrorException, BuildErrorException, BoxAlreadyOccupiedException,
            GodConditionNotSatisfiedException;

    /**
     *     Method which tells if the state has completed its operations
     */

    boolean hasFinished();

}
