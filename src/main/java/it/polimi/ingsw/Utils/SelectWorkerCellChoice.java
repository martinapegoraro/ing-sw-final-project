package it.polimi.ingsw.Utils;

public class SelectWorkerCellChoice extends Choice {
    public final int x;
    public final int y;

    public SelectWorkerCellChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "SelectWorkerCellChoice";
    }
}
