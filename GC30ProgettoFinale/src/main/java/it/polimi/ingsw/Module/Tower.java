package it.polimi.ingsw.Module;

import java.util.ArrayList;
import java.util.List;


public class Tower {
    private ArrayList<Block> pieces;

    public Tower(){
        pieces=new ArrayList<Block>();
    }
    public int getHeight(){
        return pieces.size();
    }

    public List<Block> getPieces()
    {

        return pieces;
    }

    public void build(){
        if(pieces.size()==0)
            pieces.add(Block.LEVEL1);
        else {
            try {
                Block daCostruire = Block.getNextBlock(pieces.get(pieces.size()-1));
                pieces.add(daCostruire);
            } catch (TowerCompleteException e) {
                e.printStackTrace();
            }
        }
    }





}
