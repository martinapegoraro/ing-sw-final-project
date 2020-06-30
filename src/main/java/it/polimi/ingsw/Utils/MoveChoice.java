package it.polimi.ingsw.Utils;

/**
 * This class is used to collect the user's choice on where to move
 */

public class MoveChoice extends Choice {
    public final int x;
    public final int y;

    /**
     * the builder sets the coordinates of
     * the box the player has chosen
     * @param x is the row coordinate if the field
     * @param y is the column in the field
     */

    public MoveChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "MoveChoice";
    }
}
