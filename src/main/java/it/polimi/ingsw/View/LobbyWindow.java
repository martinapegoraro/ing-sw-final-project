package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class represents the initial window of the game, the lobby window,
 * where the players decide their usernames and the number
 * of players they want to play with
 */

public class LobbyWindow extends JFrame implements WindowInterface{
    private JFrame lobbyWindowFrame;
    private View view;
    private BackgroundPanel backgroundPanel;
    private JPanel mainPanel;
    private JPanel fieldsPanel;
    private JPanel buttonPanel;

    private JLabel nameLabel;
    private JLabel numberLabel;
    private JLabel background;

    private JTextField nameTextField;
    private JTextField numberTextField;

    private JButton submitButton;
    private SubmitButtonListener submitButtonListener;

    /**
     * builds the lobby window, setting the background panel
     * and the button to submit the choice
     */

    public LobbyWindow(View view) throws IOException {
        this.view=view;

        lobbyWindowFrame = new JFrame("Santorini");
        lobbyWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundPanel = new BackgroundPanel("/background.png");
        backgroundPanel.setLayout(new GridLayout(1, 1, -1, -1));
        //da settare L'immagine di background
        lobbyWindowFrame.add(backgroundPanel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, -1, -1));
        mainPanel.setOpaque(false);
        backgroundPanel.add(mainPanel);

        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 2, -1, -1));
        fieldsPanel.setOpaque(false);
        mainPanel.add(fieldsPanel);

        nameLabel = new JLabel("Username:");
        nameLabel.setOpaque(false);
        numberLabel = new JLabel("Number:");
        numberLabel.setOpaque(false);
        nameTextField = new JTextField();
        nameTextField.setOpaque(false);
        numberTextField = new JTextField();
        numberTextField.setOpaque(false);

        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameTextField);
        fieldsPanel.add(numberLabel);
        fieldsPanel.add(numberTextField);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, -1, -1));
        buttonPanel.setOpaque(false);
        mainPanel.add(buttonPanel);
        submitButton = new JButton("Submit");
        submitButton.setOpaque(false);
        submitButton.setContentAreaFilled(false);
        submitButtonListener=new SubmitButtonListener();
        submitButton.addActionListener(submitButtonListener);
        submitButton.setActionCommand("submit");
        buttonPanel.add(submitButton);


        lobbyWindowFrame.pack();

    }

    /**
     * updates the lobby window,
     * according to the model representation
     */

    @Override
    public void updateWindow(MessageToVirtualView update) {
        if(!update.isModelRep())
        {
            JOptionPane.showMessageDialog(lobbyWindowFrame,update.getMessage().getMessage());
        }
    }

    /**
     * sets the window visible
     */

    public void setWindowVisible()
    {
        lobbyWindowFrame.setVisible(true);
    }

    /**
     * sets the window not visible
     */

    public void setWindowNotVisible()
    {
        lobbyWindowFrame.setVisible(false);
    }


    /**
     * shows a message to the player/players
     * who are not choosing the gods
     */

    @Override
    public void messagePrompt(String message) {
        JOptionPane.showMessageDialog(lobbyWindowFrame,message+" is choosing the gods");
    }


    /**
     * this class represents the listener of the submit button
     */

    public class SubmitButtonListener  implements ActionListener{

        /**
         * controls if the username and the number
         * of players inserted by the user are correct
         * and memorizes their choice
         */

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String playerName=nameTextField.getText();
            String playerNumber=numberTextField.getText();
            String message="";

            if(playerName.equals(""))
            {
                message="the name field is empty! \n";
            }
            if(playerNumber.equals("")||(Integer.parseInt(playerNumber)!=2&&(Integer.parseInt(playerNumber))!=3))
            {
                message+="Remember a game is composed by 2 or 3 players";
            }
            if (actionEvent.getActionCommand().equals("submit"))
            {
                Choice c=new PlayerNumberChoice(playerName,Integer.parseInt(playerNumber));
                view.setPlayerName(playerName);
                view.notify(c);
                submitButton.setEnabled(false);
            }
            else
            {
                JOptionPane.showMessageDialog(lobbyWindowFrame,message);
            }
        }



    }


}
