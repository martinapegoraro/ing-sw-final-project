package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class Context {
    State currentState;

    public Context()
    {

    }

    public void defaultStateChange()
    {
        /*if(currentState instanceof setUpState){
          s = new BeginTurnState;
          SETTO GLI ATTRIBUTI A VALORI DI DEFAULT
          SwitchState(s);
          }
           ...

          */
    }

    public void switchState(State next)
    {
        currentState = next;
    }

    private void hephaestusEffect(){}

    public void update(Choice userChoice, Model model)
    {

    }
}
