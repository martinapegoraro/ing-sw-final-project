package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.PingMessage;
import it.polimi.ingsw.Utils.ExitChoice;
import it.polimi.ingsw.Utils.PingChoice;
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

/**
 * the SocketClientConnection class is the one which handles the socket connection receives the messages and sends them to the controller
 */
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


    /**
     * the builder methods creates the input and output streams and at the end it runs the ping method
     * @param socket is the socket used for communicating
     * @param server instance used during the game
     * @throws IOException thrown when the connection is unexpectedly closed
     */
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

    /**
     * the methods
     * @return true if the connection is still active
     * and @returns false if it is not
     */
    public synchronized boolean isActive()
    {
        return active;
    }

    /**
     * the method closes the socket and set not active the active attribute
     */
    public synchronized void closeConnection()
    {
        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        active=false;
    }

    /**
     * the ping method sends each 15 seconds a ping message to the client
     */
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
                   System.out.println("send Ping");
                   send(new MessageToVirtualView(new PingMessage()));
                   timer=new Timer();
                   task=new TimerTask() {
                       @Override
                       public void run() {
                           close();
                       };
                   };
                   //timer.schedule(task,10000);
               }
            }
        }).start();
    }

    /**
     * @param message sent to the client
     */
    public synchronized void send(MessageToVirtualView message) {
        try {
            out.reset();
            out.writeObject(message);
            out.reset();
        } catch(IOException e){
            System.out.println(e.getMessage());
            connectionPing=false;
            closeConnection();
            System.out.println("Deregistering client...");
            server.deregisterConnection(this);
            System.out.println("Done!");
        }

    }

    /**
     * this send creates a new thread which sends the message received as  @param message
     */
    public void asyncSend(MessageToVirtualView message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    /**
     * the close method shuts down the connection and sends to the clients an exit choice
     */
    public void close()
    {
        connectionPing=false;
        notify(new ExitChoice());
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    /**
     * the run method continue to read from the socket until the active attribute is true
     */
    public void run()
    {
        while(isActive()) {
            Thread t0 = asyncReadFromSocket(this,in);
            try {
                t0.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                close();
            }
        }
    }

    /**
     * the asyncRead reads in an asynchronous way from the socket
     * @param connection used for communication channel
     * @param in stream where the message arrives
     * @return the thread
     */
    public Thread asyncReadFromSocket(SocketClientConnection connection,final ObjectInputStream in){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Choice read = (Choice) in.readObject();
                    if (!read.toString().equals("ping")) {
                        pingCounter=0;
                        if (read.toString().equals("PlayerNumberChoice")) {
                            PlayerNumberChoice np = (PlayerNumberChoice) read;
                            server.addToLobby(connection, np.name, np.playerNumber);
                        } else {
                            System.out.println("received:"+read.toString());
                            connection.notify(read);
                        }
                    }
                    else
                    {
                        System.out.println("received pong");
                        if(task!=null)
                            task.cancel();
                        pingCounter++;
                        if(pingCounter>=30)
                            close();

                    }
                } catch (IOException e) {
                    close();
                } catch (ClassNotFoundException e) {
                    close();
                    e.printStackTrace();

                }
            }
        });
        t.start();
        return t;
    }



}
