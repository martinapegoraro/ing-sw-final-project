package it.polimi.ingsw.Model;

import it.polimi.ingsw.Utils.ErrorMessages.ErrorMessage;

public class MessageToVirtualView {
    private  ModelRepresentation modelRep;
    private  ErrorMessage errorMsg;

    public void MessageToVirualView(ModelRepresentation modelRep)
    {
        this.modelRep=modelRep;
        errorMsg=null;
    }
    public void MessageToVirualView(ErrorMessage errorMsg)
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
