package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.StateEnum;
import it.polimi.ingsw.Model.GodsList;
import it.polimi.ingsw.Model.MessageToVirtualView;
import it.polimi.ingsw.Model.ModelRepresentation;
import it.polimi.ingsw.Utils.*;
import it.polimi.ingsw.Utils.Choice;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the main window of
 * the game, where the actual game takes place
 */

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
    //the top Z level (0) holds workers, the other levels hold tower blocks

        JButton[][] boardButtons;

        //Variables
    private int myID;
    private String[] playersName;
    private int[][] myWorkerCells = new int[2][2]; //Saves the position of workers owned by the player sent with MsgToVirtualView
    private View view;
    private StateEnum presentState = StateEnum.SetUp;
    private int[][] activeCells;
    private boolean workerHasBeenSelected;
    private int selectedWorker;
    private boolean buildUnder;
    private boolean playerCanPlay = true;

    /**
     * builds the window, showing the board, the
     * players and their gods
     * @param playersName names of the players
     * @param playersGods gods associated to the players
     * @param playerID ID of the player
     */

    public GameWindow(String[] playersName, ArrayList<GodsList> playersGods, int playerID, View view) {
        workerHasBeenSelected = false;
        buildUnder=false;
        this.playersName = playersName;
        myID = playerID;
        this.view=view;
        activeCells=null;
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
                //Creates new JButton with the possibility of having transparent color on top
                JButton label = new JButton()
                {
                    protected void paintComponent(Graphics g)
                    {
                        g.setColor( getBackground() );
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponent(g);
                    }
                };
                label.addActionListener(this);
                label.setActionCommand(""+(i*5+k));
                //label.setBounds(origin.x, origin.y, 96,96);
                label.setBounds(origin.x, origin.y, 96,96);
                label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);

                //Without this line the buttons would be visible, I set the alpha value to 0
                //Using setContentAreaFilled(false) creates graphical glitches
                label.setBackground(new Color(0,0,0,0));
                //label.setText(""+(i*5+k));
                label.setOpaque(false);
                //label.setContentAreaFilled(false);
                boardContainer.add(label, Integer.valueOf(5));
                boardButtons[i][k] = label;
                origin.x += 96;
            }
            origin.x = 0;
            origin.y += 96;
        }

        //The next five levels are: 1 worker, 2 dome, 3 third level, 4 second level and 5 ground level
        initialize3dMatrix();
        //pieces3dMatrix[0][0][4].setVisible(true);
        //pieces3dMatrix[0][0][0].setVisible(true);
        //pieces3dMatrix[0][1][1].setVisible(true);
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
        ImageIcon playersIcon = getResource(playersGods.get(playerID).getName());
        playerCard.setIcon(resizeIcon(playersIcon, 180, 300));
        playerCard.setVerticalTextPosition(JLabel.TOP);
        playerCard.setHorizontalTextPosition(JLabel.CENTER);
        playerCard.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        playerCard.setBackground(Color.YELLOW);
        playerCard.setOpaque(false);


        //EXIT BUTTON
        exitButton = new JButton("EXIT");
        exitButton.setBounds(1000-180-30, 10, 180, 50);
        exitButton.addActionListener(this);
        exitButton.setActionCommand("Exit");

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                if(JOptionPane.showConfirmDialog(playersSideBar,"Are you sure you want quit the game?","Quit the game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                    if(playerCanPlay) {
                        Choice c = new ExitChoice();
                        c.setId(myID);
                        view.update(c);
                        System.exit(0);
                    }
                }

            }
        });

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

//_________________________________________INITIALIZER METHODS________________________________________________________

    /**
     * builds the sidebar which shows the other
     * players' names and their god cards
     * @param playersName name of the players
     * @param playersGods gods associated to the players
     * @param playerID ID of the player
     */

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
                    opponentGodIcon = getResource(playersGods.get(counter).getName());
                    opponentGodIcon = resizeIcon(opponentGodIcon, 130, 200);
                    temp = new JLabel(playersName[counter], opponentGodIcon, JLabel.CENTER);
                    temp.setVerticalTextPosition(JLabel.BOTTOM);
                    temp.setHorizontalTextPosition(JLabel.CENTER);
                    temp.setIconTextGap(10);
                    temp.setBackground(Color.YELLOW);
                    temp.setOpaque(false);
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

    /**
     * builds the footer where error messages
     * and confirmation messages are shown
     */

    private void initializeFooter()
    {
        msgToUser = new JTextArea(8,30);
        msgToUser.setText("Qui verranno visualizzati\n eventuali messaggi di <bold>errore</bold>");
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
        //Activating buttons effects
        godSelect.setActionCommand("Activate");
        godRefuse.setActionCommand("Deactivate");
        godSelect.addActionListener(this);
        godRefuse.addActionListener(this);
        godSelect.setEnabled(false);
        godRefuse.setEnabled(false);

        buttonContainer.add(godSelect);
        buttonContainer.add(godRefuse);

        footerContainer.add(scrollPane, BorderLayout.WEST);
        footerContainer.add(currentState, BorderLayout.CENTER);
        footerContainer.add(buttonContainer, BorderLayout.EAST);

    }

    /**
     * initializes every box of the board, already
     * placing the blocks of the towers and the workers.
     * Every component will be set visible when needed
     */

    private void initialize3dMatrix()
    {
        //The five Z levels are: 4 worker, 3 dome, 2 third level, 1 second level and 0 ground level
        ImageIcon purpleWorkerIcon = resizeIcon(getResource("WorkerPurpleM_Cropped"), 60,90);
        //Workers will probably be painted later, this is just for testing
        ImageIcon domeIcon = resizeIcon(getResource("Dome"), 90,90);
        ImageIcon thirdFloorIcon = resizeIcon(getResource("Tower3"), 90,90);
        ImageIcon secondFloorIcon = resizeIcon(getResource("Tower2"), 90,90);
        ImageIcon groundFloorIcon = resizeIcon(getResource("Tower1"), 90,90);

        ImageIcon[] iconArray = new ImageIcon[5];
        iconArray[4] = purpleWorkerIcon;
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


    //_______________________________________WINDOW UPDATER METHODS_________________________________________________

    /**
     * when a player is the current player,
     * changes the window, changing the colour of
     * the current player's name  to red
     */

    private void updateCurrentPlayer(int currentPlayer)
    {

        playerCard.setForeground(Color.BLACK);
        for(JLabel l:opponentsMiniatures)
        {
            l.setForeground(Color.BLACK);
        }
        if(myID==currentPlayer)
        {
            playerCard.setForeground(Color.RED);
        }
        else
        {
            for(JLabel l:opponentsMiniatures)
            {
                if(l.getText().equals(playersName[currentPlayer]))
                {
                    l.setForeground(Color.RED);
                }
            }
        }
    }

    /**
     * sets the needed block visible on a tower
     */

    private void updateTowers(int[][] towerPositions, String[][] lastBlock)
    {
        int towerHeight;
        //Last block needs to be checked just if it's a Dome
        //The 3d Matrix is updated with tower blocks
        //Only levels 0 to 3 are used in the matrix, level 4 is reserved for workers

        //cycles through board representation and sets correct visibility for components
        for(int row=0; row<5;row++)
        {
            for(int col=0; col<5; col++)
            {
                if(lastBlock[row][col].equals("Dome"))
                {
                    //If the last block is a Dome some lower levels might not be present (Atlas power)
                    towerHeight = towerPositions[row][col];
                    for(int count=0;count < towerHeight -1;count ++)
                    {
                        //Setting blocks under dome visibility
                        pieces3dMatrix[row][col][count].setVisible(true);
                    }

                    //Setting the Dome visibility
                    pieces3dMatrix[row][col][3].setVisible(true);
                }
                else
                    {
                        towerHeight = towerPositions[row][col];
                        for(int count=0; count <towerHeight;count++)
                        {
                            pieces3dMatrix[row][col][count].setVisible(true);
                        }

                    }
            }
        }
    }

    /**
     * puts the needed worker where his player has chosen
     */

    private void updateWorkers(int[][] workerPositions)
    {
        int[] currentWorker = new int[2];
        int playerID;
        ImageIcon purpleWorkerIcon = resizeIcon(getResource("WorkerPurpleM_Cropped"), 60,90);
        ImageIcon  redWorkerIcon = resizeIcon(getResource("WorkerRedM_Cropped"), 60,90);
        ImageIcon  blueWorkerIcon = resizeIcon(getResource("WorkerBlueM_Cropped"), 60,90);
        ImageIcon currentIcon;


        //cycle through worker matrix and use appropriate worker color 0:red, 1:blue and 2:purple
        for(int row=0; row<5; row++)
        {
            for(int col=0; col<5; col++)
            {
                playerID = workerPositions[row][col];
                //A worker is present in the cell
                if(playerID != -1)
                {
                    if(playerID / 10 == 0)
                    {
                        currentIcon = redWorkerIcon;
                    }
                    else if(playerID / 10 == 1)
                    {
                        currentIcon = blueWorkerIcon;
                    }
                    else
                        {
                            currentIcon = purpleWorkerIcon;
                        }

                    if(playerID / 10 == myID)
                    {
                        //worker belongs to this player, updating workerPos array
                        currentWorker = new int[2];
                        currentWorker[0] = row;
                        currentWorker[1] = col;
                        myWorkerCells[playerID % 10] = currentWorker;
                    }

                    //4 Is the Z level for workers
                    pieces3dMatrix[row][col][4].setIcon(currentIcon);
                    //pieces3dMatrix[row][col][4].setVerticalAlignment(JLabel.TOP);
                    pieces3dMatrix[row][col][4].setVisible(true);
                }
                else
                    {
                        //If the board has no worker in the cell the icon is turned transparent
                        pieces3dMatrix[row][col][4].setVisible(false);
                    }
            }
        }
    }

    /**
     * updates the active cell for the selected worker,
     * which are the cells where he can build or move
     */

    private void updateSelectedCells(int[][] activeCells)
    {
        this.activeCells=activeCells;
        //cells containing [1] must be selected
        for(int row=0; row<5;row++)
        {
            for(int col=0; col<5; col++)
            {
                if(activeCells[row][col] == 1)
                {
                    //The button becomes yellow but still allows to see through
                    boardButtons[row][col].setBackground(new Color(208, 208, 0, 100));


                }
               else
                   {
                       boardButtons[row][col].setBackground(new Color(0,0,0,0));
                   }
            }
        }
    }

    /**
     * shows the error message to the player
     */

    private void showErrorMessage(String message)
    {
        msgToUser.setText(message);
    }

    /**
     * updates the current state of the game
     */

    private void updateCurrentState(StateEnum state)
    {
        currentState.setText("Current State: "+state.toString());
        presentState = state;
    }

    /**
     * according to the
     * @param areVisible which can be true or false
     * enables the buttons used so that the player
     * can choose whether to activate his god power
     */

    private void showGodButtons(boolean areVisible)
    {
        if(areVisible)
        {
            godSelect.setEnabled(true);
            godRefuse.setEnabled(true);
        }
        else
            {
                godSelect.setEnabled(false);
                godRefuse.setEnabled(false);
            }
    }

    /**
     * shows which gods are active, changing the window
     */

    private void updateSelectedGods(boolean[] activeGodsList)
    {
        playerCard.setOpaque(false);
        for(JLabel l:opponentsMiniatures)
        {
            l.setOpaque(false);
        }


        for(int count=0; count < activeGodsList.length; count++)
        {
            if(count == myID)
            {
                if(activeGodsList[myID])
                {
                        playerCard.setOpaque(true);
                }
            }
            else if(activeGodsList[count])
            {
               for(JLabel l:opponentsMiniatures)
               {
                   if(l.getText().equals(playersName[count]))
                   {
                       l.setOpaque(true);
                   }
               }
            }
        }

    }


 //__________________________________________UTILITY METHODS__________________________________________________________

    /**
     * @param name name of the god
     * @return the needed image
     */

    private ImageIcon getResource(String name)
    {
        try {
            return new ImageIcon(ImageIO.read(getClass().getResource("/"+name+".png")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * resizes the icon
     */

    private ImageIcon resizeIcon(ImageIcon defaultScale, int scaleDownFactor)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( defaultScale.getIconWidth()/scaleDownFactor,
                defaultScale.getIconHeight()/scaleDownFactor,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    /**
     * resizes the icon
     * @param defaultScale the icon
     * @return the resized icon
     */

    private ImageIcon resizeIcon(ImageIcon defaultScale, int width, int height)
    {
        Image newimg = defaultScale.getImage().getScaledInstance( width,
                height,  java.awt.Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    /**
     * @param update contains model representation or errors received from the server
     * the method represents graphically what's inside the update message*/
    @Override
    public void updateWindow(MessageToVirtualView update) {
        //Checks if the message is an error or a modelRep
        if(update.isModelRep())
        {
            //Message is a modelRep
            ModelRepresentation modelRep = update.getModelRep();


            //handler for win/lose
            if(modelRep.hasWon[myID])
            {
                if(JOptionPane.showConfirmDialog(playersSideBar,"Congratulations!! \n you have won!!\n you want quit the game field?","End Game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                    Choice c=new ExitChoice();
                    c.setId(myID);
                    view.update(c);
                    System.exit(0);
                }
                //return;
            }
            if(modelRep.hasLost[myID])
            {
                if(playerCanPlay){
                    JOptionPane.showMessageDialog(playersSideBar,"You have lost \n You'll spectate the game");
                    playerCanPlay = false;
                }
                for(int player = 0; player < playersName.length; player++)
                {
                    if(modelRep.hasWon[player])
                    {
                        JOptionPane.showMessageDialog(playersSideBar,"The game has ended,\n the game Window will now close");
                        Choice c=new ExitChoice();
                        c.setId(myID);
                        view.update(c);
                        System.exit(0);
                    }
                }

                    //this.setWindowNotVisible();
            }


            updateCurrentPlayer(modelRep.getActivePlayer());

            updateCurrentState(modelRep.getCurrentState());

            if(modelRep.getCurrentState() == StateEnum.ActivationGod)
            {
                showGodButtons(true);
                workerHasBeenSelected = false;

            }
            else
                {
                    showGodButtons(false);
                }

            updateTowers(modelRep.getTowerPosition(), modelRep.getLastBlock());

            updateWorkers(modelRep.getWorkerPosition());

            if(modelRep.activePlayer == myID)
                updateSelectedCells(modelRep.activeCells);

            updateSelectedGods(modelRep.getActiveGodsList());


        }
        else
            {
                //Message is an error
                if(update.getPlayerName() == null || update.getPlayerName().equals(playersName[myID]))
                {
                    showErrorMessage(update.getMessage().getMessage());
                }
            }
    }

    /**
     * sets the window visible
     */

    @Override
    public void setWindowVisible() {
        this.setVisible(true);
    }

    /**
     * sets the window not visible
     */

    @Override
    public void setWindowNotVisible() {
        this.setVisible(false);
    }

    @Override
    public void messagePrompt(String message) {

    }

    /**
     * updates the window according to what a player has clicked on
     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        Choice choiceToSend = null;
        int clickedCell;
        int[] cellArray = new int[2];


        if(actionEvent.getActionCommand().equals("Activate"))
        {
            showGodButtons(false);
            choiceToSend = new GodActivationChoice(true);
        }
        else if(actionEvent.getActionCommand().equals("Deactivate"))
        {
            showGodButtons(false);
            choiceToSend = new GodActivationChoice(false);
        }
        else if(actionEvent.getActionCommand().equals("Exit"))
        {
            if(JOptionPane.showConfirmDialog(playersSideBar,"Are you sure you want quit the game?","Quit the game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                if(playerCanPlay) {
                    Choice c = new ExitChoice();
                    c.setId(myID);
                    view.update(c);
                    System.exit(0);
                }
                else
                {
                    JOptionPane.showMessageDialog(playersSideBar,"You have lost \n You'll spectate the game");
                }
            }
        }
        else
            {
                //a cell has been clicked
                try {
                    clickedCell = Integer.parseInt(actionEvent.getActionCommand());
                    cellArray[0] = clickedCell / 5;
                    cellArray[1] = clickedCell % 5;

                     //Depending on the state I'll send different messages
                    if(presentState == StateEnum.SetUp){
                        choiceToSend = new InitialPositionChoice(cellArray[0], cellArray[1]);
                    }
                    else
                        {
                            System.out.println(workerHasBeenSelected + " " + selectedWorker);
                            //I first select the worker
                            if((myWorkerCells[0][0]==cellArray[0] && myWorkerCells[0][1]==cellArray[1]))
                            {
                                if(!workerHasBeenSelected)
                                {
                                    choiceToSend = new SelectWorkerCellChoice(cellArray[0], cellArray[1]);
                                    selectedWorker = 0;
                                }
                                else
                                {
                                    choiceToSend = new SelectWorkerCellChoice(myWorkerCells[selectedWorker][0],
                                            myWorkerCells[selectedWorker][1]);
                                }

                            }
                            else if((myWorkerCells[1][0]==cellArray[0] && myWorkerCells[1][1]==cellArray[1]) )
                            {
                                if(!workerHasBeenSelected )
                                {
                                    choiceToSend = new SelectWorkerCellChoice(cellArray[0], cellArray[1]);
                                    selectedWorker = 1;
                                }
                                else
                                {
                                    choiceToSend = new SelectWorkerCellChoice(myWorkerCells[selectedWorker][0],
                                            myWorkerCells[selectedWorker][1]);

                                }
                            }
                            else
                                {
                                    switch (presentState)
                                    {
                                        case Move:
                                            //If the move is actually valid the worker is now fixed
                                            if(activeCells[cellArray[0]][cellArray[1]] == 1)
                                            {
                                                workerHasBeenSelected = true;
                                            }
                                            choiceToSend = new MoveChoice(cellArray[0], cellArray[1]);
                                            break;

                                        case Build:
                                            //If the build is actually valid the worker is now fixed
                                            //this case is only useful for prometheus
                                            if(activeCells[cellArray[0]][cellArray[1]] == 1)
                                            {
                                                workerHasBeenSelected = true;
                                            }
                                            choiceToSend = new BuildChoice(cellArray[0], cellArray[1]);

                                            break;
                                    }

                                }
                        }


                }
                catch (NumberFormatException e)
                {
                    System.out.println("Message is not supported!");
                }


            }

        if(choiceToSend != null)
        {
            choiceToSend.setId(myID);
            view.update(choiceToSend);
        }

    }
}
