package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.ModelRepresentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {
    private JFrame gameWindowFrame;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel gamePanel;
    private BoardPanel fieldPanel;
    private JPanel godsPanel;
    private JButton[][] buttonsGrid;
    private ArrayList<BackgroundPanel> godPanelList;

    public GameWindow() {

        gameWindowFrame = new JFrame();
        gameWindowFrame = new JFrame("Santorini");
        gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindowFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, -1, -1));
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2, -1, -1));
        mainPanel.add(infoPanel);
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(2, 1, -1, -1));
        mainPanel.add(gamePanel);
        fieldPanel = new BoardPanel();
        gamePanel.add(fieldPanel);
        godsPanel = new JPanel();
        godsPanel.setLayout(new GridLayout(1, 3, -1, -1));
        gamePanel.add(godsPanel);

        godPanelList = new ArrayList<BackgroundPanel>();


        gameWindowFrame.add(mainPanel);
        gameWindowFrame.pack();

    }

    public void updateGodsPanel(ModelRepresentation modelRep)
    {
        for (String god:modelRep.getGodList()) {
            godPanelList.add(new BackgroundPanel("resources/"+god+".png"));
        }
        for (BackgroundPanel b : godPanelList) {
            godsPanel.add(b);
        }
        gameWindowFrame.pack();

    }

    public void visible() {
        gameWindowFrame.setVisible(true);
    }
}
