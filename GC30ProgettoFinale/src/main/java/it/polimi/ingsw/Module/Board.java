package it.polimi.ingsw.Module;

public class Board {
    private Box[][] board;
    private static Board singletonBoard;

    private Board()
    {
        int i, k;
        //Inizializzo i puntatori senza aver assegnato alcuna Box
        board=new Box[5][5];

        for(i = 0; i < 5; i++)
        {
            for(k = 0; k < 5; k++)
            {
                board[i][k] = new Box(i, k);
            }
        }

        singletonBoard=null;
    }

    public static Board getInstance()
    {
        if(singletonBoard==null)
            singletonBoard=new Board();
        return singletonBoard;
    }

    public Box getBox(int riga, int col)
    {
        return board[riga][col];
    }
}
