package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ExitChoice;
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
 * This class represents the window, where the first player
 * can choose the cards that will be used during the game
 */

public class CardSelectionWindow extends JFrame implements ActionListener,WindowInterface {
    public final int WIN_WIDTH = 1000;
    public final int WIN_HEIGHT = 700;
    ImageIcon blankCard =new ImageIcon(getClass().getResource("/BlankGod.png"));
    ImageIcon blankResizedIcon = resizeIcon(blankCard, 4);

    private ArrayList<GodsList> selectedCards = new ArrayList<>();
    private ArrayList<GodsList> allGods = new ArrayList<>(Arrays.asList(GodsList.values()));
    private JButton godButton=new JButton(getGodImage(allGods.get(0).getName()));
    private JLabel godName;
    private int godCounter = 0;
    private int playerNum;
    private JLabel[] godMiniatures = new JLabel[3];
    private JButton submitButton =  new JButton("Submit");
    private JButton cancelButton = new JButton("Cancel");
    private View view;
    private JFrame f;


   /* public static void main(String[] args) {
        new CardSelectionWindow(3);
    }*/

    /**
     * builds the window, where the player can slide
     * all the possible god cards, view the ones
     * he has already selected and eventually cancel
     * the selected cards and start again
     * @param playerNum number of players
     */

    public CardSelectionWindow(View view,int playerNum)
    {
        this.playerNum = playerNum;
        this.view=view;

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/CardSelWindow.png")));
        background.setBounds(0,0,1000,700);
        //Declaring needed constants
        blankCard = resizeIcon(new ImageIcon(getClass().getResource("/BlankGod.png")), (float) 1.25);
        int defaultCardWidth = blankCard.getIconWidth();
        int defaultCardHeight = blankCard.getIconHeight();

        int defaultIconWidth = blankResizedIcon.getIconWidth();
        int defaultIconHeight = blankResizedIcon.getIconHeight();

        //Declaring needed objects
        f=new JFrame("God Selection");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        godName = new JLabel(allGods.get(0).getName());
        godName.setHorizontalAlignment(JLabel.CENTER);
        JButton leftButton=new JButton("<");
        leftButton.setToolTipText("Switches to God on the left");
        leftButton.setActionCommand("before");
        godButton.setActionCommand("god");
        godButton.setToolTipText(allGods.get(0).getDesc());
        JButton rightButton=new JButton(">");
        rightButton.setActionCommand("next");
        rightButton.setToolTipText("Switches to God on the right");
        submitButton.setActionCommand("submit");
        submitButton.setEnabled(false);


        JLabel selectedGod1 = new JLabel("First God", blankResizedIcon, JLabel.CENTER);
        selectedGod1.setVerticalTextPosition(JLabel.TOP);
        selectedGod1.setHorizontalTextPosition(JLabel.CENTER);
        JLabel selectedGod2 = new JLabel("Second God", blankResizedIcon, JLabel.CENTER);
        selectedGod2.setVerticalTextPosition(JLabel.TOP);
        selectedGod2.setHorizontalTextPosition(JLabel.CENTER);
        JLabel selectedGod3 = new JLabel("Third God", blankResizedIcon, JLabel.CENTER);
        selectedGod3.setVerticalTextPosition(JLabel.TOP);
        selectedGod3.setHorizontalTextPosition(JLabel.CENTER);

        godMiniatures[0] = selectedGod1;
        godMiniatures[1] = selectedGod2;
        godMiniatures[2] = selectedGod3;

        cancelButton.setToolTipText("Cancels all selected cards");
        //By default it's disabled, only available after selecting god cards
        cancelButton.setEnabled(false);
        cancelButton.setActionCommand("cancel");




        //Setting relative objects coordinates
        final int centeredButtonY = WIN_HEIGHT / 16 + defaultCardHeight / 2 - WIN_HEIGHT / 12;
        godName.setBounds(WIN_WIDTH/2 - defaultCardWidth/2, 10, defaultCardWidth, 30);
        leftButton.setBounds(WIN_WIDTH/8, centeredButtonY, WIN_WIDTH/8,WIN_HEIGHT/6);
        godButton.setBounds(WIN_WIDTH/2 - defaultCardWidth/2,WIN_HEIGHT/16, defaultCardWidth, defaultCardHeight);
        rightButton.setBounds(WIN_WIDTH*6/8, centeredButtonY, WIN_WIDTH/8,WIN_HEIGHT/6);

        selectedGod1.setBounds(WIN_WIDTH/16,WIN_HEIGHT*29/32 - defaultIconHeight, defaultIconWidth,defaultIconHeight + 20);
        selectedGod2.setBounds(WIN_WIDTH*2/16+defaultIconWidth,WIN_HEIGHT*29/32 - defaultIconHeight, defaultIconWidth+20,defaultIconHeight + 20);
        selectedGod3.setBounds(WIN_WIDTH*3/16 + 2*defaultIconWidth,WIN_HEIGHT*29/32 - defaultIconHeight, defaultIconWidth+20,defaultIconHeight + 20);

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
        f.add(selectedGod1);
        f.add(selectedGod2);
        f.add(godName);

        if(playerNum == 3)
        {
            f.add(selectedGod3);
        }
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
             * closes the window game when a player decides to exit
             * and asks the player to confirm his choice
             * @param windowEvent window is being closed
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
     * sets the size for the icons
     */

    private ImageIcon resizeIcon(ImageIcon defaultScale, float scaleDownFactor)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( (int)(defaultScale.getIconWidth()/scaleDownFactor),
                (int)(defaultScale.getIconHeight()/scaleDownFactor),  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    /**
     * sets the path for the image
     * @param name god name
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
     * define the behaviour of the
     * window when a player makes an action
     * @param actionEvent event that occurs when
     *                    a player has clicked on a button
     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if("next".equals(actionEvent.getActionCommand()))
        {
            //clicked on nextButton
            if(godCounter == allGods.size()-1)
            {
                godCounter = 0;
            }
            else
                {
                    godCounter++;
                }

            godButton.setToolTipText(allGods.get(godCounter).getDesc());
            godButton.setIcon(getGodImage(allGods.get(godCounter).getName()));
            godName.setText(allGods.get(godCounter).getName());
        }
        else if("before".equals(actionEvent.getActionCommand()))
        {
            //god switch to the left
            if(godCounter == 0)
            {
                godCounter = allGods.size()-1;
            }
            else
                {
                    godCounter--;
                }
            godButton.setToolTipText(allGods.get(godCounter).getDesc());
            godButton.setIcon(getGodImage(allGods.get(godCounter).getName()));
            godName.setText(allGods.get(godCounter).getName());
        }
        else if("god".equals(actionEvent.getActionCommand())){
            //A god has been selected
            cancelButton.setEnabled(true);
            if(selectedCards.size()<playerNum)
            {
                if(!selectedCards.contains(allGods.get(godCounter)))
                {
                    ImageIcon smallIcon = resizeIcon(getGodImage(allGods.get(godCounter).getName()), 4);
                    godMiniatures[selectedCards.size()].setIcon(smallIcon);
                    selectedCards.add(allGods.get(godCounter));
                }
                if(selectedCards.size() == playerNum)
                {
                    submitButton.setEnabled(true);
                }
            }
            else
                {
                    System.out.println("MAXIMUM NUMBER OF CARDS REACHED!");
                }
        }
        else if("cancel".equals(actionEvent.getActionCommand()))
        {
            selectedCards.clear();
            for(int k=0; k< playerNum; k++)
            {
                godMiniatures[k].setIcon(blankResizedIcon);
            }
            cancelButton.setEnabled(false);
            submitButton.setEnabled(false);
        }
        else if("submit".equals(actionEvent.getActionCommand()))
        {
            if(selectedCards.size() != playerNum)
            {
                submitButton.setEnabled(false);
            }
            GodsCollectionChoice c=new GodsCollectionChoice(selectedCards);
            view.update(c);
            submitButton.setEnabled(false);
            cancelButton.setEnabled(false);
        }
        else
            {
                System.out.println("Command not recognized!");
            }
    }


    /**
     * updates the window according to the model representation
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
     * shows a message to the players
     * who are not choosing the god
     */

    @Override
    public void messagePrompt(String message) {
        JOptionPane.showMessageDialog(f,message+" is choosing the god");
    }
}
