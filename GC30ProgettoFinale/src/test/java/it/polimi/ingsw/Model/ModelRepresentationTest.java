package it.polimi.ingsw.Model;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ModelRepresentationTest {
    ModelRepresentation modelRepresentationTest;
    Board boardTest;
    Player player1, player2;
    Box b1,b2,b3,b4,b5,b6;
    Model model;


    @Before
    public void initializeModelRep ()
    {
        player1 = new Player(1, "Anna");
        player2 = new Player (2, "Marco");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        b1= new Box(0,1);
        b2 = new Box (2,2);
        b3 = new Box (1,4);
        b4 = new Box (4,0);
        b5 = new Box (1,2);
        b6 = new Box (3,0);
        player1.setWorkersPosition(b1,b2);
        player2.setWorkersPosition(b3,b4);
        player1.setGodCard(GodsList.ATHENA);
        player2.setGodCard(GodsList.PROMETHEUS);
        player1.setGodActive(true);
        player2.setGodActive(false);
        b5.build();
        b6.build();
        int[][] selectedCells = new int[5][5];
        modelRepresentationTest = new ModelRepresentation(boardTest, players, selectedCells);


    }

    @Test
    public void testWorkerPosition ()

    {
        int [][] workers = modelRepresentationTest.getWorkerPosition();
        assertEquals(-1, workers[1][1] );
        assertEquals(0, workers[0][1]);
        assertEquals(0, workers[1][4]);
        assertEquals(-1, workers[3][2]);
        assertEquals(0,workers[2][2]);
        assertEquals(0,workers[4][0]);
        assertEquals(-1, workers[2][1]);
        assertEquals(-1, workers[3][0]);

    }

    @Test
    public void testTowerPosition ()
    {
        int[][] towers = modelRepresentationTest.getTowerPosition();
        assertEquals(0, towers[1][2]);
        assertEquals(-1, towers[1][1]);
        assertEquals(0, towers[3][0]);
        assertEquals(-1, towers[2][2]);
        assertEquals(-1, towers[4][0]);
    }

    @Test
    public void testGodList()
    {
        String[] gods = modelRepresentationTest.getGodList();
        assertEquals("Athena", gods[0]);
        assertEquals("Prometheus", gods[1]);
    }

    @Test
    public void testPlayersName()
    {
        String [] playerslist = modelRepresentationTest.getPlayersName();
        assertEquals("Anna", playerslist[0]);
        assertEquals("Marco", playerslist[1]);
    }

    @Test
    public void testPlayerNum()
    {
        int playerNumber = modelRepresentationTest.getPlayerNum();
        assertEquals(2, playerNumber);
    }

    @Test
    public void testaActiveGodsList()
    {
        boolean []  activegods = modelRepresentationTest.getActiveGodsList();
        assertTrue(activegods[0]);
        assertFalse (activegods[1]);

    }



}