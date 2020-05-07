package it.polimi.ingsw.Network;

import javax.imageio.IIOException;
import java.io.IOException;

public class ServerApp {

    public static void main(String[] args)
    {
        Server server;
        try{
            server=new Server();
            server.run();
        }
        catch(IOException e)
        {
            System.out.println("error");
        }
    }
}
