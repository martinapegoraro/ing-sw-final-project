package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.BoxAlreadyOccupiedException;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * This class represents the player. It contains all
 * the characteristics that define a player, such has his username,
 * his god card and his ID number and a lot of useful variables
 * which define if the player is active, if his god card is active,
 * which worker he has selected, if he has lost/won and so on
 */

public class Player {
    private boolean isActive;
    private final String username;
    private GodsList card;
    private Worker[] workers = new Worker[2];
    private int idNumber; //Salvo il numero del giocatore per gestire eventuali elementi grafici
    private boolean godActive;
    private boolean athenaCondition;
    private boolean panCondition;
    private Worker selectedWorker;
    private boolean hasLost;
    private boolean hasWon;
    private Box lastMoveBox;
    private Box lastBuildBox;

    /**
     * @return lastMoveBox,
     * lastMoveBox is used by Artemis to prevent the player from moving back
     * to his old position*
     * */
    public Box getLastMoveBox() {
        return lastMoveBox;
    }


    /**Setter for lastMoveBox,
     * lastMoveBox is used by Artemis to prevent the player from moving back
     * to his old position**/
    public void setLastMoveBox(Box lastMoveBox) {
        this.lastMoveBox = lastMoveBox;
    }

    /**
     * @return lastBuildBox,
     * lastBuildBox is used by Demeter to prevent the player from building two times
     * on the same cell**/
    public Box getLastBuildBox() {
        return lastBuildBox;
    }

    /**Setter for lastBuildBox,
     * lastBuildBox is used by Demeter to prevent the player from building two times
     * on the same cell**/
    public void setLastBuildBox(Box lastBuildBox) {
        this.lastBuildBox = lastBuildBox;
    }


    /**Constructor for Player class, n is the Player ID (unique for each Player)
     * and name is the username (also unique)**/
    public Player(int n, String name)
    {
        idNumber = n;
        username = name;
        card=null;
        workers[0]=null;
        workers[1]=null;
        godActive=false;
        athenaCondition=false;
        panCondition = false;
        isActive=false;
        selectedWorker = null;
        hasLost = false;
        hasWon = false;
        lastMoveBox = null;
        lastBuildBox = null;
    }


    /**
     * @return true if the current player is active
     */
    public boolean isPlayerActive()
    {
        return isActive;
    }

    /**
     * this method sets the player active or not active
     */
    public void setPlayerActive(boolean active)
    {
        isActive=active;
    }

    /**
     * @return the name of the player
     */
    public String getPlayerName()
    {
        return username;
    }

    /**
     *
     * @return the number of the player it can be 0,1,2
     */
    public int getNumber()
    {
        return idNumber;
    } //TODO: Useful test to see if idNumber is in synch with Turn array

    /**
     *
     * @return an ArrayList the contains the workers of the player
     */
    public ArrayList<Worker> getWorkerList()
    {
        return new ArrayList<>(Arrays.asList(workers));
    }

    /**
     * method that sets the god of the player during the game
     */
    public void setGodCard(GodsList card)
    {
        this.card = card;
    }

    /**
     *
     * @return the god of the player
     */
     public GodsList getGod() {
        return card;
     }

    /**
     *
     * @return true if the athenaCondition for the player is active
     */
     public boolean isAthenaConditionTrue(){
        return athenaCondition;
     }

    /**
     * sets the active property of the player's god
     */
     public void setGodActive(boolean active)
     {
         godActive=active;
     }

    /**
     *
     * @return true if the player's god is active
     */
     public boolean isGodActive()
     {
         return godActive;
     }

    /**
     * sets true or false the athena condition of the player
     */
     public void changeAthenaCondition(boolean active)
     {
         athenaCondition=active;
     }

    /**
     * @return if the pan condition for the player is true or false
     */
     public boolean isPanConditionTrue() {return  panCondition;}

    /**
     * sets the pan condition of the player
     */
     public void changePanCondition (boolean active) {panCondition = active;}


    /**
     * given 2 boxes
     * @param b1 which is the first box
     * @param b2 which is the second box
     * the method sets the workers position
     */
    public void setWorkersPosition(Box b1,Box b2)
    {
        try
        {
            workers[0] = new Worker(b1);
            workers[1] = new Worker(b2);
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.println("Indice scorretto passato a setWorker!");
        }
        catch(BoxAlreadyOccupiedException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @return the selected worker
     */
    public Worker getSelectedWorker()
    {
        return selectedWorker;
    }

    /**
     * sets as selected a worker of the player
     */
    public void setSelectedWorker(Worker selected)
    {
        selectedWorker = selected;
    }

    /**
     * sets a worker as selected but this time given
     * @param n the index of the worker
     */
    public void setSelectedWorker(int n)
    {
        try
        {
            selectedWorker = workers[n];
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.println("Indice scorretto passato a setSelectedWorker!");
        }

    }


    /**
     *
     * @return if the player has won
     */
    public boolean getHasWon()
    {
        return hasWon;
    }

    /**
     *
     * sets  if the player has won
     */
    public void setHasWon()
    {
        hasWon=true;
    }

    /**
     *
     * @return if the player has lost
     */
    public boolean getHasLost()
    {
        return hasLost;
    }

    /**
     *
     * sets if the player has lost
     */
    public void setHasLost()
    {
        hasLost = true;
        deleteWorkers();
    }

    /**
     * this method is used in a three players game if one has lost
     * this method deletes the references of the workers of the player that has lost
     */
    private void deleteWorkers()
    {
        if (workers[0]!=null)
        {
            workers[0].getPosition().setNotOccupied();
            workers[0]=null;
        }
        if(workers[1]!=null) {
            workers[1].getPosition().setNotOccupied();
            workers[1] = null;
        }
    }

}
