package it.polimi.ingsw.Utils;

public class InitialPositionChoice extends Choice {
    public final int x;
    public final int y;

    public InitialPositionChoice(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.type = "InitialPositionChoice";
    }
}
