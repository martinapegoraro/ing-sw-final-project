package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Utils.Choice;

/**
 * This class represents the ping choice,
 * sent at regular time intervals to check
 * the availability of the clients
 */

public class PingChoice extends Choice {

    /**
     * sets the choice type
     */

    public PingChoice()
    {
        this.type="ping";
    }


}
