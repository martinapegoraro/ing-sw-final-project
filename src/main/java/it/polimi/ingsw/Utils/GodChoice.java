package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;
import it.polimi.ingsw.Model.GodsList;

public class GodChoice extends Choice {
    GodsList god;

    public GodChoice(String godName)
    {
        try {
            god=GodsList.getGod(godName);
        } catch (NotExistingGodException e) {
            e.printStackTrace();
        }
        this.type="GodChoice";
    }

    public GodsList getGod()
    {
        return god;
    }
}
