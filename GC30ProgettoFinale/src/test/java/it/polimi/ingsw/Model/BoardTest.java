package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    Board gameTable;
    @Before
    public void instantiateBoard()
    {
        gameTable = Board.getInstance();
    }

    //ensures that the coordinates are correct
    @Test
    public void coordinatesAreLinked()
    {
        int i,k;
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

    @Test
    public void singleInstanceTest()
    {
        Board gameTable2=Board.getInstance();
        assertNotNull(gameTable2);
        assertEquals(gameTable2,gameTable);
    }

    @Test
    public void getBoxTest()
    {
        Box extracted=gameTable.getBox(2,3);
        assertEquals(2,extracted.getCoord()[0]);
        assertEquals(3,extracted.getCoord()[1]);
    }

    @Test
    public void getBorderBoxesCentralBoxTest()
    {
        List<Box> listOfBoxes=gameTable.getBorderBoxes(gameTable.getBox(2,3));
        assertEquals(8,listOfBoxes.size());
    }

    @Test
    public void getBorderBoxesBorderBoxTest()
    {
        List<Box> listOfBoxes=gameTable.getBorderBoxes(gameTable.getBox(0,3));
        assertEquals(5,listOfBoxes.size());
    }

    @Test
    public void getBorderBoxesCornerBoxTest()
    {
        List<Box> listOfBoxes=gameTable.getBorderBoxes(gameTable.getBox(0,0));
        assertEquals(3,listOfBoxes.size());
    }
}