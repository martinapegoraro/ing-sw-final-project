package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class CheckWinConditionState implements State{
    private boolean hasFinished;
    StateEnum stateId;

    public CheckWinConditionState()
    {
        stateId = StateEnum.CheckWinCondition;
        hasFinished = false;
    }

    @Override
    public StateEnum getID() {
        return stateId;
    }


    public void CheckWinConditionState( Model model)
    {
        GodsList currentGod = model.getTurn().getCurrentPlayer().getGod();
        if(currentGod == GodsList.PAN)
        {
            //ho commentato se no non compilava
            /*if ()  //TODO: manca il flag per cappire se il worker si è mosso in giù di due livelli
            {
               model.getTurn().getCurrentPlayer().setHasWon();
            }*/
        }
        int playerNumber = model.getTurn().getPlayersList().size();
        for (int i = 0; i< playerNumber; i ++)
        {
            if(model.getTurn().getPlayersList().get(i).getGod() == GodsList.CHRONUS)
            {
                //commentato causa compilazione
                /*if() //TODO: manca il flag per vedere se ci sono 5 torri complete sulla Board
                {
                    model.getTurn().getPlayersList().get(i).setHasWon();
                }*/
            }
        }


    }
    @Override
    public void startup(Model model)
    {
        int playerNumber = model.getTurn().getPlayersList().size();
        for (int i = 0; i<playerNumber; i++)
        {
            //if(model.getTurn().getPlayersList().get(i).getHasWon())
            //{
                //the game ends
                hasFinished = true;
            //}
        }
    }
    @Override
    public void update(Choice userChoice, Model model) {

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }


}
