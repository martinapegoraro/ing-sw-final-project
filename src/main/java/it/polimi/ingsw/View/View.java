package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Exceptions.NotExistingGodException;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.GodChoice;
import it.polimi.ingsw.Utils.InitialPlayerChoice;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * the view class handles the client-side of the application
 * received a messageToVirtualView establish what the player
 * is supposed to see handling the gui windows
 */
public class View extends Observable<Choice> implements Observer<MessageToVirtualView> {

    private ViewState currentState;
    private WindowInterface currentWindow;
    private String playerName;
    private int idPlayer;
    private boolean chosenFirst=false;
    public View()
    {
        currentState=null;
        currentWindow=null;
        playerName="";
        idPlayer=-1;

    }

    /**
     * the constructor of this class sets the current
     * state and shows the lobby window where the client
     * can put his username and how many people he wants to play with
     *
     */
    public View(ViewState state)
    {
        currentState=state;
        playerName="";
        idPlayer=-1;
        try {
            currentWindow=new LobbyWindow(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.setWindowVisible();
    }

    /**
     * @return the current state of the client
     */
    public ViewState getCurrentState() {
        return currentState;
    }

    /**
     * this method is used in the VirtualView to send the given
     * @param choice
     * to the controller
     */
    protected void processChoice(Choice choice)
    {
        notify(choice);
    }

    /**in this method we change the GameWindow given the new modelRepresentation or show the ErrorMessage
     * sent by the server using the Message to virtualView
     * @param message
     */
    @Override
    public void update(MessageToVirtualView message) {

    }

    /**
     * given the
     * @param name
     * sets the player name attribute
     */
    public void setPlayerName(String name)
    {
        playerName=name;
    }

    /**
     * given the id of the player sets the player ID
     */
    public void setIdPlayer(int id){
        idPlayer=id;
    }

    /**
     * @return the IdPlayer
     */
    public int getIdPlayer()
    {
        return idPlayer;
    }

    /**
     * given a messageToVirtualView as
     * @param message
     * modifies the current window
     */
    public void updateWindow(MessageToVirtualView message)
    {
        if(!message.isModelRep())
        {
            //if(message.getPlayerName()==null ||playerName.equals(message.getPlayerName()))
                currentWindow.updateWindow(message);
        }
        else {
            if (message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods == null && message.getModelRep().godList[0] == null) {
                setIdPlayer(Arrays.asList(message.getModelRep().playersName).indexOf(playerName));
                if (idPlayer == message.getModelRep().activePlayer) {
                    currentWindow.setWindowNotVisible();
                    currentWindow = new CardSelectionWindow(this, message.getModelRep().playerNum);
                    currentWindow.setWindowVisible();
                } else {
                    currentWindow.messagePrompt(message.getModelRep().playersName[message.getModelRep().activePlayer]);
                    //currentWindow.setWindowNotVisible();
                }
            } else if (message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods != null) {
                //currentWindow.setWindowNotVisible();
                if (idPlayer == message.getModelRep().activePlayer) {
                    currentWindow.setWindowNotVisible();
                    currentWindow = new GodSelectionWindow(this, idPlayer, message.getModelRep());
                    currentWindow.setWindowVisible();

                } else {
                    currentWindow.messagePrompt(message.getModelRep().playersName[message.getModelRep().activePlayer]);
                    //currentWindow.setWindowNotVisible();
                }
            } else if (message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods == null && message.getModelRep().godList[0] != null) {
                currentWindow.setWindowNotVisible();
                ArrayList<GodsList> list = new ArrayList<GodsList>();
                for (String s : message.getModelRep().godList) {
                    try {
                        list.add(GodsList.getGod(s));
                    } catch (NotExistingGodException e) {
                        e.printStackTrace();
                    }
                }
                currentWindow = new GameWindow(message.getModelRep().playersName, list, idPlayer, this);
                currentWindow.setWindowVisible();
                currentWindow.updateWindow(message);

                if(!chosenFirst &&idPlayer==0)
                {
                   while(!chosenFirst) {
                       int first = Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "chose the first player", "Santorini", JOptionPane.PLAIN_MESSAGE, null, null, "").toString());
                       if (first >= 0 && first<=message.getModelRep().playerNum-1) {
                           Choice c = new InitialPlayerChoice(first);
                           chosenFirst = true;
                           notify(c);
                       }
                       else
                       {
                           JOptionPane.showMessageDialog((GameWindow)currentWindow,"there is no player"+first +"\n you have to insert a number between 0 and "+(message.getModelRep().playerNum-1));
                       }
                   }

                }
            } else {
                //if(message.getPlayerName()==null || playerName.equals(message.getPlayerName()))
                    currentWindow.updateWindow(message);
            }
        }
    }

    /**
     * the following update sends the
     * @param c
     *to the client so it can sends it to the server
     */
    public void update(Choice c)
    {
        //check choice to send
        c.setId(idPlayer);
        notify(c);
    }

    /**
     * given the  choice
     * @param c
     * the method checks if the choice we want to send to the server is compatible to the current state of the view
     * (it will be the same of the controller) this method allows us to stop wrong choices before we send them
     * @return true if the current state contains in his list of compatible choices the @param c choice otherwise it
     * @return false
     */
    public boolean checkChoiceToSend(Choice c)
    {
        if(ViewState.getPossibleChoices(currentState).contains(c.toString()))
            return true;
        else
            return false;
    }

    /**
     * given
     * @param newState
     * the method changes the attribute current state
     */
    public void setCurrentState(ViewState newState)
    {
        currentState=newState;
    }



}
