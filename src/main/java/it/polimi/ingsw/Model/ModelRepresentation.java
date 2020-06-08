package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Context;
import it.polimi.ingsw.Controller.State;
import it.polimi.ingsw.Controller.StateEnum;

import java.io.Serializable;
import java.util.List;

/**
 * an instance of this class is a picture of the model
 * it's used as a container of all the information required by the view to graphically represents the game
 */
public class ModelRepresentation implements Serializable {
    public int[][] workerposition;
    public int[][] towerposition;
    public List<GodsList> gods;
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

    /**
     * given the parameters the builder creates the representation of the model
     * @param instance
     * @param players
     * @param selectedCells
     */
    public ModelRepresentation(Board instance, List<Player> players, int[][] selectedCells)
    {
        currentState = null;
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
                //Not a terribly efficient method but works fine and prevents order-related issues in getPieces()
                //from propagating
                    if (instance.getBox(i,k).getTower().getPieces().contains(Block.LEVEL1))
                    {
                        lastBlock[i][k] = "Level 1";
                    }
                    if (instance.getBox(i,k).getTower().getPieces().contains(Block.LEVEL2)) {
                        lastBlock[i][k] = "Level 2";
                    }
                    if (instance.getBox(i,k).getTower().getPieces().contains(Block.LEVEL3)) {
                        lastBlock[i][k] = "Level 3";
                    }
                    if (instance.getBox(i,k).getTower().getPieces().contains(Block.DOME)) {
                        lastBlock[i][k] = "Dome";
                    }
                    if(instance.getBox(i,k).getTower().getPieces().isEmpty())
                        lastBlock[i][k] = "Ground";

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

    /**
     * @return a matrix of int every worker is identified with the number of the player(0,1,2)
     * and the coordinates in the matrix are the coordinates of the box where the worker is positioned.
     */
    public int[][] getWorkerPosition()
    {
        return workerposition;
    }

    /**
     * @return a matrix of int every element of the matrix is the equivalent of a box
     * for every tower is saved the height of the tower so the possible values are
     * 0:ground
     * 1:Level 1
     * 2:level 2
     * 3:Level 3
     * 4:Dome
     */
    public int[][] getTowerPosition()
    {
        return towerposition;
    }

    /**
     * @return an array of strings that contains the names of the gods selected for the game
     * the position of the god in the array defines the ownership of the god so the god in godList[0] is owned by the
     * player 0 and so on.
     * in the first phase of the game this array will be empty and it will be filled run time when the player selects his god
     */
    public String[] getGodList()
    {
        return godList;
    }

    /**
     * @return an array containing the players names as the others arrays the position in the array defines the ownership
     */
    public String[] getPlayersName()
    {
        return playersName;
    }

    /**
     * @return the number of players in the current game it will be 2 or 3
     */
    public int getPlayerNum()
    {
        return playerNum;
    }

    /**
     * @return the index of the current player
     * the current player is the one who is supposed to play in the current turn
     */
    public int getActivePlayer()
    {
        return activePlayer;
    }

    /**
     * @return an array of boolean and every boolean represents if the index player has his god active or not.
     */
    public boolean[] getActiveGodsList()
    {
        return activeGodsList;
    }

    /**
     * for every cell return the last block of the tower in that box
     * this information can't be given by the tower position because same
     * gods break the construction rules
     * @return
     */
    public String[][] getLastBlock()
    {
        return lastBlock;
    }

    /**
     * for every player we can know if she or he has won
     */
    public boolean[] getHasWon() { return hasWon;}

    /**
     * for every player we can know if she or he has lost
     */
    public boolean[] getHasLost() { return hasLost; }

    /**
     * returns the current state of the controller
     */
    public  StateEnum getCurrentState() {return  currentState;}

    /**
     * return an array list that contains the gods selected by the player 0 at the beginning of the game
     * it will be used only in the first phase of the game
     */
    public List<GodsList> getGods() { return gods; }
}
