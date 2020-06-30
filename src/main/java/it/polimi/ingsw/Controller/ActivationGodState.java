package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.GodConditionNotSatisfiedException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.GodNotActionableErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;
import it.polimi.ingsw.Utils.GodActivationChoice;

import java.util.ArrayList;

/**
 * the ActivationGodState class is built in every turn,
 * when each player has to decide if he wants to activate is god card
 */

public class ActivationGodState implements State {
    StateEnum stateID;
    private boolean hasFinished;
    ArrayList<Player> playerHaveSelected = new ArrayList<>();

    /**
     * the constructor is called to initialize the state
           */

    public ActivationGodState(Model model)
    {
        stateID = StateEnum.ActivationGod;
        hasFinished = false;
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
     * this method is called when ActivationGodState is initialized
     * it updates the ModelRepresentation
     */

    @Override
    public void startup(Model model)
    {
        model.updateModelRep(stateID);
    }

    /**Called when activating Minotaur card, checks if the opponent worker has a free cell
     * behind him to use the  God's power**/
    private boolean minotaurCheck(Box opponentWorkerBox, Box playerWorkerBox, Model model)
    {
        int x1,x2,y1,y2;
        int xBehind, yBehind;
        x1 = opponentWorkerBox.getCoord()[0];
        y1 = opponentWorkerBox.getCoord()[1];
        x2 = playerWorkerBox.getCoord()[0];
        y2 = playerWorkerBox.getCoord()[1];

        xBehind = x1 + (x1-x2);
        yBehind = y1 + (y1-y2);

        try
        {
            Box behindBox = model.getTurn().getBoardInstance().getBox(xBehind, yBehind);
            return !behindBox.isOccupied();
        }
        catch(IndexOutOfBoundsException ex)
        {
            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), model.getTurn().getCurrentPlayer()));
            System.out.println("Worker is at the edge of the board, cannot use Minotaur!\n" + ex.getMessage());
            return false;
        }
    }

    /**Called by Context after BeginTurnState has finished
     * The method collects the decision for every player
     * to activate their god card and updates the Model accordingly.
     * It also performs a check to see if the god can be activated
     * @param userChoice is a GodActivationChoice
     * **/
    @Override
    public void update(Choice userChoice, Model model) throws WrongChoiceException, GodConditionNotSatisfiedException
    {
        Player actingPlayer;
        ArrayList<Player> playerList = (ArrayList<Player>) model.getTurn().getPlayersList();
        actingPlayer = playerList.get(userChoice.getId());
        if(userChoice.toString().equals("GodActivationChoice"))
        {

            GodActivationChoice castedChoice = (GodActivationChoice)userChoice;

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
                        boolean apolloCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(b.isOccupied() && !b.getTower().getPieces().contains(Block.DOME))
                            {
                                //Flag isOccupied is used both with domes and workers
                                if(b != firstWorkerBox && b != secondWorkerBox)
                                {
                                    apolloCondition = true;
                                }
                            }
                        }

                        if(actingPlayer.isPlayerActive() && apolloCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                            {
                                model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                                //throw new GodConditionNotSatisfiedException("Apollo can't be activated!");
                            }
                        break;

                    case ATHENA:
                        //Check if player in his last Move has satisfied  AthenaCondition, which is resetted if it's the
                        //player's turn, !actingPlayer.isPlayerActive() is not needed, just for security

                        if(actingPlayer.isAthenaConditionTrue() && !actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            //throw new GodConditionNotSatisfiedException("Athena can't be activated!");
                        }
                        break;

                    case ARTEMIS:
                        //Check it's player's turn
                        //Player can move two times without moving back to it's own space
                        //Check is done in Context
                        if(actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            // throw new GodConditionNotSatisfiedException("Artemis can't be activated!");
                        }
                        break;

                    case HESTIA:
                        //Check it's player's turn
                        if(actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else{
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            //throw new GodConditionNotSatisfiedException("Hestia can't be activated!");
                        }
                        break;

                    case ATLAS:
                        //Atlas can build dome at any level (ground is not included)
                        /*boolean atlasCondition = false;
                        for(Box b : neighborBoxes)
                        {
                            if(!b.isOccupied() && !(b.getTower().getHeight() == 0))
                            {
                                //Flag isOccupied is used both with domes and workers
                                atlasCondition = true;
                            }
                        }*/

                        if(actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            //throw new GodConditionNotSatisfiedException("Atlas can't be activated!");
                        }
                        break;

                    case DEMETER:
                        //Checks if it's player turn
                        //the check for two possible consecutive builds will be done by the Context
                        if(actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            // throw new GodConditionNotSatisfiedException("Demeter can't be activated!");
                        }
                        break;


                    case HEPHAESTUS:
                        //Checks if there is a tower in player workers vicinity with height <= 1 (to build two times)
                        boolean hephaestusCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(!b.getTower().getPieces().contains(Block.DOME) && b.getTower().getHeight() <= 1)
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
                                model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                                // throw new GodConditionNotSatisfiedException("Hephaestus can't be activated!");
                            }
                        break;

                    case HERA:
                        //Check that it's not the actingPlayer's turn
                        if(!actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                            {
                                model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                                //throw new GodConditionNotSatisfiedException("Hera can't be activated!");
                            }
                        break;

                    case MINOTAUR:
                        //Like Apollo condition but for every OPPONENT worker the space behind it must be free
                        //To check if worker belongs to opponent I just check it's not mine
                        boolean minotaurCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if(b.isOccupied() &&
                                    (!b.getTower().getPieces().contains(Block.DOME)))
                            {
                                //This is not a big problem though because the check is done later in the Context (minotaurEffect)
                                //Flag isOccupied is used both with domes and workers
                                //This part calculates the cell opposite to workerCell with center in b
                                //The boxes have to be bounded to board (no out of bounds)
                                if(b != firstWorkerBox && b!= secondWorkerBox)
                                {
                                    if(b.isAdjacent(firstWorkerBox))
                                    {
                                        if(minotaurCheck(b, firstWorkerBox, model)) minotaurCondition = true;
                                    }
                                    if(b.isAdjacent(secondWorkerBox))
                                    {
                                        if(minotaurCheck(b, secondWorkerBox, model)) minotaurCondition = true;
                                    }
                                }
                            }
                        }

                        if(actingPlayer.isPlayerActive() && minotaurCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            //throw new GodConditionNotSatisfiedException("Minotaur can't be activated!");
                        }
                        break;

                    case PERSEPHONE:
                        //Opponent's turn
                        if(!actingPlayer.isPlayerActive())
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                            {
                                model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                                //throw new GodConditionNotSatisfiedException("Persephone can't be activated on player's turn!");
                            }
                        break;

                    case PROMETHEUS:
                        //Player's turn and Must be able to build before moving
                        boolean prometheusCondition = false;

                        for(Box b : neighborBoxes)
                        {
                            if (!b.isOccupied()) {
                                //There is a box where the workers can build
                                prometheusCondition = true;
                                break;
                            }
                        }

                        if(actingPlayer.isPlayerActive() && prometheusCondition)
                        {
                            actingPlayer.setGodActive(true);
                        }
                        else
                        {
                            model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                            //throw new GodConditionNotSatisfiedException("Prometheus can't be activated!");
                        }
                        break;

                    case ZEUS:
                        //Checks at least one of the player's workers can build under itself
                        //And checks it player's turn
                        boolean zeusCondition = false;
                       if((!firstWorkerBox.getTower().getPieces().contains(Block.DOME)&&firstWorkerBox.getTower().getHeight()<=2)
                           ||(!secondWorkerBox.getTower().getPieces().contains(Block.DOME)&&secondWorkerBox.getTower().getHeight()<=2))
                       {
                           zeusCondition = true;
                       }

                       if(actingPlayer.isPlayerActive() && zeusCondition)
                       {
                           actingPlayer.setGodActive(true);
                       }
                       else
                       {
                           model.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), actingPlayer));
                           //throw new GodConditionNotSatisfiedException("Zeus can't be activated!");
                       }
                       break;


                }
            }

            //If all players have made the activation choice the state has finished
            playerHaveSelected.add(actingPlayer);
            int losers=0;
            for (Player p:model.getTurn().getPlayersList() ) {
                if(p.getHasLost())
                    losers++;
            }
            if(playerHaveSelected.size() == (playerList.size()-losers))
            {
                hasFinished = true;
                model.updateModelRep(stateID);
            }
        }
        else
            {
                model.notify(new MessageToVirtualView(new SentChoiceError(), actingPlayer));
                throw new WrongChoiceException("Wrong Choice, EXPECTED: GodActivation, FOUND: "+ userChoice.toString());
            }

    }


    /**
     * @return the boolean variable that is used to check if the state has finished
     */
    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
