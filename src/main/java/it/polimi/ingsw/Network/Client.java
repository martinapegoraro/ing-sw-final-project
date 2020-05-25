package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PingChoice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;
import it.polimi.ingsw.View.LobbyWindow;
import it.polimi.ingsw.View.Observer;
import it.polimi.ingsw.View.View;


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
    private LobbyWindow lobbyWindow;

    public Client(String ip,int port)
    {
        this.ip=ip;
        this.port=port;
        view=new View();
        view.addObservers(this);
        try {
            lobbyWindow=new LobbyWindow();
            lobbyWindow.getSubmitButtonListenerListener().addObservers(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream in){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        MessageToVirtualView messaggio =(MessageToVirtualView) in.readObject();
                        if(messaggio.isModelRep()){
                            System.out.println("sono qui");
                            lobbyWindow.setNotVisible();
                            view.setView(messaggio.getModelRep());

                        } else if(messaggio.getMessage().getMessage().equals("One player left the game")){
                            System.out.println(messaggio.getMessage().getMessage());
                            socket.close();
                        }
                        else{

                            if(!messaggio.getMessage().getMessage().equals("ping"))
                            {
                                System.out.println(messaggio.getMessage().getMessage());
                            }
                            else
                            {
                                Thread t1 = asyncWriteToSocket(new PingChoice(), out);
                                t1.join();
                            }
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(Choice c, final ObjectOutputStream out){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
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


    public void run() throws IOException, InterruptedException {
        socket=new Socket(ip,port);
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        Scanner stdin=new Scanner(System.in);
        /*System.out.println("scrivi il tuo nome");
        String name=stdin.nextLine();
        System.out.println("scrivi con quanti vuoi giocare");
        int num=stdin.nextInt();*/
        /*Choice c=new PlayerNumberChoice(name,num);
        Thread t1 = asyncWriteToSocket(c, out);
        t1.join();
        //out.writeObject(c);
        //out.flush();
        //MessageToVirtualView msg;*/
        lobbyWindow.visible();
        Thread t0 = asyncReadFromSocket(in);
        t0.join();

        try {


            while (true) {
                //msg = (MessageToVirtualView)in.readObject();

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
