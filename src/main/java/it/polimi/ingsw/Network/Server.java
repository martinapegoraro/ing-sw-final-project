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
                Socket socket=serverSocket.accept();
                SocketClientConnection connection=new SocketClientConnection(socket,this);

                registerConnection(connection);
                executor.submit(connection);
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
        if(lobbiesList==null){
            lobbiesList=new ArrayList<Lobby>();
            lobbiesList.add(new Lobby(connection,name,numberOfPlayer));
        }
        else
        {
            for (Lobby l:lobbiesList) {
                if(numberOfPlayer==l.getNumberOfPlayers()) {
                    l.addPlayer(connection, name);
                }
            }
            lobbiesList.add(new Lobby(connection,name,numberOfPlayer));
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

}
