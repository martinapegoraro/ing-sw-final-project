package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Worker;
import it.polimi.ingsw.Utils.Choice;

import java.util.ArrayList;

public class setUpState {
    private final int numberPlayer;

    public setUpState(int numberOfPlayers)
    {
        numberPlayer = numberOfPlayers;
    }

    private ArrayList<GodsList> extractListOfGods()
    {
        //Random selection of god cards
        return null;
    }


//REQUIRES w NOT NULL
    private void putWorkerInPlace(Box casella, Worker w)
    {
        w.move(casella);
    }

    public void update(Choice userChoice, Model model)
    {
        //TODO: How do I check Choice is of type MoveChoice?
    }

}
