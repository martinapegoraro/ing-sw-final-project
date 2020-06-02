package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;

import java.io.IOException;

public class View extends Observable<Choice> implements Observer<MessageToVirtualView> {

    private ViewState currentState;
    private WindowInterface currentWindow;


    public View()
    {
        currentState=null;
        currentWindow=null;
    }

    public View(ViewState state)
    {
        currentState=state;

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

    public void updateWindow(MessageToVirtualView message)
    {
        if(message.getModelRep().currentState.toString().equals("SetUp") && message.getModelRep().gods==null)
        {
           currentWindow.setWindowNotVisible();
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
