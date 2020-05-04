package it.polimi.ingsw.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {
    private static final int PORT=1234;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private List<SocketClientConnection> connections;
    private Lobby lobby;


    public Server() throws IOException
    {
        this.serverSocket=new ServerSocket(PORT);
        executor= Executors.newFixedThreadPool(128);
        connections=new ArrayList<SocketClientConnection>();
        lobby=new Lobby();
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

    public void addToLobby(SocketClientConnection connection,String name)
    {
        lobby.addPlayer(connection,name);
    }

    public synchronized void deregisterConnection(SocketClientConnection c)
    {
        connections.remove(c);
        //TODO: to handle whats append to the game connection when i close one.
    }
}
