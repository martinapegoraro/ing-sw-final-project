package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.BuildErrorException;
import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Utils.BuildChoice;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.BuildErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectWorkerPositionErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SelectedCellErrorMessage;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.ArrayList;

/**
 * the BuildState class is used every time the player has to build a block,
 * usually after the MoveState.
 * It contains the two lists of the boxes where each worker of the current player can build
 * and three boolean variables which are true if the effects of Atlas, Hephaestus or Prometheus are active.
 * They report if the player can build a dome at any level or if he can build twice on the same spot, with two
 * boolean variables which point out if the effects of the gods are active,
 * and if he can build before and after moving, with a boolean variable which points out
 * if the build is the first action (possible only with Prometheus active) or not
 */

public class BuildState implements State{
    private boolean hasFinished;
    private StateEnum stateID;
    ArrayList<Box> possibleBuildListWorker1;
    ArrayList<Box> possibleBuildListWorker2;
    boolean domeAtAnyLevel;
    boolean twoBlocksHephaestus;
    private boolean firstAction;


    /**
     * the builder is called to initialize the state
     * it receives the two lists of possible boxes for the two workers, the model
     * and three boolean variables to verify whether the effects of
     * the gods Atlas, Hephaestus or Prometheus are active.
     * @param pBLW1
     * @param pBLW2
     * @param domeAAL
     * @param twoBlocksBuilt
     * @param model
     * @param firstAction
     */

    public BuildState(ArrayList<Box> pBLW1,ArrayList<Box> pBLW2, boolean domeAAL, boolean twoBlocksBuilt, Model model,boolean firstAction)
    {
        this.firstAction=firstAction;
        domeAtAnyLevel = domeAAL;
        possibleBuildListWorker1 = pBLW1;
        possibleBuildListWorker2 = pBLW2;
        twoBlocksHephaestus = twoBlocksBuilt;
        stateID=StateEnum.Build;
        //If possibleMoves is empty the player has lost
        if(possibleBuildListWorker1.isEmpty() && possibleBuildListWorker2.isEmpty() && firstAction)
        {
            playerHasLost(model);
        }


        hasFinished=false;
        startup(model);
        if(model.getTurn().getCurrentPlayer().getHasLost())
        {
            hasFinished=true;
        }

    }

    /**
     * modifies the modelif a player has lost
     * @param model
     */

    private void playerHasLost(Model model)
    {
        model.getTurn().getCurrentPlayer().setHasLost();

    }

    /**
     * returns the ID of the state
     * @return
     */

    @Override
    public StateEnum getID() {
        return StateEnum.Build;
    }

    /**
     * updetes the ModelReoresentation with the current state
     * @param model
     */

    @Override
    public void startup(Model model) {
        model.updateModelRep(StateEnum.Build);
    }


    /**
     * updates the model using the BuildChoice of the current player,
     * taking into account the activated gods
     * @param userChoice
     * @param model
     * @throws BuildErrorException
     * @throws WrongChoiceException
     */

    @Override
    public void update(Choice userChoice, Model model) throws BuildErrorException, WrongChoiceException {
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
                model.notify(new MessageToVirtualView(new SelectWorkerPositionErrorMessage()));
                throw new IndexOutOfBoundsException("WorkerChoice message is not valid! COORDINATES: " +
                        ((SelectWorkerCellChoice) userChoice).x + " " + ((SelectWorkerCellChoice) userChoice).y);
            }

            //A worker is present in the box and belongs to the actingPlayer
            if(workerBox.isOccupied())
            {
                if(actingPlayer.getWorkerList().get(0).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(0);
                    if(!firstAction && possibleBuildListWorker1.isEmpty())
                        playerHasLost(model);
                    model.updateModelRep(possibleBuildListWorker1);

                }
                else if(actingPlayer.getWorkerList().get(1).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(1);
                    if(!firstAction && possibleBuildListWorker2.isEmpty())
                        playerHasLost(model);
                    model.updateModelRep(possibleBuildListWorker2);
                }
                else
                {
                    model.notify(new MessageToVirtualView(new SelectWorkerPositionErrorMessage()));
                    throw new BuildErrorException("Selected Worker does not belong to player: "
                            + actingPlayer.getPlayerName()+"!");
                }
            }
            else
            {
                model.notify(new MessageToVirtualView(new BuildErrorMessage()));
                throw new BuildErrorException("No Worker present in the box selected by "
                        + actingPlayer.getPlayerName()+"!");
            }
        }
        else if(userChoice instanceof BuildChoice)
        {
            BuildChoice currentChoice;
            currentChoice = (BuildChoice)userChoice;
            Box b;
            try
            {
                b = model.getTurn().getBoardInstance().getBox(currentChoice.x,currentChoice.y);
            }
            catch(IndexOutOfBoundsException ex)
            {
                model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                throw new IndexOutOfBoundsException("BuildChoice coords are invalid: " + currentChoice.x + "," + currentChoice.y);
            }


            //Check which worker is currently active and if selected cell is compatible with worker
            //Otherwise ignores the choice
            if(
                    (possibleBuildListWorker1.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(0))
                            ||
                    (possibleBuildListWorker2.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(1)))
            {
                try
                {
                    if(twoBlocksHephaestus){
                        //Hephaestus can't build domes, so tower must not exist or be at 1st level
                       if(b.getTower().getHeight() <= 1)
                       {
                           b.build();
                           b.build();
                       }
                       else
                           {
                               //Exceptions in build are already handled by Model
                               b.build();
                           }
                    }
                    else if(domeAtAnyLevel){
                        try {
                            if(b.getTower().getHeight() == 0)
                            {
                                b.getTower().build();
                            }
                            else
                                {
                                    b.getTower().build(Block.DOME);
                                    b.setOccupied();
                                }
                        } catch (TowerCompleteException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        {
                            b.build();
                        }

                    //This player parameter is used in Context when creating second Build state for Demeter
                    actingPlayer.setLastBuildBox(b);
                    model.updateModelRep(stateID);
                    hasFinished = true;
                }
                //catch (BuildErrorException ex)
                //{
                  //  System.out.println(ex.getMessage());
                //}
                catch (NullPointerException ex)
                {
                    model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                    System.out.println("No selected worker!");
                }
                finally
                {
                    actingPlayer.setSelectedWorker(null);
                }
            }
            else
            {
                //The box is not a valid position for a build
                model.notify(new MessageToVirtualView(new BuildErrorMessage()));
                throw new BuildErrorException("The build received is unvalid!");
            }

        }
        else
        {
            model.notify(new MessageToVirtualView(new BuildErrorMessage()));
            throw new WrongChoiceException("Wrong choice in Build, received: "+ userChoice.toString());
        }
    }

    /**
     * returns the boolean variable that is used to check if the state has finished
     * @return
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
