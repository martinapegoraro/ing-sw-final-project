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
    ObjectOutputStream out;
    ObjectInputStream in;

    public Client(String ip,int port)
    {
        this.ip=ip;
        this.port=port;
    }

    public void run() throws IOException
    {
        Socket socket=new Socket(ip,port);
        out =new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        Scanner stdin=new Scanner(System.in);
        System.out.println("scrivi il tuo nome");
        String name=stdin.nextLine();
        System.out.println("scrivi con quanti vuoi giocare");
        int num=stdin.nextInt();
        Choice c=new PlayerNumberChoice(name,num);
        out.writeObject(c);
        out.flush();
        MessageToVirtualView msg;
        try {


            while (true) {
                msg = (MessageToVirtualView)in.readObject();



            }
        }
        catch(NoSuchElementException | ClassNotFoundException e)
        {
            System.out.println("connection closed");
        }
        finally {
            stdin.close();

            socket.close();
        }
    }
}
