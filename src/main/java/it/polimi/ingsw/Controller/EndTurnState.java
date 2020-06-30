package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

import java.util.List;

/**
 * The EndTurnState is used at the end of each turn.
 * it resets some of the variables which are changed
 * every turn
 */

public class EndTurnState implements State {
    private StateEnum stateID;
    private boolean hasFinished;

    /**
     * initializes the state
     */

    public EndTurnState(Model model)
    {

        stateID = StateEnum.EndTurn;
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
    public StateEnum getID() {
        return stateID;
    }

    /**
     * Called during initialization,
     * it resets the selected workers and
     * the active gods for each player
     */

    @Override
    public void startup(Model model)
    {
        Player currentPlayer;
        currentPlayer = model.getTurn().getCurrentPlayer();
        //currentPlayer.setPlayerActive(false); This breaks SetNextPlayer, setPlayerActive should not be accessed manually
        // HAS TO BE SET IN BEGINTURNSTATE! currentPlayer.changeAthenaCondition(false);
        //Otherwise some players may never be able to use Athena
        currentPlayer.setSelectedWorker(null);
        //currentPlayer.changePanCondition(false); This should not be necessary
        List<Player> players = model.getTurn().getPlayersList();
        for (Player player : players)
        {
            player.setGodActive(false);
        }

        model.getTurn().setNextPlayer();
        model.updateModelRep(stateID);
        hasFinished = true;
    }

    /**
     * it sends a ChoiceError if a choice is received during this state
     */


    @Override
    public void update(Choice userChoice, Model model)
    {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in CheckWinConditionState! RECEIVED: " + userChoice.toString());
    }

    /**
     * @return the boolean variable that is used to check if the state has finished
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}
