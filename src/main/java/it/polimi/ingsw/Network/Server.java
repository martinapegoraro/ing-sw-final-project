package it.polimi.ingsw.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {
    private static final int PORT=12345;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private List<SocketClientConnection> connections;
    private List<Lobby> lobbiesList;
    private boolean gameHasStarted;


    /**
     * the server builder creates an instance of the serverSocket
     * @throws IOException
     */
    public Server() throws IOException
    {
        this.serverSocket=new ServerSocket(PORT);
        executor= Executors.newFixedThreadPool(128);
        connections=new ArrayList<SocketClientConnection>();
        lobbiesList=null;
    }

    /**
     * given a socketClientConnection this method adds it to the connections list
     * @param c
     */
    private synchronized void registerConnection(SocketClientConnection c)
    {
        connections.add(c);
    }

    /**
     * this method accepts connections
     */
    public void run(){
        while(true){
            try {
                if(!gameHasStarted)
                {
                    //this flag could possibly generate some synchronization problems
                    Socket socket=serverSocket.accept();
                    SocketClientConnection connection=new SocketClientConnection(socket,this);

                    registerConnection(connection);
                    executor.submit(connection);
                }
                else
                    {
                        for(SocketClientConnection c: lobbiesList.get(0).getConnections())
                        {
                            //If a client who was playing has disconnected the server will accept new connections
                            if(!c.isActive())
                            {
                                flushLobbies();
                            }
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * once a client is connected to the sever it sends the player name and the number of players ad it is put in the correct
     * Lobby
     * @param connection
     * @param name
     * @param numberOfPlayer
     */
    public void addToLobby(SocketClientConnection connection,String name,int numberOfPlayer)
    {
        boolean fullLobby = false;
        boolean foundLobby = false;
        if(lobbiesList==null){
            lobbiesList=new ArrayList<Lobby>();
            lobbiesList.add(new Lobby(connection,name,numberOfPlayer));
        }
        else
        {
            for (Lobby l:lobbiesList) {
                if(numberOfPlayer==l.getNumberOfPlayers()) {
                    foundLobby = true;
                    l.addPlayer(connection, name);
                    if(l.isFull())
                    {
                        fullLobby = true;
                    }
                }
            }
            if(!foundLobby)
            {
                lobbiesList.add(new Lobby(connection,name,numberOfPlayer));
            }
            //if a lobby is full we flush all the other lobbies
            if(fullLobby)
            {
                for(Lobby l:lobbiesList)
                {
                    if(!l.isFull())
                    {
                        l.emptyLobby();
                        lobbiesList.remove(l);
                    }
                }
                gameHasStarted = true;
            }
        }
    }

    /**
     * this method removes a connection
     * @param c
     */
    public synchronized void deregisterConnection(SocketClientConnection c)
    {
        for (Lobby l:lobbiesList) {
            if(l.isInInTheLobby(c))
                l.removePlayer(c);
        }

    }

    /**
     * The method is used after a game is ended to reset the lobby and permit new players to join*/
    public void flushLobbies()
    {
        lobbiesList.clear();
        gameHasStarted = false;
    }

}
