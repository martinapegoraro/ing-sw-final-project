package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.Utils.Choice;

public class VirtualView extends View {
    private int idPlayer;
    private SocketClientConnection connection;

    private class MessageReceiver extends Observable<Choice> implements Observer<Choice> {

        public void update(Choice c)
        {
            processChoice(c);
        }
    }

    public VirtualView(int idPlayer,SocketClientConnection connection)
    {
        this.idPlayer=idPlayer;
        this.connection=connection;
        connection.addObservers(new MessageReceiver());
    }

    public void notify(Choice click)
    {
        super.notify(click);
    }

    public SocketClientConnection getConnection()
    {
        return connection;
    }
    @Override
    public void update(MessageToVirtualView message) {
        connection.asyncSend(message);
        //if(!message.isModelRep() && message.getMessage().getMessage().equals("One player left the game"))
        //    connection.closeConnection();
    }
}
