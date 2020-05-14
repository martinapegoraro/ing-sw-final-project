package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;
import it.polimi.ingsw.View.Observer;

import java.util.ArrayList;

public class Context implements Observer<Choice> {
    private State currentState;
    private int numberofPlayers;
    private Model contextModel;
    private ArrayList<GodsList> activeGods;

    //Variables used to apply god effects
    private boolean prometheusFirstBuild;
    private boolean artemisFirstMove;
    private boolean demeterFirstBuild;
    private ArrayList<Box> oldWorkerPositions = new ArrayList<>();
    //Used by Artemis and Demeter to save the old player positions

    public Context(Model model) throws NullPointerException
    {
        if(model == null)
        {
            throw new NullPointerException("Model can't be null when linking Context!");
        }
        currentState = new SetUpState(model);
        numberofPlayers = 0;
        contextModel = model;
        activeGods = new ArrayList<>();
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
            }
            else if(activeGods.contains(GodsList.DEMETER))
            {
                demeterTurnFlow();
            }
            else if(activeGods.contains(GodsList.PROMETHEUS))
            {
                prometheusTurnFlow();
            }
        }

        State newState;

        switch(currentState.getID())
        {
            case SetUp:
                newState = new BeginTurnState();
                switchState(newState);

            case BeginTurn:
                newState = new ActivationGodState();
                switchState(newState);

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

                newState = moveStateConstructor();
                switchState(newState);

            case Move:
                newState = new CheckWinConditionState(1);
                switchState(newState);

            case FirstCheckWinCondition:
                newState = buildStateConstructor();
                switchState(newState);

            case Build:
                newState = new CheckWinConditionState(2);
                switchState(newState);

            case SecondCheckWinCondition:
                newState = new EndTurnState();
                switchState(newState);

            case EndTurn:
                newState = new BeginTurnState();
                activeGods.clear();
                switchState(newState);
        }
    }

    public void switchState(State next)
    {
        currentState = next;
    }



    //________________________________________MOVE/BUILD METHODS__________________________________________________

    /**Returns a List of all the possible Move boxes surrounding
     * the active player selected worker
     * taking in consideration active move-affecting gods**/
    private ArrayList<Box> getPossibleMoveBoxes(Box currentCell)
    {
        ArrayList<Box> possibleMoves = (ArrayList<Box>)contextModel.getTurn().getPossibleMoves(currentCell);

        //Check god cards to first extend then contract list of boxes
        //Gods activated by the active player
        if(activeGods.contains(GodsList.APOLLO))
        {
            possibleMoves = apolloEffect(possibleMoves, currentCell);
        }
        else if(activeGods.contains(GodsList.MINOTAUR))
        {
            possibleMoves = minotaurEffect(possibleMoves, currentCell);
        }

        if (activeGods.contains(GodsList.ARTEMIS) && !artemisFirstMove)
        {
            //Eliminates from the list of possible moves the last cell where the Player moved to
            possibleMoves = artemisBoxEffect(possibleMoves);
        }

        //Gods activated by another player
        if(activeGods.contains(GodsList.ATHENA))
        {
            possibleMoves = athenaEffect(possibleMoves, currentCell);
        }

        return possibleMoves;
    }

    /**Returns a State constructed following the rules for
     * every active move-affecting god**/
    private State moveStateConstructor()
    {
        ArrayList<Box> workerPositions = new ArrayList<>();
        //Get worker cells and move boxes
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition());
        workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition());

        ArrayList<Box> possibleMovesWorker0 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(0)));
        ArrayList<Box> possibleMovesWorker1 = new ArrayList<>(getPossibleMoveBoxes(workerPositions.get(1)));

        //Define god flags to build state
        boolean pushWorker, swapWorker;
        pushWorker = false;
        swapWorker = false;
        if(activeGods.contains(GodsList.MINOTAUR)) pushWorker = true;
        if(activeGods.contains(GodsList.APOLLO)) swapWorker = true;
        if(activeGods.contains(GodsList.ARTEMIS) && !artemisFirstMove)
        {
            if(possibleMovesWorker0.isEmpty() && possibleMovesWorker1.isEmpty())
            {
                //Since this move is the second one done by the player it's not mandatory
                //If no moves are possible the state returned is going to be FirstCheckWinConditionState
                return new CheckWinConditionState(1);
            }
        }

        return new MoveState(possibleMovesWorker0, possibleMovesWorker1, pushWorker, swapWorker, contextModel);
    }

    /**Returns a State constructed following the rules of
     * each active build-affecting god**/
    private State buildStateConstructor()
    {
        ArrayList<Box> workerPositions = new ArrayList<>();
        //Get worker cells and move boxes
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
                return new CheckWinConditionState(2);
            }
        }

        if(activeGods.contains(GodsList.PROMETHEUS) && !prometheusFirstBuild)
        {
            if(possibleBuildList0.isEmpty() && possibleBuildList1.isEmpty())
            {
                //Since this build is the second one done by the player it's not mandatory
                //If no builds are possible the state returned is going to be SecondCheckWinConditionState
                return new CheckWinConditionState(2);
            }
        }

        return new BuildState(possibleBuildList0,possibleBuildList1,domeAtAnyLevel, twoBlocksInOneBuild, contextModel);
    }


    /**Returns a List of all the possible Build boxes surrounding
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


    private ArrayList<Box> apolloEffect(ArrayList<Box> basicMoves, Box b)
    {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        
        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if(cell.isOccupied() && cell.isReachable(b))
            {
                //TODO: CONTROLLARE CONDIZIONE
                godMoves.add(cell);
            }
        }

        return godMoves;
    }

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
                newState = moveStateConstructor();
                switchState(newState);

            case Move:
                    newState = new CheckWinConditionState(1);
                    switchState(newState);

            case FirstCheckWinCondition:
                if(artemisFirstMove)
                {
                    artemisFirstMove = false;
                    newState = moveStateConstructor();
                }
                else
                    {
                        newState = buildStateConstructor();
                    }
                switchState(newState);

            case Build:
                    newState = new CheckWinConditionState(2);
                    switchState(newState);

            case SecondCheckWinCondition:
                newState = new EndTurnState();
                switchState(newState);

            case EndTurn:
                newState = new BeginTurnState();
                activeGods.clear();
                switchState(newState);
        }
    }

    /**The method removes the box in which the Player moved during his first Artemis move
     * and returns the new list of possible move boxes**/
    private ArrayList<Box> artemisBoxEffect(ArrayList<Box> basicMoves)
    {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        godMoves.remove(contextModel.getTurn().getCurrentPlayer().getLastMoveBox());
        return godMoves;
    }

    /**The method defines state changes during a turn in which
     * the god card Demeter is played, overruling the
     * default stateChange() method**/
    private void demeterTurnFlow()
    {
        State newState;
        switch(currentState.getID())
        {

            case ActivationGod:
                //This build is possible, it has been checked in ActivationGodState
                newState = moveStateConstructor();
                switchState(newState);

            case Move:
                newState = new CheckWinConditionState(1);
                switchState(newState);

            case FirstCheckWinCondition:
                demeterFirstBuild = true;
                newState = buildStateConstructor();
                switchState(newState);

            case Build:
                    newState = new CheckWinConditionState(2);
                    switchState(newState);


            case SecondCheckWinCondition:
                if(demeterFirstBuild)
                {
                    demeterFirstBuild = false;
                    newState = buildStateConstructor();

                }
                else
                    {
                        newState = new EndTurnState();
                    }
                switchState(newState);

            case EndTurn:
                newState = new BeginTurnState();
                activeGods.clear();
                switchState(newState);
        }
    }

    /**The method removes the box in which the Player built during his first Demeter build
     * and returns the new list of possible build boxes**/
    private ArrayList<Box> demeterBoxEffect(ArrayList<Box> basicBuilds)
    {
        //returns the list without the last box where actingPlayer built
        ArrayList<Box> godBuilds = new ArrayList<>(basicBuilds);
        godBuilds.remove(contextModel.getTurn().getCurrentPlayer().getLastBuildBox());
        return godBuilds;
    }


    private ArrayList<Box> hephaestusEffect(ArrayList<Box> basicMoves, Box b) {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);

        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if (cell.isOccupied() || (cell.getTower() != null && cell.getTower().getHeight() > 1))
            {
                godMoves.remove(cell);
            }

        }
        return godMoves;
    }

    private ArrayList<Box> minotaurEffect(ArrayList<Box> basicMoves, Box b)
    {
        ArrayList<Box> godMoves = new ArrayList<>(basicMoves);
        
        for (Box cell : contextModel.getTurn().getBoardInstance().getBorderBoxes(b)) {
            if(cell.isOccupied() && cell.isReachable(b))
            {
                godMoves.add(cell);
            }
        }

        return godMoves;
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
        newState = buildStateConstructor();
        prometheusFirstBuild = true;
        switchState(newState);

        case Build:
            if(prometheusFirstBuild)
            {
                //The move MUST be possible otherwise the player looses
                newState = moveStateConstructor();
                switchState(newState);
                prometheusFirstBuild = false;
            }
            else
                {
                    newState = new CheckWinConditionState(2);
                    switchState(newState);
                }

        case Move:
        newState = buildStateConstructor();
        switchState(newState);

        case SecondCheckWinCondition:
        newState = new EndTurnState();
        switchState(newState);

        case EndTurn:
        newState = new BeginTurnState();
        activeGods.clear();
        switchState(newState);
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

        //Check if the state has completed it's task
        if(currentState.hasFinished())
        {
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
        //TODO: Impostare l'active worker con la WorkerSelectChoice
    }
}
