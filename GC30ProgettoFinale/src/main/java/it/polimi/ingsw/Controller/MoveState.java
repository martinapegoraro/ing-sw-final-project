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
    private ArrayList<Box> possibleMovesWorker0;
    private ArrayList<Box> possibleMovesWorker1;
    private boolean swapWorkerPosition;
    private boolean pushWorkerBack;
    private boolean hasFinished;

    public MoveState(ArrayList<Box> possibleMovesby0,ArrayList<Box> possibleMovesby1, boolean pushWorker, boolean swapWorker, Model model)
    {
        //If possibleMoves is empty the player has lost
        if(possibleMovesby0.isEmpty() && possibleMovesby1.isEmpty())
        {
            playerHasLost(model);
        }

        possibleMovesWorker0 = possibleMovesby0;
        possibleMovesWorker1 = possibleMovesby1;
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
                    model.updateModelRep(possibleMovesWorker0);

                }
                else if(actingPlayer.getWorkerList().get(1).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(1);
                    model.updateModelRep(possibleMovesWorker1);
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
            MoveChoice currentChoice;
            currentChoice = (MoveChoice)userChoice;
            Box b;
            b = model.getTurn().getBoardInstance().getBox(currentChoice.x,currentChoice.y);

            //Check which worker is currently active and if selected cell is compatible with worker
            //Otherwise ignores the choice
            if(
                    (possibleMovesWorker0.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(0))
                    ||
                    (possibleMovesWorker1.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(1)))
            {
                try
                {
                    actingPlayer.getSelectedWorker().move(b);
                    model.updateModelRep();
                    hasFinished = true;
                }
                catch (MoveErrorException ex)
                {
                    System.out.println(ex.getMessage());
                }
                catch (NullPointerException ex)
                {
                    System.out.println("No selected worker!");
                }
                finally
                {
                    actingPlayer.setSelectedWorker(null);
                }
            }
            else
                {
                    //The box is not a valid move
                    throw new MoveErrorException("The move received is unvalid!");
                }

        }
        else
            {
                throw new WrongChoiceTypeException("Wrong choice in MoveState, received: "+ userChoice.toString());
            }
    }
}
