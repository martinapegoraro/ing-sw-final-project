package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

public class Worker{
    private Box position;
    private int height; //Altezza della torre su cui si trova il worker, per non dover passare da Box e Tower
    private int posX,posY;
    //Chiamo il costruttore solo dopo aver assegnato una casella al worker

    public Worker(Box b) throws NullPointerException, BoxAlreadyOccupiedException
    {
        if(b == null)
        {
            throw new NullPointerException("Worker box can't be initialized to null!");
        }

        if(b.isOccupied())
        {
            throw new BoxAlreadyOccupiedException("Worker box can't be occupied during initialization!");
        }

        position = b;
        position.setOccupied();
        updatePosition();
    }

    public void move(Box to) throws MoveErrorException
    {
        if(position.isAdjacent(to))
        {
            position.setNotOccupied();
            position = to;
            position.setOccupied();
            updatePosition();
        }
        else
            {
                //position = to;
                //updatePosition();
                throw new MoveErrorException("WARNING: Box selected for Move is not Adjacent to current Worker!");
            }
    }
    public Box getPosition()
    {
        return position;
    }

    private void updatePosition()
    {
        posX=position.getCoord()[0];
        posY=position.getCoord()[1];
        if(position.getTower()!=null)
            height=position.getTower().getHeight();
        else
            height=0;
    }
    public void build(Box where)
    {
        where.build();
    }

    public void build(Box where,Block nextBlock)
    {
        try {
            where.getTower().build(nextBlock);
        }
        catch (TowerCompleteException e) {
            System.out.println(e.getMessage());
        }
    }

    public String toString()
    {
       String s="x: "+posX+"\ty: "+posY+"\theight: "+height;
       return s;
    }
}
