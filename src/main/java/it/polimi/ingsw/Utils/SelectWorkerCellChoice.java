package it.polimi.ingsw.Utils;

/**
 * This class is used to select the worker
 * the player wants to move and build with in
 * the current turn
 */

public class SelectWorkerCellChoice extends Choice {
    public final int x;
    public final int y;

    /**
     * the builder sets the coordinates of the cell
     * where teh selected worker is and the choice type
     * @param x
     * @param y
     */

    public SelectWorkerCellChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "SelectWorkerCellChoice";
    }
}
