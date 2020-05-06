package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Box;
import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.SelectWorkerCellChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetUpState implements State{

    private StateEnum stateID;
    private boolean hasFinished;
    private ArrayList<Box> boxList = new ArrayList<>();

    public SetUpState(Model model)
    {
        stateID = StateEnum.SetUp;
        hasFinished = false;
        startup(model);
    }

    @Override
    public StateEnum getID()
    {
        return stateID;
    }

    @Override
    //Links players with gods
    public void startup(Model model) {
        ArrayList<Player> playerList = (ArrayList<Player>)model.getTurn().getPlayersList();
        ArrayList<GodsList> godsList = (ArrayList<GodsList>)extractListOfGods();
        for(int n = 0; n < playerList.size(); n++)
        {
            playerList.get(n).setGodCard(godsList.get(n));
        }

        //Sets active player
        playerList.get(0).setPlayerActive(true);

        //Notify VirtualView
        model.updateModelRep();
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }


    //ENSURES: A random list of Gods is returned
    private List<GodsList> extractListOfGods()
    {
        ArrayList<GodsList> randomGodArray = new ArrayList<>(Arrays.asList(GodsList.values()));
        Collections.shuffle(randomGodArray);
        return randomGodArray;
    }

    @Override
    public void update(Choice userChoice, Model model) throws WrongChoiceException,BoxAlreadyOccupiedException
    {
        Player actingPlayer;
        SelectWorkerCellChoice castedChoice;
        ArrayList<Player> playerList = (ArrayList<Player>) model.getTurn().getPlayersList();
        actingPlayer = playerList.get(userChoice.getId());

        if(userChoice instanceof SelectWorkerCellChoice)
        {
            castedChoice = (SelectWorkerCellChoice)userChoice;
            Box selectedCell;
            //Initialize worker in the selected position if the cell is free
            try
            {
                selectedCell = model.getTurn().getBoardInstance().getBox(castedChoice.x, castedChoice.y);
            }
            catch(IndexOutOfBoundsException ex)
            {
                throw new WrongChoiceException("Invalid coordinates for Worker: " + castedChoice.x+","+ castedChoice.y);
            }

            if(selectedCell.isOccupied())
            {
                throw new BoxAlreadyOccupiedException("Another worker already occupies this Box!");
            }
            else
                {
                    boxList.add(selectedCell);
                }

            if(boxList.size() == 2){
                actingPlayer.setWorkersPosition(boxList.get(0), boxList.get(1));
                boxList.clear();

                //Exits out of SetUpState if the actingPlayer is the last
                if(actingPlayer == playerList.get(playerList.size()-1))
                {
                    hasFinished = true;
                }

                model.getTurn().setNextPlayer();
                model.updateModelRep();

            }
        }
        else
            {
                throw new WrongChoiceException("Wrong Choice Type! Expected: SelectWorkerCellChoice," +
                        "Received: "+ userChoice.toString());
            }
    }
}
