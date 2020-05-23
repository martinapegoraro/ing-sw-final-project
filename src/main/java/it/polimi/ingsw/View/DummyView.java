package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.MoveErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Dummy view for unpacking ModelRep type messages
public class DummyView implements Observer<MessageToVirtualView> {
    int[][] workers;
    int[][] towers;
    int[][] activeCells;

    public DummyView()
    {

    }
    public void main()
    {
        boolean exitFlag = false;
        Scanner commandLine = new Scanner(System.in);
        String line;
        int x,y,x2,y2;
        //creates a test Model
        ArrayList<String> names = new ArrayList<>();
        names.add("Mario");
        names.add("Luigi");
        Model model = new Model(names);
        model.addObservers(this);

        //Saves players
        Player Mario = model.getTurn().getPlayer(0);
        Player Luigi = model.getTurn().getPlayer(1);

        //Players choose worker position
        System.out.println("--------PLAYER MARIO--------");
        System.out.println("Riga casella del worker: ");
        x = commandLine.nextInt();
        System.out.println("Colonna casella del worker: ");
        y = commandLine.nextInt();
        System.out.println("Riga casella del worker: ");
        x2 = commandLine.nextInt();
        System.out.println("Colonna casella del worker: ");
        y2 = commandLine.nextInt();
        Mario.setWorkersPosition(Board.getInstance().getBox(x,y), Board.getInstance().getBox(x2,y2));
        model.updateModelRep(model.getModelRep().currentState);

        System.out.println("--------PLAYER LUIGI--------");
        System.out.println("Riga casella del worker: ");
        x = commandLine.nextInt();
        System.out.println("Colonna casella del worker: ");
        y = commandLine.nextInt();
        System.out.println("Riga casella del worker: ");
        x2 = commandLine.nextInt();
        System.out.println("Colonna casella del worker: ");
        y2 = commandLine.nextInt();
        Luigi.setWorkersPosition(Board.getInstance().getBox(x,y), Board.getInstance().getBox(x2,y2));
        model.updateModelRep(model.getModelRep().currentState);

        //Implementing a basic user interface for using the Model
        while(!exitFlag)
        {
            boolean failed = true;
            System.out.println("--------------MARIO's TURN------------: ");
            System.out.println("Numero worker (0/1): ");
            x = commandLine.nextInt();
            Mario.setSelectedWorker(x);

            while(failed)
            {
                System.out.println("Riga della casella nella quale vuoi muovere: ");
                x = commandLine.nextInt();
                System.out.println("Colonna della casella nella quale vuoi muovere: ");
                y = commandLine.nextInt();
                try
                {
                    Mario.getSelectedWorker().move(Board.getInstance().getBox(x,y));
                    failed = false;
                }
                catch(MoveErrorException ex)
                {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }

            failed = true;

            while(failed)
            {
                System.out.println("Riga della casella nella quale vuoi costruire: ");
                x = commandLine.nextInt();
                System.out.println("Colonna della casella nella quale vuoi costruire: ");
                y = commandLine.nextInt();
                //TODO: Raise exceptions for build too
                Mario.getSelectedWorker().build(Board.getInstance().getBox(x,y));
                failed = false;
            }

            model.getTurn().setNextPlayer();
            System.out.println("--------------LUIGI's TURN------------: ");
            System.out.println("Numero worker (0/1): ");
            x = commandLine.nextInt();
            Luigi.setSelectedWorker(x);

            failed = true;
            while(failed)
            {
                System.out.println("Riga della casella nella quale vuoi muovere: ");
                x = commandLine.nextInt();
                System.out.println("Colonna della casella nella quale vuoi muovere: ");
                y = commandLine.nextInt();
                try
                {
                    Mario.getSelectedWorker().move(Board.getInstance().getBox(x,y));
                    failed = false;
                }
                catch(MoveErrorException ex)
                {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }

            failed = true;

            while(failed)
            {
                System.out.println("Riga della casella nella quale vuoi costruire: ");
                x = commandLine.nextInt();
                System.out.println("Colonna della casella nella quale vuoi costruire: ");
                y = commandLine.nextInt();
                //TODO: Raise exceptions for build too
                Mario.getSelectedWorker().build(Board.getInstance().getBox(x,y));
                failed = false;
            }

            model.getTurn().setNextPlayer();

            System.out.println("Si desidera uscire? (Y/N): ");
            if(commandLine.next().equals("Y") || commandLine.next().equals("y"))
            {
                exitFlag = true;
            }

        }
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
    public void update(MessageToVirtualView message) {
        showModel(message.getModelRep());
    }
}
