package it.polimi.ingsw.View;

import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.*;

/**
 * this enumerations contains all the possible states that the view can be in during the game
 * there is a correspondence between the viewStates and the State Classes in the Model
 */
public enum ViewState {

    SETUPSTATE(new String[]{"SelectWorkerCellChoice"}),
    BEGINTURNSTATE(new String[]{}),
    ACTIVATIONGODSTATE(new String[]{"GodActivationChoice"}),
    MOVESTATE(new String[]{"SelectWorkerCellChoice","MoveChoice"}),
    CHECKWINCONDITIONSTATE(new String[]{}),
    BUILDSTATE(new String[]{"SelectWorkerCellChoice","BuildChoice"}),
    ENDTURNSTATE(new String[]{});


    /**
     * the array will contain all the possible choices that the state in the controller will accept
     * whether the state doesn't require choices the array it will be empty
     */
    private final String[] possibleChoices;

    ViewState(String[] possibleChoices) {
        this.possibleChoices=possibleChoices;
    }


    /**
     *this static method requires a ViewState @param state
     * @return a list of strings containing the possibleChoices elements
     */
    public static List<String> getPossibleChoices(ViewState state)
    {
        return Arrays.asList(state.possibleChoices);
    }


}
