package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

public class Box {
    private boolean occupied;
    private Tower tower;
    private final int x;
    private final int y;

    public Box(int coordX, int coordY)
    {
        occupied=false;
        tower=null;
        x = coordX;
        y = coordY;
    }

    public boolean isOccupied()
    {
        return occupied;
    }

    public Tower getTower()
    {
       return tower;
    }

    public void build()
    {
        if(tower==null)
            tower=new Tower();
        try {
            tower.build();
        } catch (TowerCompleteException e) {
           occupied=true;
        }
    }

    public int[] getCoord()
    {
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        return coord;
    }

    public boolean isReachable(Box b)
    {
        int startHeight=0,destinationHeight=0;
        if(tower!=null) startHeight=tower.getHeight();
        if(b.getTower()!=null) destinationHeight=b.getTower().getHeight();
        if(destinationHeight-startHeight>1)return false;
        else return true;
    }

}
