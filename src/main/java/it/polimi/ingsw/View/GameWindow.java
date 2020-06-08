package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class GameWindow extends JFrame implements WindowInterface, ActionListener {
    //Graphic elements
        JLayeredPane boardContainer;
        JLabel boardBackGroudImage;
        JPanel footerContainer;
        JPanel playersSideBar;
        ArrayList<JLabel> opponentsMiniatures;
        JLabel playerCard;
        JButton exitButton;
        JTextArea msgToUser;
        JLabel currentState;
        JButton godSelect;
        JButton godRefuse;
        JLabel[][][] pieces3dMatrix; //Contains all the game blocks and pieces in a 3d matrix
    //the top level (0) holds workers, the other levels hold tower blocks

        JButton[][] boardButtons;

        //Variables
    int myID;

    public GameWindow(String[] playersName, ArrayList<GodsList> playersGods, int playerID) {
        myID = playerID;

        //Main JFrame setup
        this.setTitle("Santorini Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        boardContainer = new JLayeredPane();
        boardContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boardContainer.setBounds(260, 10, 480, 480);
        //boardContainer.setLayout(new GridLayout(5,5));
        boardContainer.setSize(480,480);

        boardBackGroudImage = new JLabel(resizeIcon(getResource("Board_cells"), 480,480));
        boardBackGroudImage.setBounds(260,10,480,480);

        //Creation of BoardContainer levels, TOP level (level 0) is made of transparent buttons to capture clicks
        Point origin = new Point(0,0);
        boardButtons = new JButton[5][5];

        for (int i = 0; i < 5; i++) {
            for(int k=0; k<5;k++)
            {
                JButton label = new JButton();
                label.addActionListener(this);
                label.setActionCommand(""+(i*5+k));
                label.setBounds(origin.x, origin.y, 96,96);
                label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
                //label.setText(""+(i*5+k));
                label.setOpaque(false);
                label.setContentAreaFilled(false);
                boardContainer.add(label, Integer.valueOf(5));
                boardButtons[i][k] = label;
                origin.x += 96;
            }
            origin.x = 0;
            origin.y += 96;
        }

        //The next five levels are: 1 worker, 2 dome, 3 third level, 4 second level and 5 ground level
        initialize3dMatrix();
        pieces3dMatrix[0][0][4].setVisible(true);
        pieces3dMatrix[0][0][0].setVisible(true);
        pieces3dMatrix[0][1][1].setVisible(true);
        //boardButtons[2][2].setBackground(new Color(208, 208, 0, 100));// it's probably better to add Labels on top level


        //FOOTER AND OBJECTS INSIDE
        footerContainer = new JPanel();
        footerContainer.setLayout(new GridLayout(0,4));
        footerContainer.setBounds(0,500,780,165);
        footerContainer.setLayout(new BorderLayout());
        footerContainer.setBorder(BorderFactory.createLineBorder(Color.RED));
        initializeFooter();

        //PLAYERS SIDEBAR AND PLAYER LABELS
        playersSideBar = new JPanel();
        playersSideBar.setBounds(10,10,200, 480);
        playersSideBar.setLayout(new FlowLayout());
        playersSideBar.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        initializePlayerSidebar(playersName, playersGods, playerID);

        //PLAYER CARD AND NAME
        playerCard = new JLabel();
        playerCard.setBounds(1000-180-30,700-300-70, 180, 320);
        playerCard.setText(playersName[playerID]);
        ImageIcon playersIcon = getResource(playersGods.get(playerID).toString());
        playerCard.setIcon(resizeIcon(playersIcon, 180, 300));
        playerCard.setVerticalTextPosition(JLabel.TOP);
        playerCard.setHorizontalTextPosition(JLabel.CENTER);
        playerCard.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        //EXIT BUTTON
        exitButton = new JButton("EXIT");
        exitButton.setBounds(1000-180-30, 10, 180, 50);


        //Packing all the objects
        this.add(playersSideBar);
        //The order here matters! boardContainer must be painted on JFrame before backGround
        this.add(boardContainer);
        this.add(boardBackGroudImage);
        //And playerCard has to be before footerContainer
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
//_________________________________________INITIALIZER METHODS________________________________________________________

    private void initializePlayerSidebar(String[] playersName, ArrayList<GodsList> playersGods, int playerID)
    {
        int counter = 0;
        JLabel temp;
        ImageIcon opponentGodIcon;
        opponentsMiniatures = new ArrayList<>();
        if(playersName.length == 2 || playersName.length == 3)
        {
            while(counter < playersName.length)
            {
                if(counter!=playerID){
                    opponentGodIcon = getResource(playersGods.get(counter).toString());
                    opponentGodIcon = resizeIcon(opponentGodIcon, 130, 200);
                    temp = new JLabel(playersName[counter], opponentGodIcon, JLabel.CENTER);
                    temp.setVerticalTextPosition(JLabel.BOTTOM);
                    temp.setHorizontalTextPosition(JLabel.CENTER);
                    temp.setIconTextGap(10);

                    playersSideBar.add(temp);
                    opponentsMiniatures.add(temp);
                }
                counter++;
            }

        }
        else
            {
                System.out.println("ERROR, wrong number of players!");
            }
    }

    private void initializeFooter()
    {
        msgToUser = new JTextArea(8,30);
        msgToUser.setText("Testo di prova, \nqui andranno a comparire i messaggi\n di errore e conferma per l'utente");
        //msgToUser.setSize(300, 180);
        msgToUser.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(msgToUser);
        scrollPane.createVerticalScrollBar();

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(2,0));
        //buttonContainer.setPreferredSize(new Dimension(200,150));

        currentState = new JLabel("Current State: STARTUP");
        //currentState.setHorizontalTextPosition(JLabel.CENTER);
        currentState.setHorizontalAlignment(JLabel.CENTER);
        currentState.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        godSelect = new JButton("ACTIVATE");
        godRefuse = new JButton("DEACTIVATE");

        buttonContainer.add(godSelect);
        buttonContainer.add(godRefuse);

        footerContainer.add(scrollPane, BorderLayout.WEST);
        footerContainer.add(currentState, BorderLayout.CENTER);
        footerContainer.add(buttonContainer, BorderLayout.EAST);

    }

    private void initialize3dMatrix()
    {
        //The five Z levels are: 4 worker, 3 dome, 2 third level, 1 second level and 0 ground level
        ImageIcon workerIcon = resizeIcon(getResource("WorkerPurpleM_Cropped"), 60,90);
        //Workers will probably be painted later, this is just for testing
        ImageIcon domeIcon = resizeIcon(getResource("Dome"), 90,90);
        ImageIcon thirdFloorIcon = resizeIcon(getResource("Tower3"), 90,90);
        ImageIcon secondFloorIcon = resizeIcon(getResource("Tower2"), 90,90);
        ImageIcon groundFloorIcon = resizeIcon(getResource("Tower1"), 90,90);

        ImageIcon[] iconArray = new ImageIcon[5];
        iconArray[4] = workerIcon;
        iconArray[3] = domeIcon;
        iconArray[2] = thirdFloorIcon;
        iconArray[1] = secondFloorIcon;
        iconArray[0] = groundFloorIcon;

        pieces3dMatrix = new JLabel[5][5][5];
        Point origin = new Point(0,0);

        //the matrix is initialized with every block set to transparent but icons are already present
        for (int i = 0; i < 5; i++) {
            for(int k= 0; k<5; k++)
            {
                for(int j=0;j<5;j++)
                {
                    //k is the row, j the column on the board and i the layer
                    JLabel entity = new JLabel();
                    entity.setBounds(origin.x, origin.y, 96,96);
                    entity.setIcon(iconArray[i]);
                    entity.setVisible(false);
                    entity.setHorizontalAlignment(JLabel.CENTER);
                    entity.setVerticalAlignment(JLabel.CENTER);
                    pieces3dMatrix[k][j][i] = entity;
                    boardContainer.add(entity, Integer.valueOf(i));
                    origin.x += 96;
                }
                origin.x = 0;
                origin.y += 96;
            }
            origin.x = 0;
            origin.y = 0;
            /*JButton label = new JButton();
            label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setText(""+i);
            label.setOpaque(false);
            label.setContentAreaFilled(false);
            boardContainer.add(label, Integer.valueOf(0));*/
        }
    }

    private void updateTowers(int[][] towerPositions, String[][] lastBlock)
    {
        //Last block needs to be checked just if it's a Dome
    }
 //__________________________________________UTILITY METHODS__________________________________________________________

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
    }
}
