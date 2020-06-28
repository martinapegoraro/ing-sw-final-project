package it.polimi.ingsw.Network;



import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientApp {
    public static void main(String[] args)
    {
        String ip="";
        while(ip=="") {
            ip = (String) JOptionPane.showInputDialog(new JFrame(), "insert the ip:", "Santorini", JOptionPane.PLAIN_MESSAGE, null, null, "");
        }
        //Client client=new Client("127.0.0.1",12345);
        Client client=new Client(ip,12345);
        try{
            client.run();
        }
        catch(IOException | InterruptedException e){
            JOptionPane.showMessageDialog(new Frame(),"the connection was refused by the server!!");
            //System.exit(0);
        }
    }
}
