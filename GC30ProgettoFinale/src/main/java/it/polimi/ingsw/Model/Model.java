package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.Observable;

import java.util.List;

public class Model extends Observable {
    private Turn turn;
    private int playerNum;
    private ModelRepresentation modelRep;
    public Model(List<String> playersNamesList)
    {
        turn=new Turn(playersNamesList);
        playerNum=playersNamesList.size();
        modelRep=createModelRep();
    }
    public Turn getTurn()
    {
        return turn;
    }

    public ModelRepresentation createModelRep()
    {
        return new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList());
    }
}
