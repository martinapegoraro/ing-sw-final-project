package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.BuildErrorException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceTypeException;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.View.Observer;

import java.util.ArrayList;

public class Context implements Observer<Choice> {
    private State currentState;
    private int numberofPlayers;
    private Model contextModel;
    private ArrayList<GodsList> activeGods;

    public Context(Model model) throws NullPointerException
    {
        if(model == null)
        {
            throw new NullPointerException("Model can't be null when linking Context!");
        }
        currentState = new SetUpState(model);
        numberofPlayers = 0;
        contextModel = model;
        activeGods = new ArrayList<GodsList>();
    }


    //______________________________________State change methods_______________________________________________________



    private void stateChange()
    {
        State newState;
        //Saves
        ArrayList<Box> workerPositions = new ArrayList<>();

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
                newState = new MoveState(possibleMovesWorker0, possibleMovesWorker1, pushWorker, swapWorker, contextModel);
                switchState(newState);

            case CheckWinCondition:
            case Move:

                //Get worker cells and move boxes
                workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(0).getPosition());
                workerPositions.add(contextModel.getTurn().getCurrentPlayer().getWorkerList().get(1).getPosition());

                ArrayList<Box> possibleBuildList0 = getPossibleBuildBoxes(workerPositions.get(0));
                ArrayList<Box> possibleBuildList1 = getPossibleBuildBoxes(workerPositions.get(1));
                //Initialize god flags
                boolean domeAtAnyLevel = false;
                if(activeGods.contains(GodsList.ATLAS)) domeAtAnyLevel = true;

                newState = new BuildState(possibleBuildList0,possibleBuildList1,domeAtAnyLevel,contextModel);
                switchState(newState);

            case Build:
            case EndTurn:
                newState = new BeginTurnState();
                switchState(newState);
        }
    }

    public void switchState(State next)
    {
        currentState = next;
    }



    //________________________________________MOVE/BUILD METHODS__________________________________________________


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

        //Gods activated by another player
        if(activeGods.contains(GodsList.ATHENA))
        {
            possibleMoves = athenaEffect(possibleMoves, currentCell);
        }

        return possibleMoves;
    }

    private ArrayList<Box> getPossibleBuildBoxes(Box currentCell)

    {
        ArrayList<Box> possibleBuildBoxes = (ArrayList<Box>)contextModel.getTurn().getPossibleBuildLocations(currentCell);

        //Check god cards to first extend then contract list of boxes


        return possibleBuildBoxes;
    }


    //______________________________________________GOD EFFECTS METHODS_______________________________________________


    private ArrayList<Box> apolloEffect(ArrayList<Box> basicMoves, Box b)
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

    private void artemisEffect()
    {

    }

    private ArrayList<Box> atlasEffect(ArrayList<Box> basicBuildBoxes, Box b)
    {
        return null;
    }

    private void demeterEffect()
    {

    }

    private void hephaestusEffect()
    {

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

    public void update(Choice userChoice) throws NullPointerException
    {
        if(userChoice == null)
        {
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
        catch(WrongChoiceTypeException ex)
        {
            //TODO: Creare messaggi da resituire al Client
            System.out.println(ex.getMessage());
        }
        catch (MoveErrorException | BoxAlreadyOccupiedException | BuildErrorException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
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
