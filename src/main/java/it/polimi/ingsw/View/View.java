package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.GodChoice;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class View extends Observable<Choice> implements Observer<MessageToVirtualView> {

    private ViewState currentState;
    private WindowInterface currentWindow;
    private String playerName;
    private int idPlayer;


    public View()
    {
        currentState=null;
        currentWindow=null;
        playerName="";
        idPlayer=-1;
    }

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

    public void setPlayerName(String name)
    {
        playerName=name;
    }
    public void setIdPlayer(int id){
        idPlayer=id;
    }

    public int getIdPlayer()
    {
        return idPlayer;
    }

    public void updateWindow(MessageToVirtualView message)
    {
        if(message.getModelRep().currentState.toString().equals("Exit") )
        {
            currentWindow.setWindowNotVisible();
        }
        else if(message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods==null &&message.getModelRep().godList[0]==null)
        {
           setIdPlayer(Arrays.asList(message.getModelRep().playersName).indexOf(playerName));
           if(idPlayer==message.getModelRep().activePlayer)
           {
               currentWindow.setWindowNotVisible();
               currentWindow=new CardSelectionWindow(this,message.getModelRep().playerNum);
               currentWindow.setWindowVisible();
           }
           else
           {
               //((LobbyWindow)currentWindow).godSelectionPrompt(message.getModelRep().playersName[message.getModelRep().activePlayer]);
               currentWindow.setWindowNotVisible();
           }
        }
        else if(message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods!=null)
        {
            //currentWindow.setWindowNotVisible();
            if(idPlayer==message.getModelRep().activePlayer)
            {
                currentWindow.setWindowNotVisible();
                currentWindow=new GodSelectionWindow(this,idPlayer,message.getModelRep());
                currentWindow.setWindowVisible();
               // System.out.println("diventa visibile la scelta del singolo god");
                //roba di prova da togliere
                /*Choice c=new GodChoice(message.getModelRep().gods.get(idPlayer).toString());
                c.setId(idPlayer);
                notify(c);*/
            }
            else
            {
                //((CardSelectionWindow)currentWindow).godSelectionPrompt(message.getModelRep().playersName[message.getModelRep().activePlayer]);
                currentWindow.setWindowNotVisible();
            }
        }
        else if(message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods==null && message.getModelRep().godList[0]!=null)
        {
            currentWindow.setWindowNotVisible();
            currentWindow=new GameWindow();
            currentWindow.setWindowVisible();
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
     * given the  choice * @param c
     * the method checks if the choice we want to send to the server is compatible to the current state of the view
     * (it will be the same of the controller) this method allows us to stop wrong choices before we send them
     * @return true if the current state contains in his list of compatible choices the @param c choice otherwise it @return false
     */
    public boolean checkChoiceToSend(Choice c)
    {
        if(ViewState.getPossibleChoices(currentState).contains(c.toString()))
            return true;
        else
            return false;
    }

    /**
     * given @param newState
     * the method changes the attribute current state
     */
    public void setCurrentState(ViewState newState)
    {
        currentState=newState;
    }



}
