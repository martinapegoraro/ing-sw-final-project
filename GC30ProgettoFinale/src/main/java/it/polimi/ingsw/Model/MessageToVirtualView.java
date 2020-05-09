package it.polimi.ingsw.Model;

import it.polimi.ingsw.Utils.ErrorMessages.ErrorMessage;

import java.io.Serializable;

public class MessageToVirtualView implements Serializable {
    private  ModelRepresentation modelRep;
    private  ErrorMessage errorMsg;

    public  MessageToVirtualView(ModelRepresentation modelRep)
    {
        this.modelRep=modelRep;
        errorMsg=null;
    }
    public  MessageToVirtualView(ErrorMessage errorMsg)
    {
        modelRep=null;
        this.errorMsg=errorMsg;
    }

    public boolean isModelRep()
    {
        if(modelRep!=null)
            return true;
        else
            return false;
    }

    public ErrorMessage getMessage()
    {
        return errorMsg;
    }
    public ModelRepresentation getModelRep()
    {
        return modelRep;
    }
}
