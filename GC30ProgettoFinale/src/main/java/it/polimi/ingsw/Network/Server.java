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


    public Server() throws IOException
    {
        this.serverSocket=new ServerSocket(PORT);
        executor= Executors.newFixedThreadPool(128);
        connections=new ArrayList<SocketClientConnection>();
        lobbiesList=null;
    }

    private synchronized void registerConnection(SocketClientConnection c)
    {
        connections.add(c);
    }

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

    public synchronized void deregisterConnection(SocketClientConnection c)
    {
        for (Lobby l:lobbiesList) {
            if(l.isInInTheLobby(c))
                l.removePlayer(c);
        }
        //TODO: to handle whats append to the game connection when i close one.
    }
    public synchronized void printLobbiesList()
    {
        int i=1;
        for (Lobby l:lobbiesList ) {
            System.out.println("printing lobby number"+i);
            l.print();
            i++;
        }
    }
}
