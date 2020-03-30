package it.polimi.ingsw.Module;

import org.junit.Test;

import static org.junit.Assert.*;

public class TowerTest {

    Tower tow=new Tower();
    @Test
    public void getHeight() {
        assertEquals(0,tow.getHeight());
    }
     @Test
    public void buildTestVuota()
     {
         tow.build();
         assertEquals(1,tow.getHeight());
     }

     @Test
    public void buildTestCompleta()
    {
        tow.build();
        tow.build();
        tow.build();
        tow.build();
        assertEquals(4,tow.getHeight());
        tow.build();
    }
}