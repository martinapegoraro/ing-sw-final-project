package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Network.SocketClientConnection;
import it.polimi.ingsw.View.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        connectionMap.put(conn, name);
        if(connectionMap.size()==numberOfPlayers)
            startGame();

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
        model=new Model((ArrayList<String>)connectionMap.values());
        //add the virtualView to the model observer
        //model.addObservers();
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
    }

    public void startGame()
    {
        instantiateModel();
        createController();
        //createVirtualView();
        //partenza gioco;
    }

    public void print()
    {
        System.out.println(connectionMap.toString());
    }
}
