package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PingChoice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;
import it.polimi.ingsw.View.LobbyWindow;
import it.polimi.ingsw.View.Observer;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.ViewState;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Observer<Choice> {
    private String ip;
    private int port;
    private boolean active = true;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private View view;


    /**
     * given the parameters
     * @param ip ip address of the server
     * @param port ip port of the server
     * the client creates an instance of the view and put itself as observer of the view
     */
    public Client(String ip,int port)
    {
        this.ip=ip;
        this.port=port;
        view=new View(ViewState.LobbyState);
        view.addObservers(this);
    }


    /**
     * checks if the client is still active
     * @return true if the client is active else false
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * sets the active following the parameter
     * @param active if true active will be true else false
     */
    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * the method creates a parallel thread and read from the ObjectInputStream
     * once read the messageToVirtualView from the stream
     * if the message is a modelRep I update the view
     * if the message is an exit message I close the client
     * if the message is a ping message I reply to the ping
     * @param in is the input stream where we'll read the server's messages
     * @return a thread
     */
    public Thread asyncReadFromSocket(final ObjectInputStream in){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        MessageToVirtualView messaggio = (MessageToVirtualView) in.readObject();
                            if (messaggio.isModelRep()) {

                                view.updateWindow(messaggio);

                            } else if (messaggio.getMessage().getMessage().equals("Exit")) {
                                JOptionPane.showMessageDialog(new JFrame(),
                                        "a player quit the game\n" +
                                                "the game is ended",
                                        "end game ",
                                        JOptionPane.ERROR_MESSAGE);
                                setActive(false);
                                socket.close();
                                System.exit(0);
                            } else {

                                if (!messaggio.getMessage().getMessage().equals("ping")) {
                                    view.updateWindow(messaggio);
                                } else {
                                    Thread t1 = asyncWriteToSocket(new PingChoice(), out);
                                    t1.join();
                                }
                            }

                    }
                } catch (Exception e){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "the connection with the server is lost\n" +
                                    "the game is ended",
                            "Server Error",
                            JOptionPane.ERROR_MESSAGE);
                    setActive(false);
                    System.exit(0);

                }
            }
        });
        t.start();
        return t;
    }

    /**
     * this method creates a new thread which sends a choice in the ObjectOutputStream
     * @param c is the choice we want send to the server
     * @param out is the OutputStream where we want sends the choice
     * @return the thread
     */
    public Thread asyncWriteToSocket(Choice c, final ObjectOutputStream out){
        Thread t = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    //while (isActive()) {

                        out.writeObject(c);
                        out.reset();
                    //}
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }


    /**
     * the run methods creates the connection between client and server
     * and until the client is active it keep reading from the socket
     * @throws IOException if I try to read or write but the streams are closed
     * @throws InterruptedException if the read write process ar unexpectedly closed
     */
    public void run() throws IOException, InterruptedException {
        socket=new Socket(ip,port);
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        Scanner stdin=new Scanner(System.in);
        Thread t0 = asyncReadFromSocket(in);
        t0.join();

        try {


            while (true) {
                t0 = asyncReadFromSocket(in);
                t0.join();

            }
        }
        catch(NoSuchElementException e)
        {
            System.out.println("connection closed");
        }
        finally {
            stdin.close();

            socket.close();
        }
    }

    /**
     * the client update recives as parameter a choice object as sends it through the socket
     * @param c is the choice we want send to the server
     */
    @Override
    public void update(Choice c) {
        Thread t1 = asyncWriteToSocket(c, out);
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
