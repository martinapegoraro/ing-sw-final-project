package it.polimi.ingsw.Network;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args)
    {
        Client client=new Client("127.0.0.1",12345);
        try{
            client.run();
        }
        catch(IOException | InterruptedException e){
                System.out.println(e.getMessage());
        }
    }
}
