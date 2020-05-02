package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceTypeException;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.MoveChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.ArrayList;

public class MoveState implements State {
    private StateEnum stateID;
    private ArrayList<Box> possibleMovesList;
    private boolean swapWorkerPosition;
    private boolean pushWorkerBack;
    private boolean hasFinished;

    public MoveState(ArrayList<Box> possibleMoves, boolean pushWorker, boolean swapWorker, Model model)
    {
        //If possibleMoves is empty the player has lost
        if(possibleMoves.isEmpty())
        {
            playerHasLost(model);
        }

        possibleMovesList = possibleMoves;
        pushWorkerBack = pushWorker;
        swapWorkerPosition = swapWorker;
        hasFinished = false;
        stateID = StateEnum.Move;
    }

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

    @Override
    public void startup(Model model) {

    }

    private void playerHasLost(Model model)
    {
        model.getTurn().getCurrentPlayer().setHasLost();
        //TODO: Eliminare i suoi worker e andare al turno del player successivo
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }

    @Override
    public void update(Choice userChoice, Model model) throws WrongChoiceTypeException, MoveErrorException
    {
        Player actingPlayer = model.getTurn().getPlayer(userChoice.getId());
        if(userChoice instanceof SelectWorkerCellChoice)
        {
            Box workerBox = model.getTurn().getBoardInstance().getBox(((SelectWorkerCellChoice) userChoice).x,
                            ((SelectWorkerCellChoice) userChoice).y);

            //A worker is present in the box and belongs to the actingPlayer
            if(workerBox.isOccupied())
            {
                if(actingPlayer.getWorkerList().get(0).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(0);
                }
                else if(actingPlayer.getWorkerList().get(1).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(1);
                }
                else
                    {
                        throw new MoveErrorException("Selected Worker does not belong to player: "
                                + actingPlayer.getPlayerName()+"!");
                    }
            }
            else
                {
                    throw new MoveErrorException("No Worker present in the box selected by "
                            + actingPlayer.getPlayerName()+"!");
                }
        }
        else if(userChoice instanceof MoveChoice)
        {

        }
        else
            {
                throw new WrongChoiceTypeException("Wrong choice in MoveState, received: "+ userChoice.toString());
            }
    }
}
