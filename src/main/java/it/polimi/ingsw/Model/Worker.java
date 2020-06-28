package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

/**
 * the Worker class contains all the information of a worker
 */
public class Worker{
    private Box position;
    /**
     * if the worker is on a tower this attribute represents his height
     * in this way we don't need to go in the box and his tower to have this information
     */
    private int height;
    private int posX,posY;


    /**
     * the constructor is called only to select the first position of the worker
     * @param b is the box chosen for initialization

     */
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

    /**
     * change the current position of the worker in the box given as parameter
     */
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

    /**Swaps the positions of two workers.
     * Method added to complement move, swap does not change box.isOccupied() state
     * two consecutive moves would leave one box flag in the wrong state.**/
    public void swap(Worker opponentWorker) throws MoveErrorException
    {
        if(position.isAdjacent(opponentWorker.getPosition()))
        {
            Box temp;
            temp = position;
            position = opponentWorker.getPosition();
            opponentWorker.position = temp;
        }
        else
        {
            //position = to;
            //updatePosition();
            throw new MoveErrorException("WARNING: Box selected for Move is not Adjacent to current Worker!");
        }
    }

    /**
     * @return the position of the worker
     */
    public Box getPosition()
    {
        return position;
    }

    /**
     * updates the information of the worker such as coordinates (taken from the box where the worker is placed)
     * and his height
     */
    private void updatePosition()
    {
        posX=position.getCoord()[0];
        posY=position.getCoord()[1];
        height=position.getTower().getHeight();
    }

    /**
     * this method is used by the worker to build in the selected cell
     */
    public void build(Box where)
    {
        where.build();
    }

    /**
     * this method is used by the worker to build a specified block in the selected cell
     *
     */
    public void build(Box where,Block nextBlock)
    {
        try {
            where.getTower().build(nextBlock);
        }
        catch (TowerCompleteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return a string containing the basic information of the worker
     */
    public String toString()
    {
       String s="x: "+posX+"\ty: "+posY+"\theight: "+height;
       return s;
    }
}
