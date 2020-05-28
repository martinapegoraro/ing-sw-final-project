package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.PingMessage;
import it.polimi.ingsw.Utils.ExitChoice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;
import it.polimi.ingsw.View.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SocketClientConnection extends Observable<Choice> implements Runnable{
    private Socket socket;
    private boolean active;
    private Server server;
    private boolean connectionPing;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Timer timer;
    private TimerTask task;
    private int pingCounter;





    public SocketClientConnection(Socket socket,Server server) throws IOException {
        this.socket=socket;
        this.server=server;
        active=true;
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        connectionPing=true;
        pingCounter=0;
        ping();
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
    private void ping()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
               while(connectionPing) {
                   try {
                       TimeUnit.SECONDS.sleep(15);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   send(new MessageToVirtualView(new PingMessage()));
                   timer=new Timer();
                   task=new TimerTask() {
                       @Override
                       public void run() {
                           close();
                       };
                   };
                   timer.schedule(task,4000);
               }
            }
        }).start();
    }

    private synchronized void send(MessageToVirtualView message) {
        try {
            out.reset();
            out.writeObject(message);
            out.reset();
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
        connectionPing=false;
        notify(new ExitChoice());
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public void run()
    {
        try{
            while(isActive()) {
                Choice read = (Choice) in.readObject();
                if (!read.toString().equals("ping")) {
                    pingCounter=0;
                    if (read.toString().equals("PlayerNumberChoice")) {
                        PlayerNumberChoice np = (PlayerNumberChoice) read;
                        server.addToLobby(this, np.name, np.playerNumber);
                    } else
                        notify(read);
                }
                else
                {
                    pingCounter++;
                    if(pingCounter==4)
                        close();
                    task.cancel();
                }
            }
        } catch (IOException e) {
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
