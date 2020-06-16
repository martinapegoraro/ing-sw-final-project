package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;


/**
 * The following class represents a Box
 */
public class Box {
    private boolean occupied;
    private Tower tower;
    private final int x;
    private final int y;

    /**
     * given
     * @param coordX X coordinate on the board of the box
     * @param coordY Y coordinate on the board of the box
     * build an instance of a Box which contains also a
     * Tower instance initially set as null and an occupied flag
     */
    public Box(int coordX, int coordY)
    {
        occupied=false;
        tower=new Tower();
        x = coordX;
        y = coordY;
    }

    /**
     *@return if the box is or is not occupied
     */
    public boolean isOccupied()
    {
        return occupied;
    }

    /**
     * sets the box as not occupied
     */
    public void setOccupied()
    {
        occupied=true;
    }

    /**
     * sets the box as occupied
     */
    public void setNotOccupied()
    {
        occupied=false;
    }

    /**
     *
     * @return the tower instance of the box
     */
    public Tower getTower()
    {
        //Just a safety check, should not be needed
       //if (tower==null)
           //tower=new Tower();

        return tower;
    }

    /**
     * the method checks that the tower (attribute of the box) is not null
     * in case it is null the method instantiate it
     * than
     * the method build a piece on the tower and if the tower is complete (we have builded a dome )
     * the method sets the box as occupied
     * the method generates the TowerCompleteException if the tower has already a dome and we try to build on it.
     */
    public void build()
    {
        //This check too shouldn't be needed
        //if(tower==null)
            //tower=new Tower();
        try {
            tower.build();
        } catch (TowerCompleteException e) {
           occupied=true;
        }
        if(tower.getPieces().contains(Block.DOME))
            occupied=true;
    }


    /**
     * @return an array of int in the 0 position we have the x coord.
     * in the 1 position we ave y coordinate.
     */
    public int[] getCoord()
    {
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        return coord;
    }

    /**
     * given  @param Box b
     * @return true if the Box b is reachable from the this Box
     * a Box is reachable from another box if the two are adjacent(not checked here)
     * and the destination (Box b) is higher no more than 1 level than start height (Box this)
     */
    public boolean isReachable(Box b)
    {
        int startHeight ,destinationHeight;
        startHeight=tower.getHeight();
        destinationHeight=b.getTower().getHeight();
        if(destinationHeight-startHeight>1)return false;
        else return true;
    }


    /**
     * given a Box as @param
     * @return true if the given box represents the same box (in terms of coordinates) of the this Box
     */
    public boolean equals(Box b2)
    {
        return b2.getCoord()[0] == this.getCoord()[0] && b2.getCoord()[1] == this.getCoord()[1];
    }

    /**
     * return true if the this Box and the parameter box are adjacent
     * else return false
     * two boxes are adjacent if (given a box of coordinate x,y)
     * if the sum of x and y is no more than the sum of this x and y plus 2 or minus 2
     */
    //If b2 == this returns false
    public boolean isAdjacent(Box b2)
    {
        if(equals(b2))
        {
            return false;
        }

        return Math.abs(b2.getCoord()[0] - x) <= 1 && Math.abs(b2.getCoord()[1] - y) <= 1;
    }

    /**
     * return true if the given box is a border box
     * else return false
     */
    public boolean isBorder()
    {
        return x == 0 || y == 0 || x == 4 || y == 4;
    }

}
