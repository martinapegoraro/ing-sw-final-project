package it.polimi.ingsw.Module;

public class Worker{
    Box position;
    int height; //Altezza della torre su cui si trova il worker, per non dover passare da Box e Tower

    //Chiamo il costruttore solo dopo aver assegnato una casella al worker
    public Worker(Box b)
    {
        position = b;
        height = position.getTower().getHeight(); //No Default a 0 per evitare bug
    }

    public void move(Box to)
    {
        //Nessun controllo sul movimento da una box ad un'altra, passa da Player
        //TODO: Creare una eccezione ad hoc per Build/Move errate
        position = to;
    }

    public void build(Box where)
    {
        where.getTower().build();
    }

    public String toString()
    {
        //Return posizione della Box e altezza della torre sulla quale si trova
        return null;
    }
}
