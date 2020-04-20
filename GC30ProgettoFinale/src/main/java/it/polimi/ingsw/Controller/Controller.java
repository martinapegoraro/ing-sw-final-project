package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.View.Observer;
import it.polimi.ingsw.View.VirtualView;

public class Controller implements Observer<Choice> {

    Model modelInstance;
    private VirtualView view;
    private int playerNum;
    String[] playerList;
    Context context;

    public Controller(Model model, int playerNumber) throws WrongNumberOfPlayersException
    {
        this.modelInstance = model;
        context = new Context();
        if(playerNumber < 2 || playerNumber > 3)
        {
            throw new WrongNumberOfPlayersException("Wrong number of players passed to Controller");
        }
        playerList = new String[playerNumber];
        playerNum = playerNumber;
    }

    public void addPlayer(String p, int ID)
    {

        try
        {
            modelInstance.getTurn().addPlayer(p); //TODO: ID here?
        }
        catch(ImpossibleAddAnotherPlayerException ex)
        {
            System.out.println(ex.getMessage());
            return;
        }

        //The player was correctly added
        playerNum++;


    }

    //Players can leave only before a game is started, if a match is in progress the Player entity will not be
    //deleted until the end (the Player inputs will be ignored)
    public void playerLeaves(String p)
    {
        //TODO: Method has to be implemented in Turn
        playerNum--;
    }

    /**public void playerNumSet(int n)
    {
        this.playerNum = n;
        this.playerList = new String[n];
    }
     Not needed if everything is done in AddPlayer
     **/

    //After beginGameChoice is received by update this method starts the game
    public void beginGame()
    {

    }

    private void endGame()
    {

    }

    public void update(Choice userChoice)
    {
        context.update(userChoice, modelInstance);
    }
}
