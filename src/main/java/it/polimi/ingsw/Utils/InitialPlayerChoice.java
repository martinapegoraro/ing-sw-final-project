package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.GodsList;

import java.util.List;

public class InitialPlayerChoice extends Choice {
   int initialPlayerId;

    public InitialPlayerChoice(int initialPlayerId) {
        this.initialPlayerId = initialPlayerId;
        this.type="InitialPlayerChoice";
    }

    public int getChoice()
    {
        return initialPlayerId;
    }
}

