package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Choice;

public class Context {
    private State currentState;
    private int numberofPlayers;

    public Context()
    {
        currentState = new SetUpState(numberofPlayers);
    }

    public void defaultStateChange()
    {
        State newState;
        switch(currentState.getID())
        {
            case 0:
                newState = new BeginTurnState();
                switchState(newState);
            case 1:
                newState = new ActivationGodState();
                switchState(newState);
            case 2:
                //TODO: Chiamo il metodo di default per i calcolo delle possibili mosse
                //Uso un metodo che calcola la lista di possibili mosse guardando quali divinità sono attive e
                //chiamando al getPossibleMoves del Model
                newState = new MoveState();
                switchState(newState);
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
        }
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
