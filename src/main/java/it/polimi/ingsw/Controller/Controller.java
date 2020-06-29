package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.ExitErrorMessage;
import it.polimi.ingsw.Utils.GodActivationChoice;
import it.polimi.ingsw.View.Observable;
import it.polimi.ingsw.View.Observer;
import it.polimi.ingsw.View.VirtualView;

/**
 * The Controller links the model and the virtual view
 */

public class Controller implements Observer<Choice> {

    Model modelInstance;
    private VirtualView view;
    private int playerNum;
    String[] playerList;
    Context context;

    /**
     * the builder initializes the controller,
     * it receives the number of player chosen by the players
     * @param model
     * @param playerNumber
     * @throws WrongNumberOfPlayersException
     */

    public Controller(Model model, int playerNumber) throws WrongNumberOfPlayersException
    {
        this.modelInstance = model;
        //!!-- ho aggiunto modelInstance così riuscivo a compilare--!!
        context = new Context(modelInstance);
        if(playerNumber < 2 || playerNumber > 3)
        {
            throw new WrongNumberOfPlayersException("Wrong number of players passed to Controller");
        }
        playerList = new String[playerNumber];
        playerNum = playerNumber;
    }

    /*public void playerNumSet(int n)
    {
        this.playerNum = n;
        this.playerList = new String[n];
    }
     Not needed if everything is done in AddPlayer
     */



    /**
     * exits the game
     */

    private void endGame()
    {
        modelInstance.notify(new MessageToVirtualView(new ExitErrorMessage()));
    }

    /**
     * sends the valid choices to the context,
     * which controls the turn flow
     * @param userChoice
     */

    public synchronized void update(Choice userChoice) {
        if (userChoice.toString().equals("Exit")) {
            endGame();
        } else {
            //context.update(userChoice, modelInstance);
            Player actingPlayer = modelInstance.getTurn().getPlayer(userChoice.getId());

            //Check if the choice is valid, invalid choices are not passed to Context
            if (!actingPlayer.getHasLost()) {
                if (actingPlayer.isPlayerActive() || userChoice.toString().equals("GodActivationChoice")) {
                    //God choices are in sync for all the players
                    context.update(userChoice);
                } else {
                    System.out.println("Choice sent by inactive player, choice type: " + userChoice.toString());
                }
            } else {
                System.out.println("Choice sent by player who has lost, choice type: " + userChoice.toString());
            }
        }
    }
}
