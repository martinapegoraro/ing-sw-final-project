package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;
import java.util.ArrayList;

public class Context {
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
        currentState = new SetUpState(numberofPlayers, model);
        numberofPlayers = 0;
        contextModel = model;
        activeGods = new ArrayList<GodsList>();
    }


    //______________________________________State change methods_______________________________________________________




    private void stateChange()
    {
        State newState;
        switch(currentState.getID())
        {
            case 0:
                newState = new BeginTurnState();
                switchState(newState);
            case 1:
                newState = new ActivationGodState();
                switchState(newState);
            case 2:
                //Uso un metodo che calcola la lista di possibili mosse guardando quali divinità sono attive e
                //chiamando al getPossibleMoves del Model
                //TODO: IMPLEMENTARE LOGICA CAMBIO STATO
                Box moveTo = contextModel.getTurn().getCurrentPlayer().getSelectedWorker().getPosition();
                newState = new MoveState(getPossibleMoveBoxes(moveTo), true, true);
                switchState(newState);
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
        }
    }

    public void switchState(State next)
    {
        currentState = next;
    }



    //__________________________________State Construction Methods__________________________________________________


    private ArrayList<Box> getPossibleMoveBoxes(Box currentCell)
    {
        //Devo prendere la cella dalla quale effettuo lo spostamento
        //Passarla alla getPossibleMoves() e poi aggiornare controllando quali god sono attivi
        ArrayList<Box> possibleMoves = (ArrayList<Box>)contextModel.getTurn().getPossibleMoves(currentCell);
        //TODO: SCEGLIERE IL FLOW DEL CONTEXT
        return null;
    }

    private ArrayList<Box> getPossibleBuildBoxes()

    {
        return null;
    }


    private void hephaestusEffect()
    {

    }

    public void update(Choice userChoice) throws NullPointerException
    {
        if(userChoice == null)
        {
            throw new NullPointerException("Null pointer on model or userChoice!");
        }
        Player actingPlayer;
        ArrayList<Player> playerList = (ArrayList<Player>) contextModel.getTurn().getPlayersList();
        actingPlayer = playerList.get(userChoice.getId());
        //TODO: Aggiungere controlli sui messaggi girati dai player

        currentState.update(userChoice, contextModel);

        //Saving the Gods list activated in the current turn to simplify methods, va messa
        for(Player player : playerList)
        {
            //TODO: CHECK FOR EACH
            if(player.isGodActive())
            {
                activeGods.add(player.getGod());
            }
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
