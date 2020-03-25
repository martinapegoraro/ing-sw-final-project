package it.polimi.ingsw.Module;

public enum Block {
    LEVEL1,LEVEL2,LEVEL3,DOME;

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
