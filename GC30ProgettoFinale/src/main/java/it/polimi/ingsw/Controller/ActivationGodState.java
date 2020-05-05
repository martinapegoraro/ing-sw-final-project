package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.GodConditionNotSatisfiedException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceTypeException;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.GodActivationChoice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ActivationGodState implements State {
    StateEnum stateID;
    private boolean hasFinished;

    public ActivationGodState()
    {
        stateID = StateEnum.ActivationGod;
        hasFinished = false;
    }

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

    @Override
    public void startup(Model model) {
        //TODO: Setting the flag for each player to decide if he can activate his god card
    }

    /**Called by Context after BeginTurnState has finished
     * The method collects the decision for every player
     * to activate their god card and updates the Model accordingly
     * **/
    @Override
    public void update(Choice userChoice, Model model) throws WrongChoiceTypeException, GodConditionNotSatisfiedException
    {
        if(userChoice instanceof GodActivationChoice)
        {
            Player actingPlayer;
            GodActivationChoice castedChoice = (GodActivationChoice)userChoice;
            ArrayList<Player> playerList = (ArrayList<Player>) model.getTurn().getPlayersList();
            actingPlayer = playerList.get(userChoice.getId());

            if(castedChoice.godActive)
            {
                GodsList currentGod = actingPlayer.getGod();
                Box firstWorkerBox;
                Box secondWorkerBox;
                ArrayList<Box> neighborBoxes;
                firstWorkerBox = actingPlayer.getWorkerList().get(0).getPosition();
                secondWorkerBox = actingPlayer.getWorkerList().get(1).getPosition();

                neighborBoxes = (ArrayList<Box>)model.getTurn().getBoardInstance().getBorderBoxes(firstWorkerBox);
                neighborBoxes.addAll(model.getTurn().getBoardInstance().getBorderBoxes(secondWorkerBox));

                //Check if god card can be activated/has any possible moves it allows the player to do
                switch(currentGod) {
                    case APOLLO:
                        //Player's turn and there is an OPPONENT worker in neighboring spaces
                        //TODO: check if the worker belongs to opponent
                        boolean apolloCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(b.isOccupied() && (b.getTower() == null || !b.getTower().getPieces().contains(Block.DOME)))
                            {
                                //Flag isOccupied is used both with domes and workers
                                apolloCondition = true;
                            }
                        }



                        if(actingPlayer.isPlayerActive() && apolloCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                            {
                                throw new GodConditionNotSatisfiedException("Apollo can't be activated!");
                            }

                    case ATHENA:
                        //Check if player in his last Move has satisfied  AthenaCondition, which is resetted if it's the
                        //player's turn, !actingPlayer.isPlayerActive() is not needed, just for security

                        if(actingPlayer.isAthenaConditionTrue() && !actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            throw new GodConditionNotSatisfiedException("Athena can't be activated!");
                        }

                    case ARTEMIS:
                        //Player can move two times without moving back to it's own space
                        //Check is done in Context

                    case ATLAS:
                        //There has to be at least one free Tower near a player's worker, Domes cannot be built on level 0
                        boolean atlasCondition = false;
                        for(Box b : neighborBoxes)
                        {
                            if(!b.isOccupied() && !(b.getTower() == null))
                            {
                                //Flag isOccupied is used both with domes and workers
                                atlasCondition = true;
                            }
                        }
                        if(actingPlayer.isPlayerActive() && atlasCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            throw new GodConditionNotSatisfiedException("Atlas can't be activated!");
                        }

                    case DEMETER:
                        //Checks if it's player turn and if two sequential moves are possible without
                        //going back to the starting cell

                    case HEPHAESTUS:
                        //Checks if there is a tower in player workers vicinity with height <= 1 (to build two times)
                        boolean hephaestusCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(b.getTower() == null || (!b.getTower().getPieces().contains(Block.DOME) && b.getTower().getHeight() == 1))
                            {
                                //Domes should not be on ground, it's just a safety check
                                hephaestusCondition = true;
                            }
                        }
                        if(actingPlayer.isPlayerActive() && hephaestusCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                            {
                                throw new GodConditionNotSatisfiedException("Hephaestus can't be activated!");
                            }

                    case MINOTAUR:
                        //Like Apollo condition but for every OPPONENT worker the space behind it must be free
                        //TODO: Check if worker belongs to opponent!
                        boolean minotaurCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(b.isOccupied() && (b.getTower() == null || !b.getTower().getPieces().contains(Block.DOME)))
                            {
                                //Flag isOccupied is used both with domes and workers
                                //This part calculates the cell opposite to workerCell with center in b
                                //The boxes have to be bounded to board (no out of bounds)
                                ArrayList<Box> borderBoxes = (ArrayList<Box>) model.getTurn().getBoardInstance().getBorderBoxes(b);
                                if(borderBoxes.contains(firstWorkerBox))
                                {
                                    //TODO: Calculate mirror box excluding borderboxes
                                }
                                else if(borderBoxes.contains(secondWorkerBox))
                                {

                                }
                                minotaurCondition = true;
                            }
                        }



                        if(actingPlayer.isPlayerActive() && minotaurCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            throw new GodConditionNotSatisfiedException("Apollo can't be activated!");
                        }

                    case PROMETHEUS:
                        //Must be able to build before moving

                }
            }
        }
        else
            {
                throw new WrongChoiceTypeException("Wrong Choice, EXPECTED: GodActivation, FOUND: "+ userChoice.toString());
            }

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
