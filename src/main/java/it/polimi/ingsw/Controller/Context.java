package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.GodNotActionableErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;
import it.polimi.ingsw.View.Observer;

import java.util.ArrayList;

/**
 * The Context contains the logic of the game.
 * it controls the turn flow according to the choices
 * of the players and the current model.
 * It contains some boolean variables used to apply god effects
 * and an ArrayList used by Artemis and Demeter
 * to save the old player positions
 */

public class Context implements Observer<Choice> {
    private State currentState;
    //private int numberofPlayers;
    private Model contextModel;
    private ArrayList<GodsList> activeGods;

    //Variables used to apply god effects
    private boolean prometheusFirstBuild;
    private boolean artemisFirstMove;
    private boolean demeterFirstBuild;
    private boolean hestiaSecondBuild;
    //Used by Artemis and Demeter to save the old player positions

    /**
     * Context constructor
     * @throws NullPointerException when model is null
     */

    public Context(Model model) throws NullPointerException
    {
        if(model == null)
        {
            throw new NullPointerException("Model can't be null when linking Context!");
        }
        currentState = new SetUpState(model);
        //numberofPlayers = 0;
        contextModel = model;
        activeGods = new ArrayList<>();
        hestiaSecondBuild=false;
    }


    //______________________________________State change methods_______________________________________________________


    /**The method provides a quick way to change to the next state in the State pattern
     * according to current state and active god cards whom disrupt
     * the normal state flow
     * **/
    private void stateChange()
    {
        //If a god who changes state flow is active the state transition is
        //handled by the god method
        if(activeGods.size()>0)
        {
            if(activeGods.contains(GodsList.ARTEMIS))
            {
                artemisTurnFlow();
                return;
            }
            else if(activeGods.contains(GodsList.DEMETER))
            {
                demeterTurnFlow();
                return;
            }
            else if(activeGods.contains(GodsList.PROMETHEUS))
            {
                prometheusTurnFlow();
                return;
            }
            else if(activeGods.contains(GodsList.HESTIA))
            {
                hestiaTurnFlow();
                return;
            }
        }

        State newState;

        switch(currentState.getID())
        {
            case SetUp:
                newState = new BeginTurnState(contextModel);
                switchState(newState);
                break;

            case BeginTurn:
                newState = new ActivationGodState(contextModel);
                switchState(newState);
                break;
            case ActivationGod:
                //I save the active Gods for this turn for easier access later
                ArrayList<Player> playersList = (ArrayList<Player>)contextModel.getTurn().getPlayersList();
                for(Player player : playersList)
                {
                    if(player.isGodActive())
                    {
                        activeGods.add(player.getGod());
                    }
                }

                //The check is done again to ensure state switches are handled after ActivatioGodState
                //without this second check the flow would go directly to MoveState
                if(activeGods.contains(GodsList.ARTEMIS))
                {
                    artemisTurnFlow();
                }
                else if(activeGods.contains(GodsList.DEMETER))
                {
                    demeterTurnFlow();
                }
                else if(activeGods.contains(GodsList.PROMETHEUS))
                {
                    prometheusTurnFlow();
                }
                else if(activeGods.contains(GodsList.HESTIA))
                {
                    hestiaTurnFlow();
                }
                else
                    {
                        newState = moveStateConstructor(true);
                        switchState(newState);
                    }

                break;

            case Move:
                newState = new CheckWinConditionState(1, contextModel);
                switchState(newState);
                break;

            case FirstCheckWinCondition:
                if(!contextModel.getTurn().getCurrentPlayer().getHasLost())
                     newState = buildStateConstructor(false);
                else
                    newState=new EndTurnState(contextModel);
                switchState(newState);
                break;

            case Build:
                newState = new CheckWinConditionState(2, contextModel);
                switchState(newState);

            case SecondCheckWinCondition:
                newState = new EndTurnState(contextModel);
                switchState(newState);
                break;

            case EndTurn:
                newState = new BeginTurnState(contextModel);
                activeGods.clear();
                switchState(newState);
                break;
        }
    }

    public void switchState(State next)
    {
        currentState = next;
    }



    //________________________________________MOVE/BUILD METHODS__________________________________________________

    /**Returns a List of all the possible Move boxes surrounding
     * the active player selected worker
     * taking in consideration active move-affecting gods
     * @param currentCell the selected workers cell*/
    private ArrayList<Box> getPossibleMoveBoxes(Box currentCell)
    {
        ArrayList<Box> possibleMoves = (ArrayList<Box>)contextModel.getTurn().getPossibleMoves(currentCell);

        //Check god cards to first extend then contract list of boxes
        //Gods activated by the current player expanding the list of move boxes
        if(activeGods.contains(GodsList.APOLLO))
        {
            possibleMoves = apolloEffect(possibleMoves, currentCell);
        }
        else if(activeGods.contains(GodsList.MINOTAUR))
        {
            possibleMoves = minotaurEffect(possibleMoves, currentCell);
        }

        //Gods activated by the current player contracting the list of move boxes
        if (activeGods.contains(GodsList.ARTEMIS) && !artemisFirstMove)
        {
            //Eliminates from the list of possible moves the last cell where the Player moved to
            possibleMoves = artemisBoxEffect(possibleMoves);
        }
        else if(activeGods.contains(GodsList.PROMETHEUS))
        {
            possibleMoves = prometheusMoveEffect(possibleMoves, currentCell);
        }

        //Gods activated by another player contracting the list of move boxes
        if(activeGods.contains(GodsList.ATHENA))
        {
            possibleMoves = athenaEffect(possibleMoves, currentCell);
        }
        else if (activeGods.contains(GodsList.PERSEPHONE))
        {
            //If the player can move up then the choice is forced, if not the card has no effect
            ArrayList<Box> godMoves = persephoneEffect(possibleMoves, currentCell);
            if(!godMoves.isEmpty())
            {
                possibleMoves = godMoves;
            }
        }

        return possibleMoves;
    }

    /**Returns a State constructed following the rules for
     * every active move-affecting god
     * @param firstAction used when the move is the first action performed in the turn**/
    private State moveStateConstructor(boolean firstAction)
    {
        ArrayList<Box> workerPositions = new ArrayList<>();
        //Get worker cells and move boxes
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition());
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition());

        ArrayList<Box> possibleMovesWorker0 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(0)));
        ArrayList<Box> possibleMovesWorker1 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(1)));

        //Define god flags to build state
        boolean pushWorker, swapWorker, heraActive;
        pushWorker = false;
        swapWorker = false;
        heraActive = false;
        if(activeGods.contains(GodsList.MINOTAUR)) pushWorker = true;
        if(activeGods.contains(GodsList.APOLLO)) swapWorker = true;
        if(activeGods.contains(GodsList.HERA)) heraActive = true;

        if(activeGods.contains(GodsList.ARTEMIS) && !artemisFirstMove)
        {
            if(possibleMovesWorker0.isEmpty() && possibleMovesWorker1.isEmpty())
            {
                //Since this move is the second one done by the player it's not mandatory
                //If no moves are possible the state returned is going to be FirstCheckWinConditionState
                return new CheckWinConditionState(1, contextModel);
            }
        }

        return new MoveState(possibleMovesWorker0, possibleMovesWorker1, pushWorker, swapWorker, heraActive, contextModel,firstAction);
    }

    /**@return a State constructed following the rules of
     * each active build-affecting god**/
    private State buildStateConstructor(boolean firstAction)
    {
        ArrayList<Box> workerPositions = new ArrayList<>();
        //Get worker cells and build boxes
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition());
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition());

        ArrayList<Box> possibleBuildList0 = getPossibleBuildBoxes(workerPositions.get(0));
        ArrayList<Box> possibleBuildList1 = getPossibleBuildBoxes(workerPositions.get(1));
        //Initialize god flags
        boolean domeAtAnyLevel = false;
        boolean twoBlocksInOneBuild = false;
        if(activeGods.contains(GodsList.ATLAS)) domeAtAnyLevel = true;
        if(activeGods.contains(GodsList.HEPHAESTUS)) twoBlocksInOneBuild = true;
        if(activeGods.contains(GodsList.DEMETER) && !demeterFirstBuild)
        {
            if(possibleBuildList0.isEmpty() && possibleBuildList1.isEmpty())
            {
                //Since this build is the second one done by the player it's not mandatory
                //If no builds are possible the state returned is going to be SecondCheckWinConditionState
                return new CheckWinConditionState(2, contextModel);
            }
        }

        if(activeGods.contains(GodsList.PROMETHEUS))
        {
            if(prometheusFirstBuild)
            {
                //Saves the possible moves of both workers
                ArrayList<Box> possiblePrometheusMoves0 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(0)));
                ArrayList<Box> possiblePrometheusMoves1 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(1)));

                //Since it's prometheus first build the player can't trap himself
                //Checks if after the build the player can move, if not it eliminates the build cell
                //If after the elimination of said build cells no cell is left or no move cell
                //was present from the beginning
                //I deactivate prometheus and proceed with a default turn

                if(possiblePrometheusMoves0.size() == 1)
                {
                    //By building I could remove my only possible move if cellHeight + 1 > workerCellHeight
                    if(possiblePrometheusMoves0.get(0).getTower().getHeight() + 1 > workerPositions.get(0).getTower().getHeight())
                    {
                        possibleBuildList0.remove(possiblePrometheusMoves0.get(0));
                    }
                }

                if(possiblePrometheusMoves1.size() == 1)
                {
                    //By building I could remove my only possible move if cellHeight + 1 > workerCellHeight
                    if(possiblePrometheusMoves1.get(0).getTower().getHeight() + 1 > workerPositions.get(1).getTower().getHeight())
                    {
                        possibleBuildList1.remove(possiblePrometheusMoves1.get(0));
                    }
                }

                //No build/move cells are left after the check, prometheus is deactivated
                if((possibleBuildList0.isEmpty() && possibleBuildList1.isEmpty())
                        || (possiblePrometheusMoves0.isEmpty() && possiblePrometheusMoves1.isEmpty()))
                {
                    activeGods.remove(GodsList.PROMETHEUS);
                    contextModel.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), contextModel.getTurn().getCurrentPlayer()));
                    return moveStateConstructor(true);
                }

            }
            else
                {
                    if(possibleBuildList0.isEmpty() && possibleBuildList1.isEmpty())
                    {
                        //Since this build is the second one done by the player it's not mandatory
                        //If no builds are possible the state returned is going to be SecondCheckWinConditionState
                        return new CheckWinConditionState(2, contextModel);
                    }
                }
        }

        if(activeGods.contains(GodsList.ZEUS))
        {
            //possibleBuildList empties if the worker can't build under itself
            //Zeus chooses only god effect cards
            if(workerPositions.get(0).getTower().getHeight()<=2&& !workerPositions.get(0).getTower().getPieces().contains(Block.DOME))
                possibleBuildList0.add(workerPositions.get(0));
            else
                possibleBuildList0=new ArrayList<>();
            if(workerPositions.get(1).getTower().getHeight()<=2&& !workerPositions.get(1).getTower().getPieces().contains(Block.DOME))
                possibleBuildList1.add(workerPositions.get(1));
            else
                possibleBuildList1=new ArrayList<>();
        }

        if(hestiaSecondBuild)
        {
            ArrayList<Box> temp1=getPossibleBuildBoxes(workerPositions.get(0));
            possibleBuildList0=new ArrayList<>();
            for (Box b:temp1) {
                if(!b.isBorder())
                    possibleBuildList0.add(b);
            }
            temp1=getPossibleBuildBoxes(workerPositions.get(1));
            possibleBuildList1=new ArrayList<>();
            for (Box b:temp1) {
                if(!b.isBorder())
                    possibleBuildList1.add(b);
            }
        }
        return new BuildState(possibleBuildList0,possibleBuildList1,domeAtAnyLevel, twoBlocksInOneBuild, contextModel,firstAction);
    }


    /**
     * @param currentCell the building worker's cell
     * @return  a List of all the possible Build boxes surrounding
     * the active player selected worker
     * taking in consideration active build-affecting gods**/
    private ArrayList<Box> getPossibleBuildBoxes(Box currentCell)

    {
        ArrayList<Box> possibleBuildBoxes = (ArrayList<Box>)contextModel.getTurn().getPossibleBuildLocations(currentCell);

        //Check god cards to first extend then contract list of boxes
        if(activeGods.contains(GodsList.HEPHAESTUS))
        {
            possibleBuildBoxes = hephaestusEffect(possibleBuildBoxes, currentCell);
        }

        if (activeGods.contains(GodsList.DEMETER) && !demeterFirstBuild)
        {
            //Eliminates from the list of possible builds the last cell where the Player built
            possibleBuildBoxes = demeterBoxEffect(possibleBuildBoxes);
        }

        return possibleBuildBoxes;
    }


    //______________________________________________GOD EFFECTS METHODS_______________________________________________

/**@param basicMoves basic list of move boxes (without god effects applied)
 * @param b box where the player's worker is at
 * @return list of possible move boxes including Apollo special effect moves**/
    private ArrayList<Box> apolloEffect(ArrayList<Box> basicMoves, Box b)
    {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        
        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if(cell.isOccupied() && cell.isReachable(b) && (!cell.getTower().getPieces().contains(Block.DOME)))
            {
                godMoves.add(cell);
            }
        }

        return godMoves;
    }

    /**This method removes boxes which do not meet Athenas god power
     * @param basicMoves basic list of move boxes (without god effects applied)
     * @param b box where the player's worker is at
     * @return list of boxes where a move is permitted*/
    //REQUIRES: athenaCondition == true on the player who activated the card
    private ArrayList<Box> athenaEffect(ArrayList<Box> basicMoves, Box b)
    {
        //Prevents the player from moving up
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        
        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if(b.getTower().getHeight() < cell.getTower().getHeight())
            {
                godMoves.remove(cell);
            }
        }

        return godMoves;
    }

    /**The method defines state changes during a turn in which
     * the god card Artemis is played, overruling the
     * default stateChange() method**/
    private void artemisTurnFlow()
    {
        State newState;
        switch(currentState.getID())
        {

            case ActivationGod:
                //This move MUST be possible
                //The flag is used by moveStateConstructor
                artemisFirstMove = true;
                newState = moveStateConstructor(true);
                switchState(newState);
                break;

            case Move:
                    newState = new CheckWinConditionState(1, contextModel);
                    switchState(newState);
                    break;

            case FirstCheckWinCondition:
                if(!contextModel.getTurn().getCurrentPlayer().getHasLost())
                {
                    if(artemisFirstMove)
                    {
                        artemisFirstMove = false;
                        newState = moveStateConstructor(false);
                    }
                    else
                    {
                        newState = buildStateConstructor(false);
                    }
                }
                else
                    newState=new EndTurnState(contextModel);

                switchState(newState);
                break;

            case Build:
                    newState = new CheckWinConditionState(2, contextModel);
                    switchState(newState);
                    break;

            case SecondCheckWinCondition:
                newState = new EndTurnState(contextModel);
                switchState(newState);
                break;

            case EndTurn:
                newState = new BeginTurnState(contextModel);
                activeGods.clear();
                switchState(newState);
                break;
        }
    }

    /**The method removes the box in which the Player moved during his first Artemis move
     * and returns the new list of possible move boxes
     * @param basicMoves basic list of move boxes (without god effects applied)*/

    private ArrayList<Box> artemisBoxEffect(ArrayList<Box> basicMoves)
    {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        godMoves.remove(contextModel.getTurn().getCurrentPlayer().getLastMoveBox());
        return godMoves;
    }

    /**The method defines state changes during a turn in which
     * the god card Demeter is played, overruling the
     * default stateChange() method*/
    private void demeterTurnFlow()
    {
        State newState;
        switch(currentState.getID())
        {

            case ActivationGod:
                //This build is possible, it has been checked in ActivationGodState
                newState = moveStateConstructor(true);
                switchState(newState);
                break;

            case Move:
                newState = new CheckWinConditionState(1, contextModel);
                switchState(newState);
                break;

            case FirstCheckWinCondition:
                if(!contextModel.getTurn().getCurrentPlayer().getHasLost())
                {
                    demeterFirstBuild = true;
                    newState = buildStateConstructor(false);
                }
                else
                    newState=new EndTurnState(contextModel);

                switchState(newState);
                break;

            case Build:
                    newState = new CheckWinConditionState(2, contextModel);
                    switchState(newState);
                    break;

            case SecondCheckWinCondition:
                if(demeterFirstBuild)
                {
                    demeterFirstBuild = false;
                    newState = buildStateConstructor(false);

                }
                else
                    {
                        newState = new EndTurnState(contextModel);
                    }
                switchState(newState);
                break;

            case EndTurn:
                newState = new BeginTurnState(contextModel);
                activeGods.clear();
                switchState(newState);
                break;
        }
    }

    /**The method removes the box in which the Player built during his first Demeter build
     * @param basicBuilds basic list of build boxes (without god effects applied)
     * @return the new list of possible build boxes*/
    private ArrayList<Box> demeterBoxEffect(ArrayList<Box> basicBuilds)
    {
        //returns the list without the last box where actingPlayer built
        ArrayList<Box> godBuilds = new ArrayList<>(basicBuilds);
        godBuilds.remove(contextModel.getTurn().getCurrentPlayer().getLastBuildBox());
        return godBuilds;
    }


    /**The method remove boxes which cannot have two more blocks built on them
     * @param basicMoves basic list of move boxes (without god effects applied)
     * @param b box where the player's worker is at
     * @return list of possible build boxes*/
    private ArrayList<Box> hephaestusEffect(ArrayList<Box> basicMoves, Box b) {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);

        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if (cell.isOccupied() || (cell.getTower().getHeight() > 1))
            {
                godMoves.remove(cell);
            }

        }
        return godMoves;
    }

    /**@param basicMoves basic list of move boxes (without god effects applied)
     * @param playerWorkerBox selected worker Box
     * @return a list of boxes containing also special move Boxes permitted by Minotaur**/
    private ArrayList<Box> minotaurEffect(ArrayList<Box> basicMoves, Box playerWorkerBox)
    {
        ArrayList<Box> godMoves = new ArrayList<>();
        int x1,x2,y1,y2;
        int xBehind, yBehind;

        x2 = playerWorkerBox.getCoord()[0];
        y2 = playerWorkerBox.getCoord()[1];

        for (Box opponentWorkerBox : contextModel.getTurn().getBoardInstance().getBorderBoxes(playerWorkerBox)) {
            //Check if the cell is occupied by a worker and is reachable
            if(opponentWorkerBox.isOccupied() && playerWorkerBox.isReachable(opponentWorkerBox)
                    && (!opponentWorkerBox.getTower().getPieces().contains(Block.DOME)))
            {

                x1 = opponentWorkerBox.getCoord()[0];
                y1 = opponentWorkerBox.getCoord()[1];
                xBehind = x1 + (x1-x2);
                yBehind = y1 + (y1-y2);
                //Checks if the worker can be pushed back
                try
                {
                    Box behindBox = contextModel.getTurn().getBoardInstance().getBox(xBehind, yBehind);

                    if(!behindBox.isOccupied())
                    {
                        //the box behind opponent's worker is free
                        godMoves.add(opponentWorkerBox);
                    }
                }
                catch(IndexOutOfBoundsException ex)
                {
                    contextModel.notify(new MessageToVirtualView(new GodNotActionableErrorMessage(), contextModel.getTurn().getCurrentPlayer()));
                    System.out.println("Worker is at the edge of the board, cannot use Minotaur!\n" + ex.getMessage());
                }
            }
        }

        return godMoves;
    }

    /**
     * @param basicMoves  contains the list of basic move boxes to modify
     * @return list of move boxes higher than the player**/
    private ArrayList<Box> persephoneEffect(ArrayList<Box> basicMoves, Box workerBox)
    {
        ArrayList<Box> persephoneMoves = new ArrayList<>();
        for(Box b : basicMoves)
        {
            if(b.getTower().getHeight() > workerBox.getTower().getHeight())
            {
                persephoneMoves.add(b);
            }
        }

        return persephoneMoves;
    }

    /**@param basicMoves contains the list of basic move boxes to modify
     * @return list of move boxes on the same level as the worker**/
    private ArrayList<Box> prometheusMoveEffect(ArrayList<Box> basicMoves, Box workerBox)
    {
        ArrayList<Box> prometheusMoves = new ArrayList<>(basicMoves);
        for(Box b : contextModel.getTurn().getBoardInstance().getBorderBoxes(workerBox))
        {
            //If the cell is higher than the worker cell I remove it from possible moves
            if(workerBox.getTower() != null
                    && b.getTower().getHeight() > workerBox.getTower().getHeight())
            {
                prometheusMoves.remove(b);
            }
        }

        return prometheusMoves;
    }

    /**The method defines state changes during a turn in which
     * the god card Prometheus is played, overruling the
     * default stateChange() method**/
    private void prometheusTurnFlow()
    {
        State newState;
        switch(currentState.getID())
        {

        case ActivationGod:
            //This build is possible, it has been checked in ActivationGodState
        newState = buildStateConstructor(true);
        prometheusFirstBuild = true;
        switchState(newState);
        break;

        case Build:
            if(prometheusFirstBuild)
            {
                //The move MUST be possible otherwise the player looses
                newState = moveStateConstructor(false);
                switchState(newState);
                prometheusFirstBuild = false;
            }
            else
                {
                    newState = new CheckWinConditionState(2, contextModel);
                    switchState(newState);
                }
            break;

        case Move:
        newState = buildStateConstructor(false);
        switchState(newState);
        break;

        case SecondCheckWinCondition:
        newState = new EndTurnState(contextModel);
        switchState(newState);
        break;

        case EndTurn:
        newState = new BeginTurnState(contextModel);
        activeGods.clear();
        switchState(newState);
        break;
        }
    }

    /**The method defines state changes during a turn in which
     * the god card Hestia is played, overruling the
     * default stateChange() method**/
    private void hestiaTurnFlow() {
        State newState;
        switch (currentState.getID()) {
            case ActivationGod:
                newState = moveStateConstructor(true);
                switchState(newState);
                break;

            case Move:
                newState = new CheckWinConditionState(1, contextModel);
                switchState(newState);
                break;

            case FirstCheckWinCondition:
                if(!contextModel.getTurn().getCurrentPlayer().getHasLost())
                {
                    hestiaSecondBuild = false;
                    newState = buildStateConstructor(false);
                }
                else
                    newState=new EndTurnState(contextModel);

                switchState(newState);
                break;

            case Build:
                newState = new CheckWinConditionState(2, contextModel);
                switchState(newState);
                break;

            case SecondCheckWinCondition:
                if(!hestiaSecondBuild)
                {
                    hestiaSecondBuild = true;
                    newState = buildStateConstructor(false);
                }
                else
                {
                    hestiaSecondBuild=false;
                    newState = new EndTurnState(contextModel);
                }
                switchState(newState);
                break;

            case EndTurn:
                newState = new BeginTurnState(contextModel);
                activeGods.clear();
                switchState(newState);
                break;
        }
    }

    public void update(Choice userChoice) throws NullPointerException
    {
        if(userChoice == null)
        {
            contextModel.notify(new MessageToVirtualView(new SentChoiceError()));
            throw new NullPointerException("Null pointer on model or userChoice!");
        }
        /*Player actingPlayer;
        ArrayList<Player> playerList = (ArrayList<Player>) contextModel.getTurn().getPlayersList();
        actingPlayer = playerList.get(userChoice.getId());*/


        //Handle choice errors
        try
        {
            currentState.update(userChoice, contextModel);
        }
        catch(WrongChoiceException ex)
        {

            contextModel.notify(new MessageToVirtualView(new SentChoiceError()));
            System.out.println(ex.getMessage());
        }
        catch (MoveErrorException | BoxAlreadyOccupiedException | BuildErrorException |GodConditionNotSatisfiedException ex) {
           //Da divire i rami catch così da mandare errori diversi?
            contextModel.notify(new MessageToVirtualView(new SentChoiceError()));
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        //Check if the state has completed it's task, skips states that perform tasks only on startup method
        while(currentState.hasFinished())
        {
            /*try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            stateChange();
        }

/*        //Setto i flag in base a quali divinità sono state attivate, se è un GodActivationMessage
        if(userChoice instanceof GodActivationChoice)
        {
            //Prendo i giocatori, seleziono il giocatore corretto in base all'ID e guardo che carta God possiede
            //In funzione di quello aggiorno il model
            GodActivationChoice scelta;
            scelta = (GodActivationChoice)userChoice;
            if(scelta.godActive)
            {
                actingPlayer.setGodActive(true);
            }
            else
                {
                    //Non è necessario in quanto resettato alla fine di ogni turno
                    //Potrei decidere di sollevare un eccezione
                    actingPlayer.setGodActive(false);
                }
        }
        else if(userChoice instanceof MoveChoice)
        {
            MoveChoice scelta;
            scelta = (MoveChoice) userChoice;

        }
        else if(userChoice instanceof SelectWorkerCellChoice)
        {
            //Set worker status on model as active, deactivate other workers

        }

 */
    }
}
