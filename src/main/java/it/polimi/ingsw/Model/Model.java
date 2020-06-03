package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.StateEnum;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MessageToVirtualView> {
    private Turn turn;
    private int playerNum;
    private ModelRepresentation modelRep;
    public Model(List<String> playersNamesList)
    {

        turn=new Turn(playersNamesList);
        playerNum=playersNamesList.size();
        //I create a new modelRep based on the current status of the model
        //updateModelRep(modelRep.currentState);
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
        //I initialize mi matrix with just zeroes (java default value for int)
        int [][] selectedCells = new int[5][5];
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList(),selectedCells);
        //modelRep.currentState = null; This is not necessary, moved in ModelRep constructor
        notify(new MessageToVirtualView(modelRep));
    }

    public void updateModelRep(StateEnum currentState)
    {
        //I initialize mi matrix with just zeroes (java default value for int)
        int [][] selectedCells = new int[5][5];
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList(),selectedCells);
        modelRep.currentState = currentState;
        notify(new MessageToVirtualView(modelRep));
    }

    public void updateModelRep(List<GodsList> godsList)
    {
        //I initialize mi matrix with just zeroes (java default value for int)
        int [][] selectedCells = new int[5][5];
        StateEnum oldState = modelRep.currentState;
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList(),selectedCells);
        modelRep.gods=new ArrayList<GodsList>();
        modelRep.gods.addAll(godsList);
        modelRep.currentState = oldState;
        notify(new MessageToVirtualView(modelRep));
    }

    public void updateModelRep(ArrayList<Box> selectedWorkerCells)
    {
        int[][] selectedCells = new int[5][5];
        for(Box b : selectedWorkerCells)
        {
            //Expects just two coordinates
            int[] coordinates = b.getCoord();
            selectedCells[coordinates[0]][coordinates[1]] = 1;
        }
        //This method should never be called in a state Change, it's safe to save the currentState
        StateEnum oldState = modelRep.currentState;
        modelRep = new ModelRepresentation(turn.getBoardInstance(), turn.getPlayersList(), selectedCells);
        modelRep.currentState = oldState;

        notify(new MessageToVirtualView(modelRep));
    }

    @Override
    public void notify(MessageToVirtualView message) {
        super.notify(message);
    }
}
