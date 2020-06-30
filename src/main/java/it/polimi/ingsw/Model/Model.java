package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.StateEnum;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * the model class contains the instance of the game
 * it's represents the game and it's modified by the controller
 * the model every time it's modified updates his modelRepresentation and sends it' to the View so it can be seen by the players
 */
public class Model extends Observable<MessageToVirtualView> {
    private Turn turn;
    private int playerNum;
    private ModelRepresentation modelRep;

    /**
     * given playersNamesList a new instance of the game is created
     * @param playersNamesList containing two or three player names
     */
    public Model(List<String> playersNamesList)
    {
        turn=new Turn(playersNamesList);
        playerNum=playersNamesList.size();
        //I create a new modelRep based on the current status of the model
        //updateModelRep(modelRep.currentState);
        updateModelRep();
    }

    /**
     * method that return the current turn
     */
    public Turn getTurn()
    {
        return turn;
    }

    /**
     * @return the current modelRepresentation
     */
    public  ModelRepresentation getModelRep()
    {
        return modelRep;
    }

    /**
     * this update modelRep is used when we have to show the current player
     * in which cells she or he can move or build
     */
    public void updateModelRep()
    {
        //I initialize my matrix with just zeroes (java default value for int)
        int [][] selectedCells = new int[5][5];
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList(),selectedCells);
        //modelRep.currentState = null; This is not necessary, moved in ModelRep constructor
        StateEnum oldState = modelRep.currentState;
        modelRep.currentState = oldState;
        notify(new MessageToVirtualView(modelRep));
    }

    /**
     * this update modelRep in addition to save in the modelRep the selected cells saves also the current state of the controller
     * in this way the client can know the state of the context (moveState or buildState)
     * @param currentState current State of the game server-side
     */
    public void updateModelRep(StateEnum currentState)
    {
        //I initialize mi matrix with just zeroes (java default value for int)
        int [][] selectedCells = new int[5][5];
        modelRep = new ModelRepresentation(turn.getBoardInstance(),turn.getPlayersList(),selectedCells);
        modelRep.currentState = currentState;
        notify(new MessageToVirtualView(modelRep));
    }

    /**
     * update modelRep used at the beginning of the game is used to show the second (and the third) player the
     * gods selected for the current game by the first player
     * @param godsList selected gods
     */
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

    /**
     * updates the cells where the worker can move
     * @param selectedWorkerCells the cells where the worker can move to
     */
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

    /**
     * this notify sends the messageToVirtualView which contains the modelRep to the VirtualView
     */
    @Override
    public void notify(MessageToVirtualView message) {
        super.notify(message);
    }
}
