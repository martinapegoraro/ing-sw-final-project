package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ExitChoice;
import it.polimi.ingsw.Utils.GodChoice;
import it.polimi.ingsw.Utils.GodsCollectionChoice;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class represents the window where, in turn,
 * the second (and third) player can choose his god card
 */

public class GodSelectionWindow extends JFrame implements ActionListener,WindowInterface
{
    public final int WIN_WIDTH = 1000;
    public final int WIN_HEIGHT = 700;
    ImageIcon blankCard = new ImageIcon(getClass().getResource("/BlankGod.png"));
    ImageIcon blankResizedIcon = resizeIcon(blankCard, 4);

    ArrayList<GodsList> selectedCards = new ArrayList<>();
    JButton godButton;
    int godCounter = 0;
    int playerNum;
    JLabel[] godMiniatures = new JLabel[1];
    JButton submitButton =  new JButton("Submit");
    JButton cancelButton = new JButton("Cancel");
    View view;
    JFrame f;
    ModelRepresentation modelRep;

    /**
     * builds the window, where a player can slide the
     * possible god cards (which were chosen by the first player),
     * submit his choice and cancel the selected card and choose again
     * @param playerNum number of players
     */

    public GodSelectionWindow(View view, int playerNum, ModelRepresentation modelRep)
    {
        this.playerNum = playerNum;
        this.view=view;
        this.modelRep = modelRep;

        godButton=new JButton(getGodImage(modelRep.gods.get(0).getName()));

        //Declaring needed constants
        int defaultCardWidth = blankCard.getIconWidth();
        int defaultCardHeight = blankCard.getIconHeight();

        int defaultIconWidth = blankResizedIcon.getIconWidth();
        int defaultIconHeight = blankResizedIcon.getIconHeight();

        //Declaring needed objects
        f=new JFrame("God Selection");

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/GodSelWindow.png")));
        background.setBounds(0,0,1000,700);
        JButton leftButton=new JButton("<");
        leftButton.setToolTipText("Switches to God on the left");
        leftButton.setActionCommand("before");
        godButton.setActionCommand("god");
        godButton.setToolTipText(modelRep.gods.get(0).getDesc());
        JButton rightButton=new JButton(">");
        rightButton.setActionCommand("next");
        rightButton.setToolTipText("Switches to God on the right");
        submitButton.setActionCommand("submit");
        submitButton.setEnabled(false);


        JLabel chosenGod = new JLabel("Chosen God", blankResizedIcon, JLabel.CENTER);
        chosenGod.setVerticalTextPosition(JLabel.TOP);
        chosenGod.setHorizontalTextPosition(JLabel.CENTER);

        godMiniatures[0] = chosenGod;


        cancelButton.setToolTipText("Cancels the selected card");
        //By default it's disabled, only available after selecting god cards
        cancelButton.setEnabled(false);
        cancelButton.setActionCommand("cancel");




        //Setting relative objects coordinates
        final int centeredButtonY = WIN_HEIGHT / 16 + defaultCardHeight / 2 - WIN_HEIGHT / 12;
        leftButton.setBounds(WIN_WIDTH/8, centeredButtonY, WIN_WIDTH/8,WIN_HEIGHT/6);
        godButton.setBounds(WIN_WIDTH/2 - defaultCardWidth/2,WIN_HEIGHT/16, defaultCardWidth, defaultCardHeight);
        rightButton.setBounds(WIN_WIDTH*6/8, centeredButtonY, WIN_WIDTH/8,WIN_HEIGHT/6);

        chosenGod.setBounds(WIN_WIDTH/16,WIN_HEIGHT*29/32 - defaultIconHeight, defaultIconWidth,defaultIconHeight + 20);


        submitButton.setBounds(WIN_WIDTH*6/8, WIN_HEIGHT*12/16, 200, 50);
        cancelButton.setBounds(WIN_WIDTH*6/8, WIN_HEIGHT*14/16, 200, 50);

        //Setting objects properties
        godButton.isDefaultButton();
        leftButton.addActionListener(this);
        godButton.addActionListener(this);
        rightButton.addActionListener(this);
        cancelButton.addActionListener(this);
        submitButton.addActionListener(this);

        //Add objects to Frame
        f.add(leftButton);
        f.add(godButton);
        f.add(rightButton);
        f.add(chosenGod);
        f.add(submitButton);
        f.add(cancelButton);
        f.add(background);

        //Set frame properties
        //f.getContentPane().setBackground(Color.DARK_GRAY);
        //must be done before setting location relative to screen center (null)
        f.setSize(WIN_WIDTH,WIN_HEIGHT);
        f.setResizable(false);
        f.setLayout(null);
        //f terminates process when closed
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //f centers itself on screen
        f.setLocationRelativeTo(null);
        //the window will be enabled or disabled by the View
        //f.setVisible(true);
        f.addWindowListener(new java.awt.event.WindowAdapter(){

            /**
             * ends the game and closes the window,
             * asking the player to confirm his decision
             */
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                if(JOptionPane.showConfirmDialog(f,"Are you sure you want quit the game?","Quit the game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                    Choice c=new ExitChoice();
                    c.setId(playerNum);
                    view.update(c);
                    System.exit(0);
                }
            }
        });
    }

    /**
     * resize the icon
     */

    private ImageIcon resizeIcon(ImageIcon defaultScale, int scaleDownFactor)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( defaultScale.getIconWidth()/scaleDownFactor,
                defaultScale.getIconHeight()/scaleDownFactor,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    /**
     * @param name of the needed god
     * @return  the god image
     */

    private ImageIcon getGodImage(String name)
    {
        try {
            return new ImageIcon(ImageIO.read(getClass().getResource("/"+name+".png")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * updates the window, according to what the player has clicked on
     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if("next".equals(actionEvent.getActionCommand()))
        {
            //clicked on nextButton
            if(godCounter == modelRep.gods.size()-1)
            {
                godCounter = 0;
            }
            else
            {
                godCounter++;
            }

            godButton.setToolTipText(modelRep.gods.get(godCounter).getDesc());
            godButton.setIcon(getGodImage(modelRep.gods.get(godCounter).getName()));
        }
        else if("before".equals(actionEvent.getActionCommand()))
        {
            //god switch to the left
            if(godCounter == 0)
            {
                godCounter = modelRep.gods.size()-1;
            }
            else
            {
                godCounter--;
            }
            godButton.setToolTipText(modelRep.gods.get(godCounter).getDesc());
            godButton.setIcon(getGodImage(modelRep.gods.get(godCounter).getName()));
        }
        else if("god".equals(actionEvent.getActionCommand())){
            //A god has been selected
            cancelButton.setEnabled(true);
            submitButton.setEnabled(true);
            if(selectedCards.isEmpty())
            {
                    ImageIcon smallIcon = resizeIcon(getGodImage(modelRep.gods.get(godCounter).getName()), 4);
                    godMiniatures[selectedCards.size()].setIcon(smallIcon);
                    selectedCards.add(modelRep.gods.get(godCounter));
            }
            else
            {
                System.out.println("YOU CAN CHOOSE ONLY ONE GOD!");
            }
        }
        else if("cancel".equals(actionEvent.getActionCommand()))
        {
            selectedCards.clear();

            godMiniatures[0].setIcon(blankResizedIcon);

            cancelButton.setEnabled(false);
            submitButton.setEnabled(false);
        }
        else if("submit".equals(actionEvent.getActionCommand()))
        {
            if(selectedCards.size() == 0)
            {
                submitButton.setEnabled(false);
            }
            GodChoice c=new GodChoice(selectedCards.get(0).getName());
            c.setId(playerNum);
            view.update(c);
        }
        else
        {
            System.out.println("Command not recognized!");
        }
    }


    /**
     * updates the window, according to the model representation
     */

    @Override
    public void updateWindow(MessageToVirtualView update) {
        if(!update.isModelRep())
        {
            JOptionPane.showMessageDialog(f,update.getMessage().getMessage());
        }
    }

    /**
     * sets the window visible
     */

    @Override
    public void setWindowVisible() {
        f.setVisible(true);
    }

    /**
     * sets the window not visible
     */

    @Override
    public void setWindowNotVisible() {
        f.setVisible(false);
    }

    /**
     * shows a message to the players who are not choosing the god
     */

    @Override
    public void messagePrompt(String message) {
        JOptionPane.showMessageDialog(f,message+" is choosing the god");
    }
}
