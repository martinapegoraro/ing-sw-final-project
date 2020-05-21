package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

public class CheckWinConditionState implements State{
    private boolean hasFinished;
    StateEnum stateId;

    public CheckWinConditionState(int number)
    {
        if(number == 1)
        {
            stateId = StateEnum.FirstCheckWinCondition;
        }
        else
            {
                stateId = StateEnum.SecondCheckWinCondition;
            }

        hasFinished = false;
    }

    @Override
    public StateEnum getID() {
        return stateId;
    }


    public void checkPanCondition(Model model)
    {
        GodsList currentGod = model.getTurn().getCurrentPlayer().getGod();
        if (currentGod == GodsList.PAN) {
            if (model.getTurn().getCurrentPlayer().isPanConditionTrue()) {
                model.getTurn().getCurrentPlayer().setHasWon();
            }
        }
    }

    public void checkChronusCondition(Model model, Board instance)
    {
        // checking the number of complete towers on the board to verify whether the effect of Chronus can be applied
        int towerNumber = 0;

        for (int j = 0; j < 5; j++)
        {
            for (int k = 0; k < 5; k++)
            {
                if(instance.getBox(j,k).getTower().getHeight() == 4 && instance.getBox(j,k).getTower().getPieces().contains(Block.DOME))
                {
                    towerNumber++;
                }
            }
        }

        //chronusEffect

        int playerNumber = model.getTurn().getPlayersList().size();
        for (int i = 0; i< playerNumber; i ++)
        {

            if(model.getTurn().getPlayersList().get(i).getGod() == GodsList.CHRONUS)
            {
                if(towerNumber == 5)
                {
                    model.getTurn().getPlayersList().get(i).setHasWon();
                }
            }
        }

    }

    @Override
    public void startup(Model model)
    {
        Board instance = model.getTurn().getBoardInstance();
        checkPanCondition(model);
        checkChronusCondition(model, instance);
        int playerNumber = model.getTurn().getPlayersList().size();
        for (int i = 0; i<playerNumber; i++)
        {
            if(model.getTurn().getPlayersList().get(i).getHasWon())
            {
                //the game ends
                model.getTurn().getBoardInstance().newBoard();
            }
        }
        hasFinished = true;
    }
    @Override
    public void update(Choice userChoice, Model model) {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in CheckWinConditionState! RECEIVED: " + userChoice.toString());
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }


}
