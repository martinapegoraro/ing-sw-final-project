package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

import java.util.ArrayList;

public class MoveState implements State {
    private int stateID;
    private ArrayList<Box> possibleMovesList;
    private boolean swapWorkerPosition;
    private boolean pushWorkerBack;

    public MoveState(ArrayList<Box> possibleMoves, boolean pushWorker, boolean swapWorker)
    {
        possibleMovesList = possibleMoves;
        pushWorkerBack = pushWorker;
        swapWorkerPosition = swapWorker;
    }

    public int getID()
    {
        return stateID;
    }

    public void update(Choice userChoice, Model model)
    {

    }
}
