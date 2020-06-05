package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;
import it.polimi.ingsw.Model.Exceptions.WrongChoiceException;
import it.polimi.ingsw.Utils.*;
import it.polimi.ingsw.Utils.ErrorMessages.SelectedCellErrorMessage;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetUpState implements State{

    private StateEnum stateID;
    private boolean hasFinished;
    private ArrayList<Box> boxList = new ArrayList<>();
    private ArrayList<GodsList> selectedGodList;
    private boolean godsSelected,godsAssigned,firstPlayerSelected;

    public SetUpState(Model model)
    {
        stateID = StateEnum.SetUp;
        selectedGodList=new ArrayList<GodsList>();
        hasFinished = false;
        godsSelected=false;
        godsAssigned=false;
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
        /*ArrayList<GodsList> godsList = (ArrayList<GodsList>)extractListOfGods();
        for(int n = 0; n < playerList.size(); n++)
        {
            playerList.get(n).setGodCard(godsList.get(n));
        }*/

        //Sets active player
        playerList.get(0).setPlayerActive(true);

        //Notify VirtualView
        model.updateModelRep(stateID);
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

        if(userChoice.toString().equals("GodsCollectionChoice"))
        {
            GodsCollectionChoice c=(GodsCollectionChoice)userChoice;
            selectedGodList.addAll(c.getGodList());
            godsSelected=true;
            model.getTurn().setNextPlayer();
            model.updateModelRep(selectedGodList);
        }


        if(godsSelected && userChoice.toString().equals("GodChoice"))
        {
            actingPlayer=model.getTurn().getCurrentPlayer();
            GodChoice c= (GodChoice)userChoice;
            if(selectedGodList.contains(c.getGod()) && c.getId()==actingPlayer.getNumber())
            {
                actingPlayer.setGodCard(c.getGod());
                selectedGodList.remove(c.getGod());
                model.getTurn().setNextPlayer();
                //model.updateModelRep(stateID);
                //if in the list of chosen gods remains only a god I directly assign it to the last player
                if(selectedGodList.size()==1){
                    actingPlayer=model.getTurn().getCurrentPlayer();
                    actingPlayer.setGodCard(selectedGodList.get(0));
                    selectedGodList.remove(selectedGodList.get(0));
                    godsAssigned=true;
                    model.updateModelRep(stateID);
                    return;
                }
                model.updateModelRep(selectedGodList);
            }
            else
            {
                model.notify(new MessageToVirtualView(new SentChoiceError()));
                throw new WrongChoiceException("it's not your turn to choose the god");
            }
        }
        //the current player is the one who have to chose the first player
        if(godsSelected && godsAssigned && userChoice.toString().equals("InitialPlayerChoice"))
        {
            InitialPlayerChoice c= (InitialPlayerChoice)userChoice;
            model.getTurn().setIdFirstPlayer(c.getChoice());
            firstPlayerSelected=true;
            model.getTurn().resetTurnCounter();
            model.updateModelRep(stateID);
        }


        if(godsSelected && godsAssigned && firstPlayerSelected && userChoice.toString().equals("SelectWorkerCellChoice"))
        {
            if( userChoice.toString().equals("SelectWorkerCellChoice")) {
                castedChoice = (SelectWorkerCellChoice) userChoice;
                Box selectedCell;
                //Initialize worker in the selected position if the cell is free
                try {
                    selectedCell = model.getTurn().getBoardInstance().getBox(castedChoice.x, castedChoice.y);
                } catch (IndexOutOfBoundsException ex) {
                    model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                    throw new WrongChoiceException("Invalid coordinates for Worker: " + castedChoice.x + "," + castedChoice.y);
                }

                if (selectedCell.isOccupied()) {
                    model.notify(new MessageToVirtualView(new SelectedCellErrorMessage()));
                    throw new BoxAlreadyOccupiedException("Another worker already occupies this Box!");
                } else {
                    boxList.add(selectedCell);
                }

                if (boxList.size() == 2) {
                    actingPlayer.setWorkersPosition(boxList.get(0), boxList.get(1));
                    boxList.clear();

                    //Exits out of SetUpState if the actingPlayer is the last
                    if (actingPlayer == playerList.get(playerList.size() - 1)) {
                        hasFinished = true;
                    }
                    //the set next player doesn't go in the else branch because it has to be done in any case otherwise after the
                    //positioning oth the last player's worker the current player it's not updated but remains the last;
                    model.getTurn().setNextPlayer();
                    model.updateModelRep(stateID);


                }
            }
            else
            {
                model.notify(new MessageToVirtualView(new SentChoiceError()));
                throw new WrongChoiceException("Wrong Choice Type! Expected: SelectWorkerCellChoice," +
                        "Received: "+ userChoice.toString());
            }
        }

    }
}
