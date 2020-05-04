package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.BuildErrorException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceTypeException;
import it.polimi.ingsw.Utils.BuildChoice;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.MoveChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BuildState implements State{
    private boolean hasFinished;
    ArrayList<Box> possibleBuildListWorker1;
    ArrayList<Box> possibleBuildListWorker2;
    boolean domeAtAnyLevel;


    public BuildState(ArrayList<Box> pBLW1,ArrayList<Box> pBLW2, boolean domeAAL,Model model)
    {
        domeAtAnyLevel = domeAAL;
        possibleBuildListWorker1 = pBLW1;
        possibleBuildListWorker2 = pBLW2;
        //If possibleMoves is empty the player has lost
        if(possibleBuildListWorker1.isEmpty() && possibleBuildListWorker2.isEmpty())
        {
            playerHasLost(model);
        }
        hasFinished=false;
    }

    private void playerHasLost(Model model)
    {
        model.getTurn().getCurrentPlayer().setHasLost();

    }
    @Override
    public StateEnum getID() {
        return StateEnum.Build;
    }

    @Override
    public void startup(Model model) {

    }

    @Override
    public void update(Choice userChoice, Model model) throws BuildErrorException, WrongChoiceTypeException {
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
                    model.updateModelRep(possibleBuildListWorker1);

                }
                else if(actingPlayer.getWorkerList().get(1).getPosition() == workerBox)
                {
                    actingPlayer.setSelectedWorker(1);
                    model.updateModelRep(possibleBuildListWorker2);
                }
                else
                {
                    throw new BuildErrorException("Selected Worker does not belong to player: "
                            + actingPlayer.getPlayerName()+"!");
                }
            }
            else
            {
                throw new BuildErrorException("No Worker present in the box selected by "
                        + actingPlayer.getPlayerName()+"!");
            }
        }
        else if(userChoice instanceof BuildChoice)
        {
            BuildChoice currentChoice;
            currentChoice = (BuildChoice)userChoice;
            Box b;
            b = model.getTurn().getBoardInstance().getBox(currentChoice.x,currentChoice.y);

            //Check which worker is currently active and if selected cell is compatible with worker
            //Otherwise ignores the choice
            if(
                    (possibleBuildListWorker1.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(0))
                            ||
                            (possibleBuildListWorker2.contains(b) && actingPlayer.getSelectedWorker() == actingPlayer.getWorkerList().get(1)))
            {
                try
                {
                    if(domeAtAnyLevel=false){
                       b.build();
                    }
                    else {
                        try {
                            b.getTower().build(Block.DOME);
                        } catch (TowerCompleteException e) {
                            e.printStackTrace();
                        }
                    }
                    model.updateModelRep();
                    hasFinished = true;
                }
                //catch (BuildErrorException ex)
                //{
                  //  System.out.println(ex.getMessage());
                //}
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
                throw new BuildErrorException("The build received is unvalid!");
            }

        }
        else
        {
            throw new WrongChoiceTypeException("Wrong choice in MoveState, received: "+ userChoice.toString());
        }
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
