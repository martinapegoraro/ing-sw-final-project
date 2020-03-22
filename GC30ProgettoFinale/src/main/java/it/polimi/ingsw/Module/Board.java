package it.polimi.ingsw.Module;

public class Board {
    private Box[][] board;
    private static Board singletonBoard;

    private Board()
    {
        board=new Box[5][5];
        singletonBoard=null;
    }

    public Board getInstance()
    {
        if(singletonBoard==null)
            singletonBoard=new Board();
        return singletonBoard;
    }
}
