package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BuildState implements State{
    private boolean hasFinished;
    ArrayList<Box> possibleBuildList;
    boolean domeAtAnyLevel;

    public BuildState(ArrayList<Box> pBL, boolean domeAAL)
    {
        domeAtAnyLevel = domeAAL;
        possibleBuildList = new ArrayList<>(pBL);
    }
    @Override
    public StateEnum getID() {
        return StateEnum.Build;
    }

    @Override
    public void startup(Model model) {

    }

    @Override
    public void update(Choice userChoice, Model model) {

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
