package it.polimi.ingsw.Utils;

/**
 * This class is used to collect the user's choice on where to build
 */

public class BuildChoice extends Choice {

    public final int x;
    public final int y;

    /**
     * the builder is used to set
     * the coordinates of the box
     * selected by the user
     * @param x
     * @param y
     */

    public BuildChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "BuildChoice";
    }


}
