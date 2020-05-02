package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.Model.Model;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {

    Model model;
    Controller controllerUnderTest;

    @Before
    public void modelSetUp()
    {
        List<String> listaNomi=new ArrayList<String>();
        listaNomi.add("pippo");
        listaNomi.add("pluto");
        listaNomi.add("topolino");
        model=new Model(listaNomi);
    }

    @Before
    public void controllerSetUp()
    {
        try {
            controllerUnderTest=new Controller(model,3);
        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        }
    }



}