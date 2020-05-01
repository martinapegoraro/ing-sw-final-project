package it.polimi.ingsw.Model;

import it.polimi.ingsw.View.Observable;

import java.util.List;

public class Model extends Observable<ModelRepresentation> {
    private Turn turn;
    private int playerNum;
    private ModelRepresentation modelRep;
    public Model(List<String> playersNamesList)
    {
        turn=new Turn(playersNamesList);
        playerNum=playersNamesList.size();
        //I create a new modelRep based on the current status of the model
        updateModelRep();
    }
    public Turn getTurn()
    {
        return turn;
    }

    public  ModelRepresentation getModelRep()
    {
        return modelRep;
    }

    public void updateModelRep()
    {
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList());
        notify(modelRep);
    }

    @Override
    public void notify(ModelRepresentation message) {
        super.notify(message);
    }
}
