package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * the tower class represents a tower built on a box
 */
public class Tower {

    private ArrayList<Block> pieces;

    /**
     * the builder crates an array list of box at the beginning it will be empty
     */
    public Tower(){
        pieces=new ArrayList<Block>();
    }

    /**
     * the height is equivalent at the number of blocks in the tower
     * @return the height of a tower
     */
    public int getHeight(){

        return pieces.size();
    }

    /**
     * @return the list of pieces which are in the tower
     */
    public List<Block> getPieces()
    {
        return pieces;
    }

    /**
     * this build method is used when a custom block has to be built
     * @param b custom block to build
     * @throws TowerCompleteException when the tower has already a dome
     */
    public void build(Block b) throws TowerCompleteException{
        if(pieces.contains(Block.DOME))
            throw new TowerCompleteException("This tower is already complete");
        else
            pieces.add(b);
    }


    /**
     * this build method is used when I have to build the default block on a tower
     * @throws TowerCompleteException if the tower has already a dome
     */
    public void build() throws TowerCompleteException{
        if(getHeight()==0)
            pieces.add(Block.LEVEL1);
        else if(getPieces().get(getHeight()-1)==Block.DOME)
            throw new TowerCompleteException(" you are trying to build over a dome!!");
        else
            pieces.add(Block.getNextBlock(pieces.get(getHeight()-1)));
    }




}
