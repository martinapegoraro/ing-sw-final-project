package it.polimi.ingsw.View;

import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.PlayerNumberChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


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

    public LobbyWindow(View view) throws IOException {
        this.view=view;

        lobbyWindowFrame = new JFrame("Santorini");
        lobbyWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundPanel = new BackgroundPanel("resources/background.png");
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
        buttonPanel.add(submitButton);


        lobbyWindowFrame.pack();

    }

    public void setWindowVisible()
    {
        lobbyWindowFrame.setVisible(true);
    }

    public void setWindowNotVisible()
    {
        lobbyWindowFrame.setVisible(false);
    }



    @Override
    public void updateWindow() {

    }

    public class SubmitButtonListener  implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String playerName=nameTextField.getText();
            String playerNumber=numberTextField.getText();
            String message="";

            if(playerName.equals(""))
            {
                message="you have put your name \n";
            }
            if(playerNumber.equals("")||(Integer.parseInt(playerNumber)!=2&&(Integer.parseInt(playerNumber))!=3))
            {
                message+="Remember a game is composed by 2 or 3 players";
            }
            if (message.equals(""))
            {
                Choice c=new PlayerNumberChoice(playerName,Integer.parseInt(playerNumber));
                view.notify(c);

            }
            else
            {
                JOptionPane.showMessageDialog(lobbyWindowFrame,message);
            }
        }



    }


}
