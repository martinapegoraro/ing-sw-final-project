package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private boolean active = true;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String ip,int port)
    {
        this.ip=ip;
        this.port=port;
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
                            System.out.println(messaggio.getModelRep().playerNum);
                        } else {
                            System.out.println(messaggio.getMessage().getMessage());
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
                        out.flush();
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
        Socket socket=new Socket(ip,port);
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        Scanner stdin=new Scanner(System.in);
        System.out.println("scrivi il tuo nome");
        String name=stdin.nextLine();
        System.out.println("scrivi con quanti vuoi giocare");
        int num=stdin.nextInt();
        Choice c=new PlayerNumberChoice(name,num);
        Thread t1 = asyncWriteToSocket(c, out);
        t1.join();
        //out.writeObject(c);
        //out.flush();
        //MessageToVirtualView msg;
        try {


            while (true) {
                //msg = (MessageToVirtualView)in.readObject();
                Thread t0 = asyncReadFromSocket(in);
                t0.join();
                //t0 = asyncReadFromSocket(in);
                //t0.join();

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
}
