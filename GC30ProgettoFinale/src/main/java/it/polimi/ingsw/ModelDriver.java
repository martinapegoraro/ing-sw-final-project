package it.polimi.ingsw;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.View.DummyView;

import java.util.ArrayList;
import java.util.Scanner;

/**This class is used to Unit test the functionalities of the Model
 * the Network is not present and Choice message control is not present**/

public class ModelDriver {
    public static void main()
    {
        Scanner commandLine = new Scanner(System.in);
        //I create a test Model
        ArrayList<String> names = new ArrayList<>();
        names.add("Mario");
        names.add("Luigi");
        Model model = new Model(names);
        DummyView view = new DummyView();
        model.addObservers(view);

        //Implementing a basic user interface for using the Model

    }
}
