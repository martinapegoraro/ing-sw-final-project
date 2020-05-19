package it.polimi.ingsw.Utils;

import java.io.Serializable;

public abstract class Choice implements Serializable {
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
