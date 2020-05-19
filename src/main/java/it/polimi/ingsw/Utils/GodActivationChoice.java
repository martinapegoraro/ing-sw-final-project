package it.polimi.ingsw.Utils;

public class GodActivationChoice extends Choice {

    public final boolean godActive;

    public GodActivationChoice(boolean active)
    {
        godActive = active;
        this.type = "GodActivationChoice";
    }

}
