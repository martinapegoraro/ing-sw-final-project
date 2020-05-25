package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;

public class View extends Observable<Choice> implements Observer<MessageToVirtualView> {

    private ViewState currentState;
    //TODO: create a GameWindow

    private GameWindow gameWindow;


    public View()
    {
        currentState=null;
        gameWindow=new GameWindow();
    }

    public void setGameWindowVisible()
    {
        gameWindow.visible();
    }

    /*public View(ModelRepresentation modelRep)
    {
        setView(state,modelRep);
    }*/

    /**
     * the setView is used to initialize the attributes of the class
     * I imagine that an instance of a view is present at the beginning of the game but for example the gameWindow is disabled and
     * not even
     */
    public void setView(ModelRepresentation modelRep)
    {
        currentState=ViewState.getState(modelRep.currentState.toString());
        gameWindow.updateGodsPanel(modelRep);
        gameWindow.updateInfoPanel(modelRep);
        setGameWindowVisible();
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
