package it.polimi.ingsw.Model;

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
        modelRep = new ModelRepresentation(turn.getBoardInstance(), turn.getPlayersList(), selectedCells);
        notify(new MessageToVirtualView(modelRep));
    }

    @Override
    public void notify(MessageToVirtualView message) {
        super.notify(message);
    }
}