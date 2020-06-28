package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the information of a turn
 */

public class Turn {
    private  int nTurn;
    private Board boardInstance;
    private ArrayList<Player> listaGiocatori;
    private int idFirstPlayer;

    /**
     * the constructor is used to initialize the first turn of the game given
     * @param playersNamesList list of  players
     */

    public Turn(List<String> playersNamesList)
    {
        nTurn=0;
        idFirstPlayer=0;
        boardInstance=Board.getInstance();
        listaGiocatori=new ArrayList<Player>();
        for (String name:playersNamesList) {
            try {
                addPlayer(name);
            } catch (ImpossibleAddAnotherPlayerException e) {
                break;
            }
        }
        listaGiocatori.get(0).setPlayerActive(true);
    }

    /**
     * this method is used to set the next player when the turn changes
     */

    public void setNextPlayer()
    {
        nTurn++;
        int turnoDi=(idFirstPlayer+nTurn)%listaGiocatori.size();
        for (Player p:listaGiocatori) {
            p.setPlayerActive(false);
        }
        while(listaGiocatori.get(turnoDi).getHasLost()) {
            turnoDi = (turnoDi + 1) % listaGiocatori.size();
            nTurn++;
        }
        listaGiocatori.get(turnoDi).setPlayerActive(true);

    }

    /**
     * the counter is resetted every time all the players (two or three)
     * have finished their turn
     */

    public void resetTurnCounter()
    {
        nTurn=0;
        listaGiocatori.get(idFirstPlayer).setPlayerActive(true);
    }

    /**
     * @return the id of the first player
     */
    public int getIdFirstPlayer()
    {
        return idFirstPlayer;
    }

    /**
     * this method sets the id of the player who starts given
     * @param id which is the player's ID
     */

    public void setIdFirstPlayer(int id)
    {
        idFirstPlayer=id;
    }


    /**
     * @return the current player
     */

    public Player getCurrentPlayer()
    {
        for (Player p:listaGiocatori) {
            if (p.isPlayerActive())
                return p;
        }
        return null;
    }

    /**
     * @return the instance of the board
     */

    public Board getBoardInstance() {
        return boardInstance;
    }

    /**
     * @return the god chosen by
     * @param player which is the given player
     */

    public GodsList getPlayerGod(Player player)
    {
        return player.getGod();
    }

    /**
     * adds
     * @param name which is a player's name
     * to the list of players
     * @throws ImpossibleAddAnotherPlayerException if the players' number is higher
     * then three
     */

    public void addPlayer(String name)throws ImpossibleAddAnotherPlayerException
    {
        if(listaGiocatori.size()<3)
            listaGiocatori.add(new Player(listaGiocatori.size(),name));
        else
            throw new ImpossibleAddAnotherPlayerException("max number of players already reached");
    }

    /**
     * given
     * @param b , a box,
     * @return  the list of the boxes a worker can move to
     */

    public List<Box> getPossibleMoves(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        List<Box> borderBoxes=boardInstance.getBorderBoxes(b);
        for (Box cell:borderBoxes) {
            if(!cell.isOccupied() && b.isReachable(cell))
                lista.add(cell);
        }
        return lista;

    }

    /**
     * given
     * @param b , a box,
     * @return  the possible boxes a worker can build on
     */

    public List<Box> getPossibleBuildLocations(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        for (Box cell:boardInstance.getBorderBoxes(b)) {
            if(!cell.isOccupied())
                lista.add(cell);
        }
        return lista;
    }

    /**
     * @return the list of players
     */

    public List<Player> getPlayersList()
    {
        return listaGiocatori;
    }

    /**
     * given
     * @param id ,  a plyer's ID
     * @return a player
     */

    public Player getPlayer(int id)
    {
        return listaGiocatori.get(id);
    }

}
