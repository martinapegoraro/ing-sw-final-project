package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;

public class View extends Observable<Choice> implements Observer<MessageToVirtualView> {

    protected void processChoice(Choice choice)
    {
        notify(choice);
    }
    @Override
    public void update(MessageToVirtualView message) {

    }
}
