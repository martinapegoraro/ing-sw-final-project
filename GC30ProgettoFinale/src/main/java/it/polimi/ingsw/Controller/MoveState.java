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
    private boolean hasFinished;

    public MoveState(ArrayList<Box> possibleMoves, boolean pushWorker, boolean swapWorker)
    {
        possibleMovesList = possibleMoves;
        pushWorkerBack = pushWorker;
        swapWorkerPosition = swapWorker;
        hasFinished = false;
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
    public boolean hasFinished() {
        return hasFinished;
    }

    @Override
    public void update(Choice userChoice, Model model)
    {

    }
}
