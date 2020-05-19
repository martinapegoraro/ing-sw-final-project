package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

import javax.swing.*;
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

    /*Il seguente metodo verrà usato nel caso in cui non si debba
    seguire l'ordine di costruzione predefinito dal regolamento di gioco*/
    public void build(Block b) throws TowerCompleteException{
        if(pieces.contains(Block.DOME))
            throw new TowerCompleteException("This tower is already complete");
        else
            pieces.add(b);
    }

    /*Il seguente metodo verrà chiamato in tutti i casi si voglia costruire in una cella
    tranne i casi particolari*/
    public void build() throws TowerCompleteException{
        if(getHeight()==0)
            pieces.add(Block.LEVEL1);
        else if(getPieces().get(getHeight()-1)==Block.DOME)
            throw new TowerCompleteException(" you are trying to build over a dome!!");
        else
            pieces.add(Block.getNextBlock(pieces.get(getHeight()-1)));
    }




}
