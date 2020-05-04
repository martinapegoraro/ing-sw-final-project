package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player giocatore1;
    @Before
    public void setUp() {
        giocatore1 = new  Player(1, "Mario");

    }

    @Test
    public void isActiveSetPlayerActiveTest() {
        assertFalse(giocatore1.isPlayerActive());
        giocatore1.setPlayerActive(true);
        assertTrue(giocatore1.isPlayerActive());
        giocatore1.setPlayerActive(false);
        assertFalse(giocatore1.isPlayerActive());
    }

    @Test
    public void getPlayerNameTest(){
        assertEquals("Mario",giocatore1.getPlayerName());
    }

    @Test
    public void getNumberTest()
    {
        assertEquals(1,giocatore1.getNumber());
    }

    @Test
    public void getWorkerList() {
        for (Worker w:giocatore1.getWorkerList()) {
            assertNull(w);
        }
    }

    @Test
    public void setGodGetGodTest()
    {
        GodsList carta=GodsList.APOLLO;
        giocatore1.setGodCard(carta);
        assertEquals(GodsList.APOLLO,giocatore1.getGod());
    }

    @Test
    public void setGetAthenaConditionTest()
    {
        giocatore1.changeAthenaCondition(true);
        assertTrue(giocatore1.isAthenaConditionTrue());
        giocatore1.changeAthenaCondition(false);
        assertFalse(giocatore1.isAthenaConditionTrue());
    }

    @Test
    public void setWorkersTest()
    {
        Box box1=new Box(1,1);
        Box box2=new Box(5,3);
        giocatore1.setWorkersPosition(box1,box2);
        assertEquals(2,giocatore1.getWorkerList().size());
        for (Worker w:giocatore1.getWorkerList()) {
            assertNotNull(w);
        }
        giocatore1.setSelectedWorker(1);
        assertTrue(giocatore1.getSelectedWorker().getPosition().equals(box2));

    }
    @Test
    public void playerHasLostTest()
    {
        Box box1=new Box(1,1);
        Box box2=new Box(5,3);
        giocatore1.setWorkersPosition(box1,box2);
        giocatore1.setHasLost();
        assertNull(giocatore1.getWorkerList().get(0));
        assertNull(giocatore1.getWorkerList().get(1));
    }



}