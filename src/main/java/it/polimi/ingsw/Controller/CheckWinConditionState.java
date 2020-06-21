package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ErrorMessages.SentChoiceError;

/**
 * The CheckWinConditionState is used every time a player might have won
 */

public class CheckWinConditionState implements State{
    private boolean hasFinished;
    StateEnum stateId;

    /**
     * the builder id used to initialize the class
     * and to specify if the CheckWinCondition is the first or the second one.
     * The FirstCheckWinCondition is called after the move,
     * the SecondCheckWinCondition is called after the build
     * @param number
     * @param model
     */

    public CheckWinConditionState(int number,Model model)
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
        startup(model);
    }

    /**
     * returns the ID of the state
     * @return
     */

    @Override
    public StateEnum getID() {
        return stateId;
    }


    /**
     * checks whether the player has the god Pan and
     * if the boolean variable, which reports if the worker has moved down two
     * or more levels, is set as true, if so it changes the win condition
     *
     * @param model
     */

    public void checkPanCondition(Model model)
    {
        GodsList currentGod = model.getTurn().getCurrentPlayer().getGod();
        if (currentGod == GodsList.PAN) {
            if (model.getTurn().getCurrentPlayer().isPanConditionTrue()) {
                model.getTurn().getCurrentPlayer().setHasWon();
            }
        }
    }

    /**
     * checks whether a player has the god Chronus and
     * if there are five complete towers on the board,
     * if so it changes the win condition
     * @param model
     * @param instance
     */

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

    /**
     * checks each player's hasWon and hasLost variables
     * to determine if anyone has won or lost.
     * It can end the game
     * @param model
     */

    @Override
    public void startup(Model model)
    {
        Board instance = model.getTurn().getBoardInstance();
        checkPanCondition(model);
        checkChronusCondition(model, instance);
        int playerNumber = model.getTurn().getPlayersList().size();
        int lPlayer=0;
        for(int i=0;i<playerNumber;i++)
        {
            if(model.getTurn().getPlayersList().get(i).getHasLost() && !(model.getTurn().getCurrentPlayer().getNumber()==model.getTurn().getPlayersList().get(i).getNumber()))
                lPlayer++;
        }

        if(lPlayer==playerNumber-1)
            model.getTurn().getCurrentPlayer().setHasWon();

        for (int i = 0; i<playerNumber; i++)
        {
            if(model.getTurn().getPlayersList().get(i).getHasWon())
            {
                //the game ends
                Board.newBoard();

                for(int j=0;j<playerNumber;j++)
                {
                    if(j!=i)
                        model.getTurn().getPlayersList().get(i).setHasLost();
                }
            }
        }


        if (stateId == StateEnum.FirstCheckWinCondition){model.updateModelRep(StateEnum.FirstCheckWinCondition);}
        if (stateId == StateEnum.SecondCheckWinCondition){model.updateModelRep(StateEnum.SecondCheckWinCondition);}
        hasFinished = true;
    }

    /**
     * it sends a MessageToVirtualView if a choice is received during this state
     * @param userChoice
     * @param model
     */

    @Override
    public void update(Choice userChoice, Model model) {
        model.notify(new MessageToVirtualView(new SentChoiceError()));
        System.out.println("No choice can be received in CheckWinConditionState! RECEIVED: " + userChoice.toString());
    }

    /**
     * returns the boolean variable that is used to check if the state has finished
     * @return
     */

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }


}
