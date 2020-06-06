package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Utils.Choice;
import it.polimi.ingsw.Utils.ExitChoice;
import it.polimi.ingsw.Utils.GodsCollectionChoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CardSelectionWindow extends JFrame implements ActionListener,WindowInterface {
    public final int WIN_WIDTH = 1000;
    public final int WIN_HEIGHT = 700;
    ImageIcon blankCard = new ImageIcon("resources/BlankGod.png");
    ImageIcon blankResizedIcon = resizeIcon(blankCard, 4);

    ArrayList<GodsList> selectedCards = new ArrayList<>();
    ArrayList<GodsList> allGods = new ArrayList<>(Arrays.asList(GodsList.values()));
    JButton godButton=new JButton(getGodImage(allGods.get(0).toString()));
    int godCounter = 0;
    int playerNum;
    JLabel[] godMiniatures = new JLabel[3];
    JButton submitButton =  new JButton("Submit");
    JButton cancelButton = new JButton("Cancel");
    View view;
    JFrame f;


   /* public static void main(String[] args) {
        new CardSelectionWindow(3);
    }*/

    public CardSelectionWindow(View view,int playerNum)
    {
        this.playerNum = playerNum;
        this.view=view;
        //Declaring needed constants
        int defaultCardWidth = blankCard.getIconWidth();
        int defaultCardHeight = blankCard.getIconHeight();

        int defaultIconWidth = blankResizedIcon.getIconWidth();
        int defaultIconHeight = blankResizedIcon.getIconHeight();

        //Declaring needed objects
        f=new JFrame("God Selection");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton leftButton=new JButton("<");
        leftButton.setToolTipText("Switches to God on the left");
        leftButton.setActionCommand("before");
        godButton.setActionCommand("god");
        godButton.setToolTipText(allGods.get(0).getDesc());
        JButton rightButton=new JButton(">");
        rightButton.setActionCommand("next");
        rightButton.setToolTipText("Switches to God on the right");
        submitButton.setActionCommand("submit");


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

        if(playerNum == 3)
        {
            f.add(selectedGod3);
        }
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
        }
        else if("submit".equals(actionEvent.getActionCommand()))
        {
            GodsCollectionChoice c=new GodsCollectionChoice(selectedCards);
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
