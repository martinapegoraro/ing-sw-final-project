package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.MoveErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectWorkerPositionErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectedCellErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;
import it.polimi.ingsw.Utils.MoveChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.ArrayList;

/**
 * The MoveState is used every time a player has to move.
 * It is usually built after the ActivationGodState and
 * it contains the list of possible moves for each worker and four boolean variables
 * which report whether the gods Apollo, Minotaur, Hera or Prometheus are active.
 * the boolean variable points out if the action is the first one, because
 * when Prometheus is active the move is the second action
 */

public class MoveState implements State {
    private StateEnum stateID;
    private ArrayList<Box> possibleMovesWorker0;
    private ArrayList<Box> possibleMovesWorker1;
    private boolean swapWorkerPosition;
    private boolean pushWorkerBack;
    private boolean hasFinished;
    private boolean heraIsActive;
    private boolean firstAction;

    /**
     * Used to initialize the state.
     * It receives the two lists of possible moves for the workers,
     * the model and the four boolean variables which state if the gods are active
     * @param possibleMovesby0 possible moves for the first worker
     * @param possibleMovesby1 possible moves for the second worker
     * @param pushWorker if Minotaur is active this is set to true, it allows workers to push others
     * @param swapWorker if Apollo is active this is set to true, allows worker swaps
     * @param firstAction if the Move is the first action in the turn (move or build)
     *                    this flag is set to true
     */

    public MoveState(ArrayList<Box> possibleMovesby0,ArrayList<Box> possibleMovesby1, boolean pushWorker, boolean swapWorker, boolean heraIsActive, Model model,boolean firstAction)
    {

        //If possibleMoves is empty the player has lost
        stateID = StateEnum.Move;
        if(possibleMovesby0.isEmpty() && possibleMovesby1.isEmpty() && firstAction)
        {
            playerHasLost(model);
        }
        this.firstAction=firstAction;

        possibleMovesWorker0 = possibleMovesby0;
        possibleMovesWorker1 = possibleMovesby1;
        pushWorkerBack = pushWorker;
        swapWorkerPosition = swapWorker;
        hasFinished = false;
        this.heraIsActive = heraIsActive;

        startup(model);
        if(model.getTurn().getCurrentPlayer().getHasLost())
        {
            hasFinished=true;
        }
    }

    /**
     * @return the ID of the state
     */

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

    /**
     * Called during initialization,
     * it updates the current state of the ModelRepresentation
     */

    @Override
    public void startup(Model model) {
        model.updateModelRep(stateID);
    }

    /**
     * sets the boolean variable hasLost of the current player to true
     */

    private void playerHasLost(Model model)
    {
        model.getTurn().getCurrentPlayer().setHasLost();

    }

    /**
     * @return the boolean variable that is used to check if the state has finished
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }

    /**
     * takes the MoveChoice of the player and updates the model accordingly,
     * taking into account all the activated gods
     * @throws WrongChoiceException if choices other than SelectWorkerCellChoice or MoveChoice are received
     * @throws MoveErrorException if the Move is not valid (because of god effects or basic game rules)
     */

    @Override
    public void update(Choice userChoice, Model model) throws WrongChoiceException, MoveErrorException
    {
        Player actingPlayer = model.getTurn().getPlayer(userChoice.getId());
        if(userChoice.toString().equals("SelectWorkerCellChoice"))
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
                    if(!firstAction && possibleMovesWorker0.isEmpty()) {
                        playerHasLost(model);
                        hasFinished=true;
                        return;
                    }
                    model.updateModelRep(possibleMovesWorker0);

                }
                else if(actingPlayer.getWorkerList().get(1).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(1);
                    if(!firstAction && possibleMovesWorker0.isEmpty()) {
                        playerHasLost(model);
                        hasFinished=true;
                    }
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
        else if(userChoice.toString().equals("MoveChoice"))
        {
            MoveChoice currentChoice;
            currentChoice = (MoveChoice)userChoice;
            Box b=new Box(0,0);
            try
            {
                b = model.getTurn().getBoardInstance().getBox(currentChoice.x,currentChoice.y);
            }
            catch(IndexOutOfBoundsException ex)
            {
                model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                throw new WrongChoiceException("MoveChoice coords are invalid: " + currentChoice.x + "," + currentChoice.y);
            }

            //Checks if a worker has already been selected
            if(actingPlayer.getSelectedWorker() == null)
            {
                model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                throw new MoveErrorException("No worker has been selected for the move!");
            }

            //Checks which worker is currently active and if selected cell is compatible with worker
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
                            int xPush, yPush,x,y;
                            x=opponentWorker.getPosition().getCoord()[0];
                            y=opponentWorker.getPosition().getCoord()[1];
                            xPush = x - actingPlayer.getSelectedWorker().getPosition().getCoord()[0];
                            yPush = y - actingPlayer.getSelectedWorker().getPosition().getCoord()[1];

                            opponentWorker.move(model.getTurn().getBoardInstance().getBox(x+xPush, y+yPush));
                            actingPlayer.getSelectedWorker().move(b);
                        }
                    }
                    else
                        {
                            //No special god effects apply
                            actingPlayer.getSelectedWorker().move(b);
                        }

                    //Sets this Box to use in Context for creating Artemis second MoveState
                    //It saves the old position, not the box the player moved to
                    actingPlayer.setLastMoveBox(oldBox);

                    //If the player moves up his AthenaCondition will be set to true, at the beginning of his next
                    //MoveState it'll be set to false again
                    if(b.getTower().getHeight() > oldBox.getTower().getHeight())
                    {
                        actingPlayer.changeAthenaCondition(true);
                    }

                    //If the player moves down two or more levels the PanCondition is set to true
                    if (oldBox.getTower().getHeight() - b.getTower().getHeight() >= 2)
                    {
                        actingPlayer.changePanCondition (true);
                    }

                    //Check to see if player has won using default rules, moving UP to third level
                    if(b.getTower().getHeight() == 3 && oldBox.getTower().getHeight() == 2 &&
                            (!heraIsActive || !b.isBorder()))
                    {
                        //If Hera is active the player can't win on a border Box
                        //The flag heraIsActive is set only for opponent's turn
                        //Nobody can play Hera card in it's turn
                        for(Player p:model.getTurn().getPlayersList())
                        {
                            if(p.getNumber()!=actingPlayer.getNumber())
                                p.setHasLost();
                        }
                        actingPlayer.setHasWon();

                    }

                    model.updateModelRep(stateID);
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
