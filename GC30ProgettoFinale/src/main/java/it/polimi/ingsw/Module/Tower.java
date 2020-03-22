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
        //controllo la lista dei blocchi se non è completta aggiungo un blocco inserendo in modo automatico il blocco corretto
    }





}
