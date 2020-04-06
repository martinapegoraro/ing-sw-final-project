package it.polimi.ingsw.Module;

import it.polimi.ingsw.Module.Exceptions.TowerCompleteException;
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
         try {
             tow.build();
         } catch (TowerCompleteException e) {
             e.printStackTrace();
         }
         assertEquals(1,tow.getHeight());
     }

     @Test
    public void buildTestCompleta()
    {
        try {
            tow.build();
            tow.build();
            tow.build();
            tow.build();
        } catch (TowerCompleteException e) {
            e.printStackTrace();
        }
        assertEquals(4,tow.getHeight());
        try {
            tow.build();
        } catch (TowerCompleteException e) {
            e.printStackTrace();
        }

    }
}