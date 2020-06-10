package it.polimi.ingsw.Model;

import it.polimi.ingsw.Utils.ErrorMessages.ErrorMessage;

import java.io.Serializable;

/**
 * this class represents the message sent to the virtual view
 * the class contains two different objects
 * A modelRepresentation of an ErrorMessage but only one at time
 * instantiated an object MessageToVirtualView due the absence of set methods and the private attributes is not possible
 * change the content of the message
 */
public class MessageToVirtualView implements Serializable {
    private  ModelRepresentation modelRep;
    private  ErrorMessage errorMsg;
    private String playerName;

    /**
     * creates a MessageToVirtualView containing a ModelRepresentation
     * @param modelRep contains model representation
     */
    public  MessageToVirtualView(ModelRepresentation modelRep)
    {
        this.modelRep=modelRep;
        errorMsg=null;
        playerName = null;
    }

    /**
     * creates a MessageToVirtualView addressed to a single player
     * @param modelRep contains model representation
     * @param messageTo contains the Player recipient of the message**/
    public MessageToVirtualView(ModelRepresentation modelRep, Player messageTo)
    {
        this.modelRep = modelRep;
        errorMsg = null;
        playerName = messageTo.getPlayerName();
    }

    /**
     * creates a Message To Virtual view containing an error message
     * @param errorMsg contains the error
     * @param messageTo contains the Player recipient of the message
     */
    public  MessageToVirtualView(ErrorMessage errorMsg, Player messageTo)
    {
        modelRep=null;
        this.errorMsg=errorMsg;
        playerName = messageTo.getPlayerName();
    }

    /**
     * creates a Message To Virtual view containing an error message
     * @param errorMsg contains the error
     */
    public  MessageToVirtualView(ErrorMessage errorMsg)
    {
        modelRep=null;
        this.errorMsg=errorMsg;
        playerName = null;
    }

    /**
     * @return if the MessageToVirtualView contains a ModelRepresentation
     * else it returns false
     */
    public boolean isModelRep()
    {
        return modelRep != null;
    }

    /**
     * @return the errorMessage
     */
    public ErrorMessage getMessage()
    {
        return errorMsg;
    }

    /**
     * @return the model representation
     */
    public ModelRepresentation getModelRep()
    {
        return modelRep;
    }

    /**
     * @return the player whom the message is directed to*/
    public String getPlayerName(){return playerName;}
}
