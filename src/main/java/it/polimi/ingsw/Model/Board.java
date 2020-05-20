package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The following class represents a Board
 */
public class Board {
    private Box[][] board;
    private static Board singletonBoard;

    /**
     * builds an instance of board, initially composed by 25 empty boxes
     * the singletonBoard is still set as null to make sure that this instance of board remains unique
     */
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

    /**
     * this method assigns a new instance of board to the singletonBoard
     * @return the singletonBoard, which now is surely the only board present in the match
     */
    public static Board getInstance()
    {
        if(singletonBoard==null)
            singletonBoard=new Board();
        return singletonBoard;
    }

    /**
     * given
     * @param riga
     * @param col
     * whuch define the position of the box wanted
     * @return the required box
     * @throws IndexOutOfBoundsException if the two parameters given do not correspond to an existing box
     */
    public Box getBox(int riga, int col) throws IndexOutOfBoundsException
    {
        return board[riga][col];
    }

    /**
     * given
     * @param b , which is a box
     * @return an ArrayList of boxes, that are the eight boxes surrounding the given box
     * (obviously they can be less than eight if the given box is on the border of the board)
     */
    public List<Box> getBorderBoxes(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        int i=-1,j=-1;
        while(i<2)
        {
            j=-1;
            while(j<2)
            {
                if(b.getCoord()[0]+i>=0 && b.getCoord()[0]+i<5 && b.getCoord()[1]+j>=0 && b.getCoord()[1]+j<5)
                    if(i!=0 || j!=0)
                        lista.add(getBox(b.getCoord()[0]+i,b.getCoord()[1]+j));
                j++;
            }
            i++;
        }
        return lista;
    }

    /**
     * this method creates a new singletonBoard, it is going to be used when a new match is started
     * in order to create an empty board
     */
    public void newBoard()
    {
        singletonBoard = new Board();
    }
}