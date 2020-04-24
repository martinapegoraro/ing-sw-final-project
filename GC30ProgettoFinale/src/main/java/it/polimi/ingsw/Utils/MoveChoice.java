package it.polimi.ingsw.Utils;

public class MoveChoice extends Choice {
    public final int x;
    public final int y;
    public int playerId;

    public MoveChoice(int x, int y, int id)
    {
        this.x = x;
        this.y = y;
        this.playerId = id;
    }
}
