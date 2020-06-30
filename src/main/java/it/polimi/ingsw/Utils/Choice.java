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
     * @return the id of the player who has sent the choice
     */

    public int getId()
    {
        return playerId;
    }

    /**
     * this method is used to set the ID
     * of the user who sent the choice
     * @param id of the player who sends the choice
     */

    public void setId(int id)
    {
        playerId = id;
    }

    /**
     * @return the type of the choice
     */

    @Override

    public String toString()
    {
        return type;
    }

}
