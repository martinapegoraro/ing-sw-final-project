package it.polimi.ingsw.Model;

import java.util.List;

public class ModelRepresentation {
    public int[][] workerposition;
    public int[][] towerposition; //TODO: Aggiungere altezza torri oltre alla posizione
    public String[] godList;
    public String[] playersName;
    public int playerNum;
    public int activePlayer;
    public boolean[] activeGodsList;
    //activeCells is used to show clients which moves are possible highlighting the spaces
    //0 means the box is not selected
    public int[][] activeCells;

    public ModelRepresentation(Board instance, List<Player> players, int[][] selectedCells)
    {
        playerNum = players.size();

        activeCells = selectedCells.clone();
        workerposition = new int [5][5]; //matrice contentente -1 dove non è presente un worker, 0 dove è presente
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
                    workerposition[casella[0]][casella[1]] = 0;
                }

            }
        }

        towerposition = new int[5][5]; // come worker position ma con le torri
        for (int i = 0; i<=4; i++)
        {
            for (int k = 0; k<=4; k++)
            {
                if (instance.getBox(i,k).getTower() !=  null )
                {
                    towerposition [i][k] = 0;
                }
                else towerposition[i][k] = -1;

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
}
