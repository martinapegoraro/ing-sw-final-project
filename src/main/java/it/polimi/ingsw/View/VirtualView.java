package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.Utils.Choice;

/**
 * the virtual view class is used in the server-side of the application
 * it receives the choice from the client and sends them to the controller and takes the messageToVirtualView from the model and sends to the clients
 * in this project the virtual view is only the receiver and sender of messages the checks and elaboration methods are implemented in the controller (server-side)
 * or the view (client-side)
 * */
public class VirtualView extends View {
    private int idPlayer;
    private SocketClientConnection connection;

    /**
     * the private class message receiver is used to submit to the controller the choices
     */
    private class MessageReceiver extends Observable<Choice> implements Observer<Choice> {

        public void update(Choice c)
        {
            processChoice(c);
        }
    }


    /**
     * the constructor of the VirtualView builds the object and sets
     * the Message receiver private class as observer of the connection

     */
    public VirtualView(int idPlayer,SocketClientConnection connection)
    {
        this.idPlayer=idPlayer;
        this.connection=connection;
        connection.addObservers(new MessageReceiver());
    }

    /**
     * the notify sends to the controller the choice received
     */
    public void notify(Choice click)
    {
        super.notify(click);
    }

    /**
     * @return the connection
     */
    public SocketClientConnection getConnection()
    {
        return connection;
    }

    /**
     * this update method sends through the connection the message to the view
     */
    @Override
    public void update(MessageToVirtualView message) {
        connection.send(message);
        //if(!message.isModelRep() && message.getMessage().getMessage().equals("One player left the game"))
        //    connection.closeConnection();
    }
}
