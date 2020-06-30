package it.polimi.ingsw.Utils;

/**
 * This class is used to activate the chosen god
 */

public class GodActivationChoice extends Choice {

    public final boolean godActive;

    /**
     * sets the choice type and sets the god to active
     * @param active if active is true the god will be activated,if possible, by the server otherwise it wil be
     *               left false or set false
     */

    public GodActivationChoice(boolean active)
    {
        godActive = active;
        this.type = "GodActivationChoice";
    }

}
