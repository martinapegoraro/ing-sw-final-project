package it.polimi.ingsw.Module;

import it.polimi.ingsw.Module.Exceptions.TowerCompleteException;

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

    public void build() throws TowerCompleteException{
        if(pieces.size()==0)
            pieces.add(Block.LEVEL1);
        else {

                Block daCostruire = Block.getNextBlock(pieces.get(pieces.size()-1));
                pieces.add(daCostruire);

        }
    }





}
