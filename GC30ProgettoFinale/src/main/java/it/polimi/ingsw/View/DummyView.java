package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.ModelRepresentation;

import java.util.Arrays;

//Dummy view for unpacking ModelRep type messages
public class DummyView implements Observer<ModelRepresentation> {
    int[][] workers;
    int[][] towers;
    int[][] activeCells;

    public DummyView()
    {

    }
    public static void main()
    {
        //Istanziare il Server per simulare la view
    }

    //Prints everything inside the model Representation
    public void showModel(ModelRepresentation modelRep)
    {
        System.out.println("PLAYERS AND CARDS:");
        System.out.println(Arrays.toString(modelRep.playersName));
        System.out.println(Arrays.toString(modelRep.godList));
        System.out.println("ACTIVE PLAYER: " + modelRep.activePlayer);
        System.out.println("\nACTIVE GOD CARDS: " + Arrays.toString(modelRep.activeGodsList));
        workers = modelRep.workerposition.clone();
        System.out.println("\n\nWORKER POSITIONS:");
        for(int[] row : workers)
        {
            System.out.println(Arrays.toString(row));
        }

        towers = modelRep.towerposition.clone();

        System.out.println("\n\nTOWER POSITIONS:");
        for(int[] row : towers)
        {
            System.out.println(Arrays.toString(row));
        }

        activeCells = modelRep.activeCells.clone();

        System.out.println("\n\nACTIVE CELLS:");
        for(int[] row : activeCells)
        {
            System.out.println(Arrays.toString(row));
        }
    }

    @Override
    public void update(ModelRepresentation message) {
        showModel(message);
    }
}
