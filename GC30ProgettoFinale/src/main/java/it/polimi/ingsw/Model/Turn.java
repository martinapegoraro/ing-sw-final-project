package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.ImpossibleAddAnotherPlayerException;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private  int nTurn;
    private Board boardInstance;
    private ArrayList<Player> listaGiocatori;
    public Turn(List<String> playersNamesList)
    {
        nTurn=0;
        boardInstance=Board.getInstance();
        listaGiocatori=new ArrayList<Player>();
        for (String name:playersNamesList) {
            try {
                addPlayer(name);
            } catch (ImpossibleAddAnotherPlayerException e) {
                break;
            }
        }
        setNextPlayer();
    }

    public void setNextPlayer()
    {
        for (Player p:listaGiocatori) {
            p.setPlayerActive(false);
        }
        listaGiocatori.get(nTurn%listaGiocatori.size()).setPlayerActive(true);
    }

    public Player getCurrentPlayer()
    {
        for (Player p:listaGiocatori) {
            if (p.isPlayerActive())
                return p;
        }
        return null;
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
        for (Box cell:boardInstance.getBorderBoxes(b)) {
            if(!cell.isOccupied() && cell.isReachable(b))
                lista.add(b);
        }
        return lista;

    }
    public List<Box> getPossibleBuildLocations(Box b)
    {
        List<Box> lista=new ArrayList<Box>();
        for (Box cell:boardInstance.getBorderBoxes(b)) {
            if(!cell.isOccupied())
                lista.add(b);
        }
        return lista;
    }

}
