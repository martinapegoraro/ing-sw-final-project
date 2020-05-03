package it.polimi.ingsw.Utils;

public abstract class Choice {
    private int playerId;
    public String type;

    public int getId()
    {
        return playerId;
    }

    public void setId(int id)
    {
        playerId = id;
    }

    @Override

    public String toString()
    {
        return type;
    }

}
