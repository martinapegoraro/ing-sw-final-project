package it.polimi.ingsw.Model;

import java.util.List;

public class ModelRepresentation {
    public int[][] workerposition;
    public int[][] towerposition;
    public String[] godList;
    public String[] playersName;
    public int playerNum;
    public int activePlayer;
    public boolean[] activeGodsList;

    public ModelRepresentation(Board instance, List<Player> players)
    {
        workerposition = new int [5][5]; //matrice contentente -1 dove non è presente un worker, 0 dove è presente
        for (int i = 0; i<=5; i++)
        {
            for (int k = 0; k<=5; k++)
            {
                workerposition[i][k] = -1;

            }
        }
        for (Player player : players) {
            List<Worker> workers = player.getWorkerList();
            for (Worker worker : workers) {
                int[] casella= worker./*getposition.*/getCoord();
                workerposition[casella[0]][casella[1]] = 0;

            }
        }

        towerposition = new int[5][5]; // come worker position ma con le torri
        for (int i = 0; i<=5; i++)
        {
            for (int k = 0; k<=5; k++)
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
            godList[i] = players.get(i).getGod().getName();
        }

        playersName = new String[playerNum];
        for (int i = 0; i< players.size(); i++)
        {
            playersName[i] = players.get(i).getPlayerName();
        }

        playerNum = players.size();

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
}
