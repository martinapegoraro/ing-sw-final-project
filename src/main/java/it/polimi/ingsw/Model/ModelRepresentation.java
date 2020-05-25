package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Context;
import it.polimi.ingsw.Controller.State;
import it.polimi.ingsw.Controller.StateEnum;

import java.io.Serializable;
import java.util.List;

public class ModelRepresentation implements Serializable {
    public int[][] workerposition;
    public int[][] towerposition;
    public String[] godList;
    public String[] playersName;
    public int playerNum;
    public int activePlayer;
    public boolean[] activeGodsList;
    public String[][] lastBlock;
    //activeCells is used to show clients which moves are possible highlighting the spaces
    //0 means the box is not selected
    public int[][] activeCells;
    //arrays with players' flags hasWon and hasLost
    public boolean[] hasWon;
    public boolean[] hasLost;
    public StateEnum currentState;

    public ModelRepresentation(Board instance, List<Player> players, int[][] selectedCells)
    {
        playerNum = players.size();

        activeCells = selectedCells.clone();
        workerposition = new int [5][5];
        //matrix contains -1 when no worker is present
        // or a player's ID when a worker is present
        for (int i = 0; i<=4; i++)
        {
            for (int k = 0; k<=4; k++)
            {
                workerposition[i][k] = -1;

            }
        }
        for (Player player : players) {
            List<Worker> workers = player.getWorkerList();
            for (Worker worker : workers) {
               //when the modelRep is created for the first time the workers aren't declared yet,
                //so the worker position matrix remains of all -1
                if(worker!=null) {
                    int[] casella = worker.getPosition().getCoord();
                    workerposition[casella[0]][casella[1]] = player.getNumber();
                }

            }
        }

        towerposition = new int[5][5]; // matrix contains -1 when no tower is present
                                        //when a tower is present, it contains the height of the tower
        for (int i = 0; i<=4; i++)
        {
            for (int k = 0; k<=4; k++)
            {
                    towerposition [i][k] = instance.getBox(i,k).getTower().getHeight();

            }
        }

        godList = new String[playerNum];
        for (int i =0; i < players.size(); i++)
        {
            //as the workers list the first time the god instances are null
            if(players.get(i).getGod()!=null) {
                godList[i] = players.get(i).getGod().getName();
            }
        }

        playersName = new String[playerNum];
        for (int i = 0; i<players.size(); i++)
        {
            playersName[i] = players.get(i).getPlayerName();
        }



        for(Player player : players)
        {
            if (player.isPlayerActive())
            {
                activePlayer = player.getNumber();
            }
        }

        activeGodsList = new boolean[playerNum];
        for (int i = 0; i < players.size(); i ++)
        {
            activeGodsList[i] = players.get(i).isGodActive();
        }

        lastBlock = new String[5][5];

        for (int i = 0; i<=4; i++)
        {
            for (int k = 0; k<=4; k++)
            {
                    if (instance.getBox(i,k).getTower().getHeight() == 1) {lastBlock[i][k] = "Level 1";}
                    if (instance.getBox(i,k).getTower().getHeight() == 2) {lastBlock[i][k] = "Level 2";}
                    if (instance.getBox(i,k).getTower().getHeight() == 3) {lastBlock[i][k] = "Level 3";}
                    if (instance.getBox(i,k).getTower().getHeight() == 4) {lastBlock[i][k] = "Dome";}
                    else lastBlock[i][k] = "Ground";

            }
        }

        hasWon = new boolean[playerNum];
        for (int i = 0; i < players.size(); i ++)
        {
            hasWon[i] = players.get(i).getHasWon();
        }

        hasLost = new boolean[playerNum];
        for (int i = 0; i < players.size(); i ++)
        {
            hasLost[i] = players.get(i).getHasLost();
        }


    }

    public int[][] getWorkerPosition()
    {
        return workerposition;
    }

    public int[][] getTowerPosition()
    {
        return towerposition;
    }

    public String[] getGodList()
    {
        return godList;
    }

    public String[] getPlayersName()
    {
        return playersName;
    }

    public int getPlayerNum()
    {
        return playerNum;
    }

    public int getActivePlayer()
    {
        return activePlayer;
    }

    public boolean[] getActiveGodsList()
    {
        return activeGodsList;
    }

    public String[][] getLastBlock()
    {
        return lastBlock;
    }

    public boolean[] getHasWon() { return hasWon;}

    public boolean[] getHasLost() { return hasLost; }

    public  StateEnum getCurrentState() {return  currentState;}
}
