package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.View.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements Runnable{
    private Socket socket;
    private ObjectOutputStream out;
    private boolean active;
    private Server server;
    private Scanner in;
    private String name;

    public SocketClientConnection(Socket socket,Server server)
    {
        this.socket=socket;
        this.server=server;
        active=true;
    }
    private synchronized boolean isActive()
    {
        return active;
    }

    public synchronized void closeConnection()
    {
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        active=false;
    }

    private void close()
    {
        closeConnection();
        //server.deregisterConnection(this);
    }

    public void run()
    {
        try{
            in=new Scanner(socket.getInputStream());
            out= new ObjectOutputStream(socket.getOutputStream());
            name=in.nextLine();
            server.addToLobby(this,name);
            while(isActive())
            {
                String read=in.nextLine();
                notify(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
