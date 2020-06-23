package it.polimi.ingsw.Utils;

/**
 * This class is used to activate the chosen god
 */

public class GodActivationChoice extends Choice {

    public final boolean godActive;

    /**
     * sets the choice type and sets the god to active
     * @param active
     */

    public GodActivationChoice(boolean active)
    {
        godActive = active;
        this.type = "GodActivationChoice";
    }

}
