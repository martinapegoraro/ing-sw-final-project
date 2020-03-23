package it.polimi.ingsw.Module;

public class Player {
    private boolean isActive;
    private String username;
    //TODO: private God card;
    //TODO: Aggiungere effetti applicati al Player da/dai Gods
    private Worker[] workers = new Worker[2];
    private int number; //Salvo il numero del giocatore per gestire eventuali elementi grafici

    public Player(int n, String name)
    {
        number = n;
        username = name; // Assumo non cambi durante la partita

    }

    public boolean getActive()
    {
        return isActive;
    }

    public String getName()
    {
        return username;
    }

    public int getNumber()
    {
        return number;
    }

    /**
     * public God getGod() {
     *
     * }
     * **/

    public void setWorker(int nWorker, Box casella)
    {
        try
        {
            workers[nWorker] = new Worker(casella);
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.println("Indice scorretto passato a setWorker! I: " + nWorker);
        }
    }


}