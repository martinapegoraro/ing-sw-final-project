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

    /**
     * creates a MessageToVirtualView containing a ModelRepresentation
     * @param modelRep
     */
    public  MessageToVirtualView(ModelRepresentation modelRep)
    {
        this.modelRep=modelRep;
        errorMsg=null;
    }

    /**
     * creates a Message To Virtual view containing an error message
     * @param errorMsg
     */
    public  MessageToVirtualView(ErrorMessage errorMsg)
    {
        modelRep=null;
        this.errorMsg=errorMsg;
    }

    /**
     * @return if the MessageToVirtualView contains a ModelRepresentation
     * else it returns false
     */
    public boolean isModelRep()
    {
        if(modelRep!=null)
            return true;
        else
            return false;
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
}
