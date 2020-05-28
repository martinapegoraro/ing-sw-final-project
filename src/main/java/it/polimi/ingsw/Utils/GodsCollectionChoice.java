package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.GodsList;

import java.util.List;

public class GodsCollectionChoice extends Choice {
    List<GodsList> godsList;
    public GodsCollectionChoice(List<GodsList> godList)
    {
        this.godsList=godList;
    }
}
