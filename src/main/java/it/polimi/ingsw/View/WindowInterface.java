package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;

public interface WindowInterface {

    /**Updates window after having received ModelRep inside MessageToVirtualView**/
    public void updateWindow(MessageToVirtualView update);

    public void setWindowVisible();
    public void setWindowNotVisible();
    public void messagePrompt(String message);
}
