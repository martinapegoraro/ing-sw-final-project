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
    private JLabel playerListLabel;
    private JLabel currentPlayerLabel;
    private JLabel currentStateLabel;
    private JList playersList;
    private JTextField currentStateTextField;
    private JTextField currentPlayerTextField;
    private JButton[][] buttonsGrid;
    private ArrayList<BackgroundPanel> godPanelList;

    public GameWindow() {

        gameWindowFrame = new JFrame();
        gameWindowFrame = new JFrame("Santorini");
        gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindowFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, -1, -1));

        //creation infoPanel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2, -1, -1));
        playerListLabel=new JLabel("players list:");
        infoPanel.add(playerListLabel);
        //da aggiornare quando arriva il modeRep
        playersList=new JList();
        infoPanel.add(playersList);
        currentPlayerLabel=new JLabel("current Player:");
        infoPanel.add(currentPlayerLabel);
        currentPlayerTextField=new JTextField();
        infoPanel.add(currentPlayerTextField);
        currentStateLabel=new JLabel("state of the game");
        infoPanel.add(currentStateLabel);
        currentStateTextField=new JTextField();
        infoPanel.add(currentStateTextField);
        mainPanel.add(infoPanel);
        //gamePanel creation
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(2, 1, -1, -1));
        mainPanel.add(gamePanel);
        //creation of the fieldPanel
        fieldPanel = new BoardPanel();
        gamePanel.add(fieldPanel);
        godsPanel = new JPanel();
        //GodsPanel Creation
        godsPanel.setLayout(new GridLayout(1, 3, -1, -1));
        gamePanel.add(godsPanel);
        godPanelList = new ArrayList<BackgroundPanel>();
        gameWindowFrame.add(mainPanel);
        gameWindowFrame.pack();

    }

    public void updateInfoPanel(ModelRepresentation modelRep)
    {
        //todo to fix the list of the current players;
        DefaultListModel<String> tempList=new DefaultListModel<String>();
        for(String s:modelRep.playersName)
            tempList.addElement(s);
        playersList=new JList(tempList);

        currentPlayerTextField.setText(modelRep.playersName[modelRep.activePlayer]);
        currentStateTextField.setText(modelRep.currentState.toString());
        fieldPanel.getBox(2,2).setImage();
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
