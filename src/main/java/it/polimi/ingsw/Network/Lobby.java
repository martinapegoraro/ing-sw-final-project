package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.Utils.ErrorMessages.PlayerNameErrorMessage;
import it.polimi.ingsw.View.VirtualView;

import javax.crypto.MacSpi;
import java.util.*;

public class Lobby {

    private Map<SocketClientConnection,String> connectionMap;
    private Model model;
    private Controller controller;
    private List<VirtualView> virtualViewList;
    private int numberOfPlayers;


    public Lobby(SocketClientConnection connection,String nome,int numberOfPlayers)
    {

        this.numberOfPlayers=numberOfPlayers;
        connectionMap=new HashMap<>();
        connectionMap.put(connection,nome);
        model=null;
        controller=null;
        virtualViewList=new ArrayList<VirtualView>();

    }

    public synchronized void  addPlayer(SocketClientConnection conn,String name)
    {
        if(!connectionMap.containsValue(name)) {
            connectionMap.put(conn, name);
            if (connectionMap.size() == numberOfPlayers)
                startGame();
        }
        else
        {
            conn.asyncSend(new MessageToVirtualView(new PlayerNameErrorMessage()));
        }

    }
    public int getNumberInTheLobby()
    {
        return connectionMap.size();
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    public synchronized boolean isInInTheLobby(SocketClientConnection conn)
    {
        return connectionMap.get(conn) != null;
    }

    public synchronized void removePlayer(SocketClientConnection connection)
    {
        connectionMap.remove(connection);
    }

    private  void instantiateModel()
    {

        List<String> nomiGiocatori=new ArrayList<String>(connectionMap.values());
        model=new Model(nomiGiocatori);
    }

    private void createController()
    {
        try {
            controller=new Controller(model,numberOfPlayers);
        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        }
    }

    //TODO:Gestione virtual view
    private void createVirtualView()
    {
        int i=0;
        for (SocketClientConnection connection:connectionMap.keySet()) {
            virtualViewList.add(new VirtualView(i,connection));
            i++;
        }
    }

    public void startGame()
    {
        System.out.println("starting the game");

        instantiateModel();
        createController();
        createVirtualView();

        for (VirtualView v:virtualViewList) {
            model.addObservers(v);
            v.addObservers(controller);
        }

        int i=0;
        for (SocketClientConnection c:connectionMap.keySet() ) {
            c.asyncSend(new MessageToVirtualView(model.getModelRep()));
            //c.run();
            i++;
        }

    }

    public void print()
    {
        System.out.println(connectionMap.toString());
    }
}
