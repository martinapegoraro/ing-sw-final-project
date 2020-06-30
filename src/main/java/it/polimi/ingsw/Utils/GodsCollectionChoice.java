package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.GodsList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to collect the choice of the gods
 * that will be used in the game, made by the first
 * player
 */

public class GodsCollectionChoice extends Choice {
    private ArrayList<GodsList> godsList;

    /**
     * th builder sets the list of gods and the choice type
     * @param godList is the list of the gods selected by the player 0
     */

    public GodsCollectionChoice(ArrayList<GodsList> godList)
    {
        this.godsList=godList;
        this.type="GodsCollectionChoice";
    }

    /**
     * returns the list of gods
     * @return the list of chosen gods
     */

    public ArrayList<GodsList> getGodList()
    {
        return godsList;
    }
}
