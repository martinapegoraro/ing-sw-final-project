package it.polimi.ingsw.Module;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private Board board;
    //interpreto il turno come un contatore che viene incrementato ogni volta che gioca un giocatore
    private int round;
    private List<Player> playerSet;
    //lista delle carte utilizzabili

    public Match(List<String> names)
    {
        board=board.getInstance();
        round=0;
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
    public int getRound()
    {
        return round;
    }
    public int getNumberPlayer(){
        return playerSet.size();
    }
    public void addPlayer(String name)
    {
        playerSet.add(new Player(playerSet.size(),name));
    }
    public Player getPlayerRound()
    {
        return playerSet.get(round%playerSet.size());
    }
    public Player switchPlayer()
    {
        round++;
        return getPlayerRound();
    }
}
