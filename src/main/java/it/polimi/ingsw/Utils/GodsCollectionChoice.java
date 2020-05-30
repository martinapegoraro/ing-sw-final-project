package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.GodsList;

import java.util.ArrayList;
import java.util.List;

public class GodsCollectionChoice extends Choice {
    private ArrayList<GodsList> godsList;
    public GodsCollectionChoice(ArrayList<GodsList> godList)
    {
        this.godsList=godList;
        this.type="GodsCollectionChoice";
    }

    public ArrayList<GodsList> getGodList()
    {
        return godsList;
    }
}
