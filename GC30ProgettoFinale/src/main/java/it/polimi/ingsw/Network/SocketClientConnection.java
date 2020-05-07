package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.View.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements Runnable{
    private Socket socket;
    private boolean active;
    private Server server;


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
            Scanner in = new Scanner(socket.getInputStream());
            //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter socketOut=new PrintWriter(socket.getOutputStream());
            socketOut.println("scrivi il tuo nome");
            socketOut.flush();
            String name = in.nextLine();
            socketOut.println("scrivi con quante persone vuoi giocare");
            socketOut.flush();
            Integer num= Integer.valueOf(in.nextLine());
            server.addToLobby(this, name,num);
            server.printLobbiesList();
            while(isActive())
            {
                String read= in.nextLine();
                notify(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
