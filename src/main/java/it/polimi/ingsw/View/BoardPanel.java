package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {

    private Image img;
    private ViewBox[][] buttonsGrid;

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

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(img!=null) {
            g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
        }
    }

    public ViewBox getBox(int x,int y)
    {
        return buttonsGrid[x][y];
    }
}