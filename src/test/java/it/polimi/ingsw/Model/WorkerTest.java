package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;
import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    Worker workerUnderTest;
    Box box1,box2,box3;

    @Before
    public void instantiateWorker()
    {

        box1=new Box(1,3);
        box2=new Box(2,2);
        box3=new Box(3,2);
        try {
            workerUnderTest=new Worker(box2);
        } catch (BoxAlreadyOccupiedException e) {
            e.printStackTrace();
        }
        box1.build();
        box1.build();
        try{box3.getTower().build(Block.DOME);}catch(TowerCompleteException e){e.printStackTrace();}
    }
    @Test
    public void getPosition() {
        assertEquals(2,workerUnderTest.getPosition().getCoord()[0]);
        assertEquals(2,workerUnderTest.getPosition().getCoord()[1]);
    }

    @Test()
    public void allowedMovementTest()
    {
        try
        {
            workerUnderTest.move(new Box(1,2));
            assertEquals(1,workerUnderTest.getPosition().getCoord()[0]);
            assertEquals(2,workerUnderTest.getPosition().getCoord()[1]);
        }
        catch(MoveErrorException ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }


    }

    @Test
    public void constructionTest()
    {
        Box b4=new Box(1,2);
        workerUnderTest.build(b4);
        assertEquals(1,b4.getTower().getHeight());
        workerUnderTest.build(box1);
        assertEquals(3,box1.getTower().getHeight());
        workerUnderTest.build(box3,Block.LEVEL3);
    }
}