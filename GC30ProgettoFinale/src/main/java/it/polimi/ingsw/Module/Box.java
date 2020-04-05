package it.polimi.ingsw.Module;

import java.util.List;

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
        tower.build();
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
        return b.getTower().getHeight() - tower.getHeight() == 1;
    }

}
