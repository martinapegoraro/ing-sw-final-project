package it.polimi.ingsw.View;

import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.*;

/**
 * this enumerations contains all the possible states that the view can be in during the game
 * there is a correspondence between the viewStates and the State Classes in the Model
 */
public enum ViewState {

    SetUpState(new String[]{"SelectWorkerCellChoice"}),
    BeginTurnState(new String[]{}),
    ActivationGodState(new String[]{"GodActivationChoice"}),
    MoveState(new String[]{"SelectWorkerCellChoice","MoveChoice"}),
    CheckWinConditionState(new String[]{}),
    BuildState(new String[]{"SelectWorkerCellChoice","BuildChoice"}),
    EndTurnState(new String[]{}),
    LobbyState(new String[]{}),
    SleepState(new String[]{});


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

    public static ViewState getState(String state)
    {
        switch (state){
            case "SetUp":
                return ViewState.SetUpState;
            case "BeginTurn":
                return ViewState.BeginTurnState;
            case "ActivationGod":
                return ViewState.ActivationGodState;
            case "Move":
                return ViewState.MoveState;
            case "FirstCheckWinCondition":
            case "SecondCheckWinCondition":
                return ViewState.CheckWinConditionState;
            case "Build":
                return ViewState.BuildState;
            case "EndTurn":
                return ViewState.EndTurnState;
            case "SleepState":
                return ViewState.SleepState;
            case "LobbyState":
                return ViewState.LobbyState;
            default:
                return null;
        }
    }

}
