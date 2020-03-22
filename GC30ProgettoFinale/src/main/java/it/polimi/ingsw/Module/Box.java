package it.polimi.ingsw.Module;

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
        if(tower !=null)
            return tower.getTower();
        else
            return null;
    }

    public void build()
    {
        if(tower==null)
            tower=new Tower();
        tower.build();
    }



}
