package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private  int nTurn;
    private Board boardInstance;
    private ArrayList<Player> listaGiocatori;
    private int idFirstPlayer;
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

    public void resetTurnCounter()
    {
        nTurn=0;
    }

    public void setIdFirstPlayer(int id)
    {
        idFirstPlayer=id;
    }


    public Player getCurrentPlayer()
    {
        for (Player p:listaGiocatori) {
            if (p.isPlayerActive())
                return p;
        }
        return null;
    }
    public Board getBoardInstance() {
        return boardInstance;
    }

    public GodsList getPlayerGod(Player player)
    {
        return player.getGod();
    }

    public void addPlayer(String name)throws ImpossibleAddAnotherPlayerException
    {
        if(listaGiocatori.size()<3)
            listaGiocatori.add(new Player(listaGiocatori.size(),name));
        else
            throw new ImpossibleAddAnotherPlayerException("max number of players already reached");
    }


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


    public List<Box> getPossibleBuildLocations(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        for (Box cell:boardInstance.getBorderBoxes(b)) {
            if(!cell.isOccupied())
                lista.add(cell);
        }
        return lista;
    }

    public List<Player> getPlayersList()
    {
        return listaGiocatori;
    }

    public Player getPlayer(int id)
    {
        return listaGiocatori.get(id);
    }

}
