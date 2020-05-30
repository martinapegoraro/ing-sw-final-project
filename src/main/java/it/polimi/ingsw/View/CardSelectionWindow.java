package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardSelectionWindow extends JFrame implements ActionListener {
    public final int WIN_WIDTH = 1000;
    public final int WIN_HEIGHT = 800;
    ImageIcon apollo = new ImageIcon("resources/Apollo.png");
    ImageIcon artemis = new ImageIcon("resources/Artemis.png");
    ImageIcon athena = new ImageIcon("resources/Athena.png");
    ImageIcon atlas = new ImageIcon("resources/Atlas.png");

    ArrayList<GodsList> selectedCards = new ArrayList<>();



    public static void main(String[] args) {
        new CardSelectionWindow(3);
    }

    public CardSelectionWindow(int playerNum)
    {
        //Declaring needed constants
        int defaultCardWidth = athena.getIconWidth();
        int defaultCardHeight = athena.getIconHeight();
        ImageIcon apolloResizedIcon = resizeIcon(apollo, 4);
        ImageIcon athenaResizedIcon = resizeIcon(athena, 4);
        int defaultIconWidth = athenaResizedIcon.getIconWidth();
        int defaultIconHeight = athenaResizedIcon.getIconHeight();

        //Declaring needed objects
        JFrame f=new JFrame("God Selection");
        JButton leftButton=new JButton("<");
        leftButton.setToolTipText("Switches to God on the left");
        leftButton.setActionCommand("before");
        JButton godButton=new JButton(apollo);
        godButton.setActionCommand("god");
        JButton rightButton=new JButton(">");
        rightButton.setActionCommand("next");
        rightButton.setToolTipText("Switches to God on the right");


        JLabel selectedGod1 = new JLabel("First God", apolloResizedIcon, JLabel.CENTER);
        selectedGod1.setVerticalTextPosition(JLabel.BOTTOM);
        selectedGod1.setHorizontalTextPosition(JLabel.CENTER);
        JLabel selectedGod2 = new JLabel("Second God", athenaResizedIcon, JLabel.CENTER);
        selectedGod2.setVerticalTextPosition(JLabel.BOTTOM);
        selectedGod2.setHorizontalTextPosition(JLabel.CENTER);
        JLabel selectedGod3 = new JLabel("Third God", apolloResizedIcon, JLabel.CENTER);
        selectedGod3.setVerticalTextPosition(JLabel.BOTTOM);
        selectedGod3.setHorizontalTextPosition(JLabel.CENTER);

        JButton submitButton =  new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");



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
        rightButton.addActionListener(this);

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
        f.setSize(1000,800);
        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
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
            //cliked on nextButton
            System.out.println("NEXT");
        }
        else if("before".equals(actionEvent.getActionCommand()))
        {
            System.out.println("BEFORE");
        }
    }
}
