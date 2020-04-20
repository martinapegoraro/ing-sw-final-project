package it.polimi.ingsw.Module;

import it.polimi.ingsw.Module.Exceptions.TowerCompleteException;

public class Worker{
    Box position;
    int height; //Altezza della torre su cui si trova il worker, per non dover passare da Box e Tower
    int posX,posY;
    //Chiamo il costruttore solo dopo aver assegnato una casella al worker

    public Worker(Box b)
    {
        position = b;
        updatePosition();
    }

    public void move(Box to)
    {
        position = to;
        updatePosition();
    }
    private void updatePosition()
    {
        posX=position.getCoord()[0];
        posY=position.getCoord()[1];
        height=position.getTower().getHeight();
    }
    public void build(Box where)
    {
        where.build();
    }

    public void build(Box where,Block nextBlock)
    {
        try {
            where.getTower().build(nextBlock);
        } catch (TowerCompleteException e) {
            e.printStackTrace();
        }
    }

    public String toString()
    {
       String s="x: "+posX+"\ty: "+posY+"\theight: "+height;
       return s;
    }
}
