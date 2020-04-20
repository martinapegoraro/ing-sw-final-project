package it.polimi.ingsw.Module;

import java.util.ArrayList;
import java.util.List;

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

    public List<Box> getBorderBoxes(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        int i=-1,j=-1;
        while(i+j<=2)
        {
            j=-1;
            while(j<2)
            {
                lista.add(getBox(b.getCoord()[0]+i,b.getCoord()[1]+j));
                j++;
            }
            i++;
        }
        lista.remove(5);
        return lista;
    }
}
