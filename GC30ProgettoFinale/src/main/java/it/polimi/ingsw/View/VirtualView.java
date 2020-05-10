package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.Utils.Choice;

public class VirtualView extends Observable<Choice> implements Observer<MessageToVirtualView>{
    private int idPlayer;
    //?
    private SocketClientConnection connection;

    public VirtualView(int idPlayer,SocketClientConnection connection)
    {
        this.idPlayer=idPlayer;
        this.connection=connection;
    }

    public void notify(Choice click)
    {
        super.notify(click);
    }

    @Override
    public void update(MessageToVirtualView message) {
        connection.asyncSend(message);
    }
}
