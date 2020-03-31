package it.polimi.ingsw.Module;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Board gameTable = Board.getInstance();

    @Test
    public void coordinatesAreLinked()
    {
        int i,k;
        //Test che le celle contenute nella matrice di board abbiano coordinate corrette
        for(i = 0; i < 5; i++)
        {
            for(k = 0; k < 5; k++)
            {
                assertEquals(gameTable.getBox(i,k).getCoord()[0], i);
                assertEquals(gameTable.getBox(i,k).getCoord()[1], k);
            }
        }

        //TODO: Test che dia errore per int > 1 sull'Array coord
    }
}