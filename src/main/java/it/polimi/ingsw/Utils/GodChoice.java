package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;
import it.polimi.ingsw.Model.GodsList;

/**
 * This class is used to select
 * the chosen god between the ones
 * selected by the first player
 */

public class GodChoice extends Choice {
    GodsList god;

    /**
     * the builder sets the choice type and
     * selects the chosen god
     * @param godName
     */

    public GodChoice(String godName)
    {
        try {
            god=GodsList.getGod(godName);
        } catch (NotExistingGodException e) {
            e.printStackTrace();
        }
        this.type="GodChoice";
    }

    /**
     * returns the god
     * @return
     */

    public GodsList getGod()
    {
        return god;
    }
}
