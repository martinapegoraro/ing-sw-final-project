package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private List<SocketClientConnection> connectionList;
    private List<String> namesList;
    private Model model;
    private Controller controller;
    private List<VirtualView> virtualViewList;
    private int playerInTheLobby;

    public Lobby()
    {
        connectionList=new ArrayList<SocketClientConnection>();
        namesList=new ArrayList<String>();
        model=null;
        controller=null;
        virtualViewList=new ArrayList<VirtualView>();
        playerInTheLobby=0;
    }

    public synchronized void  addPlayer(SocketClientConnection conn,String name)
    {
        connectionList.add(playerInTheLobby,conn);
        namesList.add(playerInTheLobby,name);
        playerInTheLobby++;

    }

    public synchronized void removePlayer(String name)
    {
        connectionList.remove(namesList.indexOf(name));
        namesList.remove(name);
        playerInTheLobby--;
    }

    private  void instantiateModel()
    {
        model=new Model(namesList);
        //add the virtualView to the model observer
        //model.addObservers();
    }

    private void createController()
    {
        try {
            controller=new Controller(model,playerInTheLobby);
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

}
