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

/**
 * The lobby class handles the creation of a game
 * every time a client register itself in the server he have tu put his name and the number of player whit he or she wants to play
 * the client using the number of players puts the client in a different lobby when the lobby is full the lobby class
 * starts the game: it creates the model,the controller and the virtual view and bound them together following the
 * mvc patter
 */
public class Lobby {

    private Map<SocketClientConnection,String> connectionMap;
    private Model model;
    private Controller controller;
    private List<VirtualView> virtualViewList;
    private int numberOfPlayers;


    /**
     * the builder of the class takes as parameter
     * @param connection
     * @param nome
     * @param numberOfPlayers
     *and puts them in a hasMap
     */
    public Lobby(SocketClientConnection connection,String nome,int numberOfPlayers)
    {

        this.numberOfPlayers=numberOfPlayers;
        connectionMap=new HashMap<>();
        connectionMap.put(connection,nome);
        model=null;
        controller=null;
        virtualViewList=new ArrayList<VirtualView>();

    }

    /**
     * this method puts in a lobby a new client
     * it checks if in the lobby there is no other players with the same name
     *
     * @param conn
     * @param name
     */
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

    /**
     * return the number of registered clients in the lobby at the moment
     * @return
     */
    public int getNumberInTheLobby()
    {
        return connectionMap.size();
    }

    /**
     * return the number of player supposed to be in the lobby in order to start the game.
     * @return
     */
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * given a SocketClientConnection as @param conn
     * @return true if the client who has that connection is in the lobby
     */
    public synchronized boolean isInInTheLobby(SocketClientConnection conn)
    {
        return connectionMap.get(conn) != null;
    }

    /**
     * given the  @param connection
     * removes the client from the lobby
     */
    public synchronized void removePlayer(SocketClientConnection connection)
    {
        connectionMap.remove(connection);
    }

    /**
     * creates an instance of the model
     *
     */
    private  void instantiateModel()
    {

        List<String> nomiGiocatori=new ArrayList<String>(connectionMap.values());
        model=new Model(nomiGiocatori);
    }

    /**
     * creates an instance of the controller
     *
     */
    private void createController()
    {
        try {
            controller=new Controller(model,numberOfPlayers);
        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates for every client in the lobby his instance of the VirtualView
     *
     */
    private void createVirtualView()
    {
        int i=0;
        for (SocketClientConnection connection:connectionMap.keySet()) {
            virtualViewList.add(new VirtualView(i,connection));
            i++;
        }
    }

    /**
     * this method creates model view controller and connects them one another
     * following the MVC pattern
     */
    public void startGame()
    {


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
