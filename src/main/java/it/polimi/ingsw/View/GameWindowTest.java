package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;

import java.util.ArrayList;

public class GameWindowTest {
    static final int PLAYERS = 3;
    public static void main(String[] main)
    {
        ArrayList<GodsList> listaGod = new ArrayList<>();
        String[] nomiPlayer;
        View v=new View();
        if(PLAYERS == 3)
        {
            nomiPlayer = new String[3];
            nomiPlayer[0] = "Pippo";
            nomiPlayer[1] = "Pluto";
            nomiPlayer[2] = "Paperino";

            listaGod.add(GodsList.PERSEPHONE);
            listaGod.add(GodsList.MINOTAUR);
            listaGod.add(GodsList.HEPHAESTUS);
        }
        else
            {
                nomiPlayer = new String[2];
                nomiPlayer[0] = "Pippo";
                nomiPlayer[1] = "Pluto";

                listaGod.add(GodsList.PERSEPHONE);
                listaGod.add(GodsList.MINOTAUR);
                //listaGod.add(GodsList.HEPHAESTUS);
        }

        GameWindow f = new GameWindow(nomiPlayer,listaGod, 0,v);
        f.setWindowVisible();
    }
}
