package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * this class chooses the board image
 */

public class BoardPanel extends JPanel {

    private Image img;
    private ViewBox[][] buttonsGrid;

    /**
     * the constructor sets the path of the chosen image
     * and builds the grid where the workers
     * and towers are going to be with buttons,
     * so that the player can select any box
     */

    public BoardPanel()
    {
        try{
            img= ImageIO.read(new File("resources/Board.PNG"));
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

        buttonsGrid=new ViewBox[5][5];
        this.setLayout(new GridLayout(5,5,-1,-1));
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                buttonsGrid[i][j]=new ViewBox(i,j);
                buttonsGrid[i][j].setOpaque(false);
                buttonsGrid[i][j].setContentAreaFilled(false);
                this.add(buttonsGrid[i][j]);
            }
        }
    }

    /**
     * puts the image on the window
     */

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(img!=null) {
            g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
        }
    }

    /**
     * given
     * @param x coordinate x
     * @param y coordinate y
     * @return the box corresponding to the coordinates
     */

    public ViewBox getBox(int x,int y)
    {
        return buttonsGrid[x][y];
    }
}