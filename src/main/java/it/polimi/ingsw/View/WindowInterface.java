package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;

public interface WindowInterface {

    /**Updates window after having received ModelRep inside MessageToVirtualView**/
    public void updateWindow(MessageToVirtualView update);

    /**
     * sets the window visible
     */
    public void setWindowVisible();

    /**
     * sets the window not visible
     */

    public void setWindowNotVisible();

    /**
     * shows messages
     */
    public void messagePrompt(String message);
}
