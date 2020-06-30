package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TowerCompleteException;

/**
 * The following class represents the enumeration of the possible Blocks
 */
public enum Block {
    LEVEL1,LEVEL2,LEVEL3,DOME;

    /**
     * given
     * @param base the last Block of the tower
     * @return the next block of the tower
     * @throws TowerCompleteException if the base is a dome, because that is supposed to be the last block
     * on a tower
     */
    public static Block getNextBlock(Block base)throws TowerCompleteException
    {
        switch (base){
            case LEVEL1:
                return Block.LEVEL2;
            case LEVEL2:
                return Block.LEVEL3;
            case LEVEL3:
                return Block.DOME;
            case DOME:
                throw new TowerCompleteException("you are trying to build over a dome");

        }
        return null;
    }

}
