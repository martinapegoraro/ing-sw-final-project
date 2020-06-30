package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.GodsList;

import java.util.List;

/**
 * This class is used to choose the user
 * who will be the first to play
 */

public class InitialPlayerChoice extends Choice {
   int initialPlayerId;

    /**
     * the builder sets the choice type
     * and the chosen player
     * @param initialPlayerId is the number of the first player
     */

    public InitialPlayerChoice(int initialPlayerId) {
        this.initialPlayerId = initialPlayerId;
        this.type="InitialPlayerChoice";
    }


    /**
     * returns the initial player
     * @return return the id of the first player
     */

    public int getChoice()
    {
        return initialPlayerId;
    }
}

