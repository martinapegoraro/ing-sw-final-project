package it.polimi.ingsw.Utils;

public class BuildChoice extends Choice {

    public final int x;
    public final int y;
    public int playerId;
    public BuildChoice(int x, int y, int id)
    {
        this.x = x;
        this.y = y;
        playerId = id;
    }


}
