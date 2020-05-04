package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {
    Model modelTest;
    ModelRepresentation modelRepTest;
    Board boardTest;
    Player player1, player2;

    @Before

    public void initializeModel ()
    {
        List<String> listaNomi=new ArrayList<>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add("paperino");
        modelTest =new Model(listaNomi);
        player1 = new Player(1, "Anna");
        player2 = new Player (2, "Marco");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        modelRepTest = new ModelRepresentation(boardTest, players,)

    }

    @Test
    public void getTurn() {
    }

    @Test
    public void createModelRep() {
    }
}