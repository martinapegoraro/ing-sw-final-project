package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SetUpState implements State{
    private int numberOfPlayers;
    private int stateID;

    public SetUpState(int n)
    {
        stateID = 0;
        numberOfPlayers = n;
    }

    public int getID()
    {
        return stateID;
    }

    //ENSURES: A random list of Gods is returned

    private ArrayList<GodsList> extractListOfGods()
    {
        ArrayList<GodsList> randomGodArray = new ArrayList<>(Arrays.asList(GodsList.values()));
        Collections.shuffle(randomGodArray);
        return randomGodArray;
    }

    public void update(Choice userChoice, Model model)
    {

    }
}
