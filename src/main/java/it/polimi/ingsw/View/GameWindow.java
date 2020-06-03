package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.MessageToVirtualView;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements WindowInterface{
        JLayeredPane boardContainer;
        JLabel boardBackGroudImage;
        JPanel footerContainer;
        JPanel playersSideBar;
        JPanel playerCard;
        JButton exitButton;

    public GameWindow() {
        this.setTitle("Santorini Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        boardContainer = new JLayeredPane();
        boardContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boardContainer.setBounds(260, 10, 480, 480);
        boardContainer.setLayout(new GridLayout(5,5));
        boardContainer.setSize(480,480);

        boardBackGroudImage = new JLabel(resizeIcon(getResource("Board_cells"), 480,480));
        boardBackGroudImage.setBounds(260,10,480,480);

        for (int i = 0; i < 25; i++) {
            JLabel label = new JLabel();
            label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            boardContainer.add(label, Integer.valueOf(1));
        }

        footerContainer = new JPanel();
        footerContainer.setBounds(0,500,987,165);
        footerContainer.setBorder(BorderFactory.createLineBorder(Color.RED));

        playersSideBar = new JPanel();
        playersSideBar.setBounds(10,10,200, 480);
        playersSideBar.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        playerCard = new JPanel();
        playerCard.setBounds(1000-180-30,700-300-50, 180, 300);
        playerCard.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        exitButton = new JButton("EXIT");
        exitButton.setBounds(1000-180-30, 10, 180, 50);


        //Packing all the objects
        this.add(playersSideBar);
        //The order here matters! boardContainer must be painted on JFrame before backGround
        this.add(boardContainer);
        this.add(boardBackGroudImage);

        this.add(playerCard);
        this.add(footerContainer);
        this.add(exitButton);
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

    }

 /*   public void updateInfoPanel(ModelRepresentation modelRep)
    {

        playersList=new JList(modelRep.playersName.clone());
        currentPlayerTextField.setText(modelRep.playersName[modelRep.activePlayer]);
        currentStateTextField.setText(modelRep.currentState.toString());
        fieldPanel.getBox(2,2).setImage();
        this.pack();
    }

    public void updateGodsPanel(ModelRepresentation modelRep)
    {
        for (String god:modelRep.getGodList()) {
            godPanelList.add(new BackgroundPanel("resources/"+god+".png"));
        }
        for (BackgroundPanel b : godPanelList) {
            godsPanel.add(b);
        }
        this.pack();

    }*/

    private ImageIcon getResource(String name)
    {
        return new ImageIcon("resources/"+name+".png");
    }

    private ImageIcon resizeIcon(ImageIcon defaultScale, int scaleDownFactor)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( defaultScale.getIconWidth()/scaleDownFactor,
                defaultScale.getIconHeight()/scaleDownFactor,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    private ImageIcon resizeIcon(ImageIcon defaultScale, int width, int height)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( width,
                height,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    @Override
    public void updateWindow(MessageToVirtualView update) {

    }

    @Override
    public void setWindowVisible() {
        this.setVisible(true);
    }

    @Override
    public void setWindowNotVisible() {
        this.setVisible(false);
    }
}
