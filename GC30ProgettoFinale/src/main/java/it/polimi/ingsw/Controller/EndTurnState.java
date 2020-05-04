package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Choice;

import java.util.List;

public class EndTurnState implements State {
    private StateEnum stateID;
    private boolean hasFinished;

    public EndTurnState()
    {

        stateID = StateEnum.EndTurn;
        hasFinished = false;
    }

    @Override
    public StateEnum getID() {
        return null;
    }

    @Override
    public void startup(Model model)
    {
        Player currentPlayer;
        currentPlayer = model.getTurn().getCurrentPlayer();
        currentPlayer.setPlayerActive(false);
        currentPlayer.changeAthenaCondition(false);
        currentPlayer.setSelectedWorker(null);
        List<Player> players = model.getTurn().getPlayersList();
        for (Player player : players)
        {
            player.setGodActive(false);
        }

        model.updateModelRep();
        hasFinished = true;
    }

    @Override
    public void update(Choice userChoice, Model model) {

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }
}