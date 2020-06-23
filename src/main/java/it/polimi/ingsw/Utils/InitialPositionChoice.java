package it.polimi.ingsw.Utils;

/**
 * This class is used to select
 * the initial position of a worker
 */

public class InitialPositionChoice extends Choice {
    public final int x;
    public final int y;

    /**
     * the builder sets the initial position and the choice type
     * @param x
     * @param y
     */

    public InitialPositionChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "InitialPositionChoice";
    }
}
