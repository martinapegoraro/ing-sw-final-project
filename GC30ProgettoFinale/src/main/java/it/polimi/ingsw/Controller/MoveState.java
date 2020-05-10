package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.MoveErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectWorkerPositionErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectedCellErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;
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
    public void update(Choice userChoice, Model model) throws WrongChoiceException, MoveErrorException
    {
        Player actingPlayer = model.getTurn().getPlayer(userChoice.getId());
        if(userChoice instanceof SelectWorkerCellChoice)
        {
            //Cannot assume the choice message is valid!
            Box workerBox;
            try
            {
                workerBox = model.getTurn().getBoardInstance().getBox(((SelectWorkerCellChoice) userChoice).x,
                        ((SelectWorkerCellChoice) userChoice).y);
            }
            catch(IndexOutOfBoundsException ex)
            {
                model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                throw new WrongChoiceException("WorkerChoice message is not valid! COORDINATES: " +
                        ((SelectWorkerCellChoice) userChoice).x + " " + ((SelectWorkerCellChoice) userChoice).y);
            }

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
                        model.notify(new MessageToVirtualView(new SelectWorkerPositionErrorMessage()));
                        throw new MoveErrorException("Selected Worker does not belong to player: "
                                + actingPlayer.getPlayerName()+"!");
                    }
            }
            else
                {
                    model.notify(new MessageToVirtualView(new SelectWorkerPositionErrorMessage()));
                    throw new MoveErrorException("No Worker present in the box selected by "
                            + actingPlayer.getPlayerName()+"!");
                }
        }
        else if(userChoice instanceof MoveChoice)
        {
            MoveChoice currentChoice;
            currentChoice = (MoveChoice)userChoice;
            Box b;
            try
            {
                b = model.getTurn().getBoardInstance().getBox(currentChoice.x,currentChoice.y);
            }
            catch(IndexOutOfBoundsException ex)
            {
                model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                throw new WrongChoiceException("MoveChoice coords are invalid: " + currentChoice.x + "," + currentChoice.y);
            }

            //Check which worker is currently active and if selected cell is compatible with worker
            //Otherwise ignores the choice
            if(
                    (possibleMovesWorker0.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(0))
                    ||
                    (possibleMovesWorker1.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(1)))
            {
                try
                {
                    Box oldBox;
                    oldBox = actingPlayer.getSelectedWorker().getPosition();
                    Worker opponentWorker = null;
                    if(swapWorkerPosition)
                    {
                        //Apollo is active
                        for(Player p : model.getTurn().getPlayersList())
                        {
                            if(p!=actingPlayer)
                            {
                                for(Worker w: p.getWorkerList())
                                {
                                    if(w.getPosition() == b)
                                    {
                                        opponentWorker = w;
                                        break;
                                    }
                                }
                            }
                        }

                        if(opponentWorker != null)
                        {
                            opponentWorker.swap(actingPlayer.getSelectedWorker());
                        }
                        else
                            {
                                throw new MoveErrorException("There's no opponent worker in the vicinity to swap!");
                            }


                    }
                    else if (pushWorkerBack) {
                        //Minotaur is active
                        for(Player p : model.getTurn().getPlayersList())
                        {
                            if(p!=actingPlayer)
                            {
                                for(Worker w: p.getWorkerList())
                                {
                                    if(w.getPosition() == b)
                                    {
                                        opponentWorker = w;
                                        break;
                                    }
                                }
                            }
                        }

                        if(opponentWorker != null)
                        {
                            //Calculate push cell
                            int xPush, yPush;
                            xPush =
                                    opponentWorker.getPosition().getCoord()[0] - actingPlayer.getSelectedWorker().getPosition().getCoord()[0];
                            yPush =
                                    opponentWorker.getPosition().getCoord()[1] - actingPlayer.getSelectedWorker().getPosition().getCoord()[1];

                            opponentWorker.move(model.getTurn().getBoardInstance().getBox(xPush, yPush));
                            actingPlayer.getSelectedWorker().move(b);
                        }
                    }
                    else
                        {
                            //No special god effects apply
                            actingPlayer.getSelectedWorker().move(b);
                        }

                    //If the player moves up his AthenaCondition will be set to true, at the beginning of his next
                    //MoveState it'll be set to false again
                    if(b.getTower().getHeight() > oldBox.getTower().getHeight())
                    {
                        actingPlayer.changeAthenaCondition(true);
                    }

                    //Check to see if player has won using default rules, moving to third level
                    if(b.getTower().getHeight() == 3)
                    {
                        actingPlayer.setHasWon();
                    }

                    model.updateModelRep();
                    hasFinished = true;
                }
                catch (MoveErrorException ex)
                {
                    model.notify(new MessageToVirtualView(new MoveErrorMessage()));
                    System.out.println(ex.getMessage());
                }
                catch (NullPointerException ex)
                {
                    model.notify(new MessageToVirtualView(new SelectWorkerPositionErrorMessage()));
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
                    model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                    throw new MoveErrorException("The move received is unvalid!");
                }

        }
        else
            {
                model.notify(new MessageToVirtualView(new SentChoiceError()));
                throw new WrongChoiceException("Wrong choice in MoveState, received: "+ userChoice.toString());
            }
    }
}
