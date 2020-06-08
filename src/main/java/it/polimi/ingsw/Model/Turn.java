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
     * the builder is used to initialize the first turn of the game
     * @param playersNamesList
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
        while(listaGiocatori.get(turnoDi).getHasLost())
           turnoDi=(turnoDi+1)%listaGiocatori.size();
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
     * this method sets the id of the player who starts
     * @param id
     */

    public void setIdFirstPlayer(int id)
    {
        idFirstPlayer=id;
    }


    /**
     * returns the current player
     * @return
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
     * returns the instance of the board
     * @return
     */

    public Board getBoardInstance() {
        return boardInstance;
    }

    /**
     * returns the god chosen by a given player
     * @param player
     * @return
     */

    public GodsList getPlayerGod(Player player)
    {
        return player.getGod();
    }

    /**
     * adds a player to the list of players
     * @param name
     * @throws ImpossibleAddAnotherPlayerException
     */

    public void addPlayer(String name)throws ImpossibleAddAnotherPlayerException
    {
        if(listaGiocatori.size()<3)
            listaGiocatori.add(new Player(listaGiocatori.size(),name));
        else
            throw new ImpossibleAddAnotherPlayerException("max number of players already reached");
    }

    /**
     * given a certain box, returns the list of the boxes a worker can move to
     * @param b
     * @return
     */

    public List<Box> getPossibleMoves(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        List<Box> borderBoxes=boardInstance.getBorderBoxes(b);
        for (Box cell:borderBoxes) {
            if(!cell.isOccupied() && cell.isReachable(b))
                lista.add(cell);
        }
        return lista;

    }

    /**
     * given a certain box, returns the possible boxes a worker can build on
     * @param b
     * @return
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
     * returns the list of players
     * @return
     */

    public List<Player> getPlayersList()
    {
        return listaGiocatori;
    }

    /**
     * returns a player, given his ID
     * @param id
     * @return
     */

    public Player getPlayer(int id)
    {
        return listaGiocatori.get(id);
    }

}
