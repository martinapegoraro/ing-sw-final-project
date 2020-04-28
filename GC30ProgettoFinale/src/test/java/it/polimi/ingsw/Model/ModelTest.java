package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {
    Model modelTest;
    List<String> playerNamesList;
    @Before
    public void initializeModel () {modelTest = new Model(playerNamesList);}

    @Test
    public void getTurn() {
    }

    @Test
    public void createModelRep() {
    }
}