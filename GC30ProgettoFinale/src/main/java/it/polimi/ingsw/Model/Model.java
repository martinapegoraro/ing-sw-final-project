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
    }
    public Turn getTurn()
    {
        return turn;
    }
    //Manca da gestire tutta la parte collegata Al model representation.
}
