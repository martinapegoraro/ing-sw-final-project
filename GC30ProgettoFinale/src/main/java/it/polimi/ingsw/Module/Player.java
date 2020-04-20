package it.polimi.ingsw.Module;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {
    private boolean isActive;
    private final String username;
    private GodsList card;
    private Worker[] workers = new Worker[2];
    private int number; //Salvo il numero del giocatore per gestire eventuali elementi grafici
    private boolean godActive;
    private boolean athenaCondition;

    public Player(int n, String name)
    {
        number = n;
        username = name;
        card=null;
        workers[0]=null;
        workers[1]=null;
        godActive=false;
        athenaCondition=false;
        isActive=false;
    }

    public boolean isPlayerActive()
    {
        return isActive;
    }
    public void setPlayerActive(boolean active)
    {
        isActive=active;
    }
    public String getPlayerName()
    {
        return username;
    }

    public int getNumber()
    {
        return number;
    }

    public ArrayList<Worker> getWorkerList()
    {
        return new ArrayList<>(Arrays.asList(workers));
    }

     public GodsList getGod() {
        return card;
     }
     public boolean isAthenaConditionTrue(){
        return athenaCondition;
     }
     public void setGodActive(boolean active)
     {
         godActive=active;
     }
     public boolean isGodActive()
     {
         return godActive;
     }
     public void changeAthenaCondition(boolean active)
     {
         athenaCondition=active;
     }
    public void setWorkersPosition(Box b1,Box b2)
    {
        try
        {
            workers[0] = new Worker(b1);
            workers[1] = new Worker(b2);
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.println("Indice scorretto passato a setWorker!");
        }
    }


}
