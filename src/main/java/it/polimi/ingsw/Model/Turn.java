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
     */

    public Turn(List<String> playersNamesList)
    {
        nTurn=0;
        idFirstPlayer=0;
        Board.newBoard();
        boardInstance= Board.getInstance();
        listaGiocatori=new ArrayList<>();
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
     * Sets the first player using his/her Id
     * @param id the id of the player who starts
     */

    public void setIdFirstPlayer(int id)
    {
        idFirstPlayer=id;
    }


    /**
     * @return the current active player
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
     * @deprecated
     * @return the god chosen by a given player
     */

    public GodsList getPlayerGod(Player player)
    {
        return player.getGod();
    }

    /**
     * adds a player to the list of players
     * @param name players name
     * @throws ImpossibleAddAnotherPlayerException if three players are already in the game
     */

    public void addPlayer(String name)throws ImpossibleAddAnotherPlayerException
    {
        if(listaGiocatori.size()<3)
            listaGiocatori.add(new Player(listaGiocatori.size(),name));
        else
            throw new ImpossibleAddAnotherPlayerException("max number of players already reached");
    }

    /**
     * given a box, returns the list of the boxes a worker can move to around the selected box
     * @param b selected box
     * @return the list of the boxes a worker can move to
     */

    public List<Box> getPossibleMoves(Box b)
    {
        List<Box> lista=new ArrayList<>();
        List<Box> borderBoxes=boardInstance.getBorderBoxes(b);
        for (Box cell:borderBoxes) {
            if(!cell.isOccupied() && b.isReachable(cell))
                lista.add(cell);
        }
        return lista;

    }

    /**
     * given a box, returns the possible boxes a worker can build on around it
     * @param b selected box
     * @return possible boxes a worker can build on
     */

    public List<Box> getPossibleBuildLocations(Box b)
    {
        List<Box> lista=new ArrayList<>();
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
     * returns a player, given his ID
     */

    public Player getPlayer(int id)
    {
        return listaGiocatori.get(id);
    }

}
