package it.polimi.ingsw.Module;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int nTurn;
    private Match gestorePartita;
    public Turn(List<String> names)
    {
        nTurn=0;
        gestorePartita=new Match(names);
    }


    public Player switchPlayer()
    {
        nTurn++;
        return gestorePartita.getPlayerRound(nTurn);
    }

    public int getTurn()
    {
        return nTurn;
    }
    public List<Box> whereMove(Box b)
    {
        Board tavolo=gestorePartita.getBoard();
        int x=b.getCoord()[0];
        int y=b.getCoord()[1];
        List<Box> occupabili=new ArrayList<Box>();
        int i=0,j=0;
        x--;
        y--;
        while (i<3)
        {
            x+=i;
            for(j=0;i<3;j++)
            {
                y+=j;
                if(!tavolo.getBox(x,y).isOccupied() && b.isReachable(tavolo.getBox(x,y))&& x>=0 && x<=5 && y>=0 && y<=5)
                    occupabili.add(tavolo.getBox(x,y));
            }
            i++;
            y-=2;
        }
        return occupabili;
    }

}
