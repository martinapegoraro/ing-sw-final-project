package it.polimi.ingsw.Module;

import java.util.List;

public class Box {
    private boolean occupied;
    private Tower tower;
    public Box()
    {
        occupied=false;
        tower=null;
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



}
