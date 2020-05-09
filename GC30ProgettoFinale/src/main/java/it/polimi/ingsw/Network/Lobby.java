package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.View.VirtualView;

import javax.crypto.MacSpi;
import java.util.*;

public class Lobby {

    private Map<SocketClientConnection,String> connectionMap;
    private Model model;
    private Controller controller;
    private VirtualView virtualView;
    private int numberOfPlayers;


    public Lobby(SocketClientConnection connection,String nome,int numberOfPlayers)
    {

        this.numberOfPlayers=numberOfPlayers;
        connectionMap=new HashMap<>();
        connectionMap.put(connection,nome);
        model=null;
        controller=null;
        virtualView=null;

    }

    public synchronized void  addPlayer(SocketClientConnection conn,String name)
    {
        connectionMap.put(conn, name);
        if(connectionMap.size()==numberOfPlayers)
            startGame();

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
        System.out.println("sono qui");
        model=new Model(nomiGiocatori);
        System.out.println("model creato");
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
        //vv1.addObserver(controller);
        virtualView=new VirtualView();
    }

    public void startGame()
    {
        System.out.println("starting the game");

        instantiateModel();
        System.out.println("sono qui2");
        createController();
        System.out.println("sono qui3");
        createVirtualView();
        //partenza gioco;
        //model.addObservers(virtualView);
        virtualView.addObservers(controller);
        for (SocketClientConnection c:connectionMap.keySet() ) {
            System.out.println("sono qui");
            c.asyncSend(new MessageToVirtualView(model.getModelRep()));
        }
    }

    public void print()
    {
        System.out.println(connectionMap.toString());
    }
}
