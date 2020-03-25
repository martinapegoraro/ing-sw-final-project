package it.polimi.ingsw.Module;

import java.util.ArrayList;


public class Tower {
    private ArrayList<Block> pieces;

    public Tower(){
        pieces=new ArrayList<Block>();
    }
    public int getHeight(){
        return pieces.size();
    }

    public Tower getTower()
    {
        try {
            return (Tower) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void build(){
        if(pieces.size()==0)
            pieces.add(Block.LEVEL1);
        else {
            try {
                Block daCostruire = Block.getNextBlock(pieces.get(pieces.size()));
                pieces.add(daCostruire);
            } catch (TowerCompleteException e) {
                e.printStackTrace();
            }
        }
    }





}
