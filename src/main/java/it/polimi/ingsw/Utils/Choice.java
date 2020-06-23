package it.polimi.ingsw.Utils;

import java.io.Serializable;

/**
 * This abstract class represents the choices, sent by the user
 * */

public abstract class Choice implements Serializable {
    private int playerId;
    public String type;

    /**
     * this method is used to get the ID
     *of the user who sent the choice
     * @return
     */

    public int getId()
    {
        return playerId;
    }

    /**
     * this method is used to set the ID
     * of the user who sent the choice
     * @param id
     */

    public void setId(int id)
    {
        playerId = id;
    }

    /**
     * returns the type of the choice
     * @return
     */

    @Override

    public String toString()
    {
        return type;
    }

}
