package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ExitChoice;
import it.polimi.ingsw.Utils.GodChoice;
import it.polimi.ingsw.Utils.GodsCollectionChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GodSelectionWindow extends JFrame implements ActionListener,WindowInterface
{
    public final int WIN_WIDTH = 1000;
    public final int WIN_HEIGHT = 700;
    ImageIcon blankCard = new ImageIcon("resources/BlankGod.png");
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

    public GodSelectionWindow(View view, int playerNum, ModelRepresentation modelRep)
    {
        this.playerNum = playerNum;
        this.view=view;
        this.modelRep = modelRep;

        godButton=new JButton(getGodImage(modelRep.gods.get(0).toString()));

        //Declaring needed constants
        int defaultCardWidth = blankCard.getIconWidth();
        int defaultCardHeight = blankCard.getIconHeight();

        int defaultIconWidth = blankResizedIcon.getIconWidth();
        int defaultIconHeight = blankResizedIcon.getIconHeight();

        //Declaring needed objects
        f=new JFrame("God Selection");

        JButton leftButton=new JButton("<");
        leftButton.setToolTipText("Switches to God on the left");
        leftButton.setActionCommand("before");
        godButton.setActionCommand("god");
        godButton.setToolTipText(modelRep.gods.get(0).getDesc());
        JButton rightButton=new JButton(">");
        rightButton.setActionCommand("next");
        rightButton.setToolTipText("Switches to God on the right");
        submitButton.setActionCommand("submit");


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

    private ImageIcon resizeIcon(ImageIcon defaultScale, int scaleDownFactor)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( defaultScale.getIconWidth()/scaleDownFactor,
                defaultScale.getIconHeight()/scaleDownFactor,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    private ImageIcon getGodImage(String name)
    {
        return new ImageIcon("resources/"+name+".png");
    }

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
            if(selectedCards.size()<1)
            {
                if(!selectedCards.contains(modelRep.gods.get(godCounter)))
                {
                    ImageIcon smallIcon = resizeIcon(getGodImage(modelRep.gods.get(godCounter).getName()), 4);
                    godMiniatures[selectedCards.size()].setIcon(smallIcon);
                    selectedCards.add(modelRep.gods.get(godCounter));
                }
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

    public void godSelectionPrompt(String name)
    {
        JOptionPane.showMessageDialog(f,name+" is choosing the god");
    }

    @Override
    public void updateWindow(MessageToVirtualView update) {
        if(!update.isModelRep())
        {
            JOptionPane.showMessageDialog(f,update.getMessage().getMessage());
        }
    }

    @Override
    public void setWindowVisible() {
        f.setVisible(true);
    }

    @Override
    public void setWindowNotVisible() {
        f.setVisible(false);
    }
}
