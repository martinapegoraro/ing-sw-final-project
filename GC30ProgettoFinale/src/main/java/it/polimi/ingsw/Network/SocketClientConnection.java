package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;
import it.polimi.ingsw.View.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientConnection extends Observable<Choice> implements Runnable{
    private Socket socket;
    private boolean active;
    private Server server;
    ObjectOutputStream out;
    ObjectInputStream in;


    public SocketClientConnection(Socket socket,Server server) throws IOException {
        this.socket=socket;
        this.server=server;
        active=true;
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
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

    private synchronized void send(MessageToVirtualView message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    public void asyncSend(MessageToVirtualView message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    private void close()
    {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public void run()
    {
        try{
            PlayerNumberChoice nameNPChoice= (PlayerNumberChoice)in.readObject();
            server.addToLobby(this, nameNPChoice.name,nameNPChoice.playerNumber);
            while(isActive())
            {
                Choice read= (Choice)in.readObject();
                notify(read);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
