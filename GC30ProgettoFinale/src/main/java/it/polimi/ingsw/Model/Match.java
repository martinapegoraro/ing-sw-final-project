package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private Board board;

    private List<Player> playerSet;
    //lista delle carte utilizzabili

    public Match(List<String> names)
    {
        board=board.getInstance();

        playerSet=new ArrayList<Player>();
        for (String n:names)
            addPlayer(n);
        //estrazione e scelta delle divinità
    }

    public List<Worker> getWorkersPlayer(int playerNumber)
    {
        return playerSet.get(playerNumber).getWorkerList();
    }
    public Board getBoard()
    {
        return board.getInstance();
    }

    public int getNumberPlayer(){
        return playerSet.size();
    }
    public void addPlayer(String name)
    {
        playerSet.add(new Player(playerSet.size(),name));
    }

    public Player getPlayerRound(int turn)
    {
        return playerSet.get(turn%playerSet.size());
    }
}
