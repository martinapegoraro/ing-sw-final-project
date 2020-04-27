package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetUpState implements State{
    private int numberOfPlayers;
    private int stateID;
    private boolean hasFinished;

    public SetUpState(int n, Model model)
    {
        stateID = 0;
        numberOfPlayers = n;
        hasFinished = false;
        startup(model);
    }

    @Override
    public int getID()
    {
        return stateID;
    }

    @Override
    //Links players with gods
    public void startup(Model model) {
        ArrayList<Player> playerList = (ArrayList<Player>)model.getTurn().getPlayersList();
        ArrayList<GodsList> godsList = (ArrayList<GodsList>)extractListOfGods();
        for(int n = 0; n < playerList.size(); n++)
        {
            playerList.get(n).setGodCard(godsList.get(n));
        }
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }


    //ENSURES: A random list of Gods is returned
    private List<GodsList> extractListOfGods()
    {
        ArrayList<GodsList> randomGodArray = new ArrayList<>(Arrays.asList(GodsList.values()));
        Collections.shuffle(randomGodArray);
        return randomGodArray;
    }

    @Override
    public void update(Choice userChoice, Model model)
    {

    }
}
