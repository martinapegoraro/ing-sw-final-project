package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * this class represents a box
 */

public class ViewBox extends JButton {
    private  int x,y;
    private boolean highlighted;
    private int towerLevel;
    private String lastBlock;
    private JButton box;
    private Image img;

    /**
     * builds each box, given its coordinates
     */

    public ViewBox(int x,int y)
    {
        this.x=x;
        this.y=y;
        highlighted=false;
        this.towerLevel=0;
        lastBlock="";
        box=new JButton();
        box.setOpaque(false);
        box.setContentAreaFilled(false);
    }

    /**
     * sets the worker image on the given box
     */

    public void setImage()
    {
        box.setIcon(new ImageIcon("resources/WorkerPurpleF_Cropped.png"));
    }

    /**
     * sets the level and the last block of a tower
     */

    public void setTower(int level,String lastBlock)
    {
        towerLevel=level;
        this.lastBlock=lastBlock;
    }

    /**
     * sets an highlighted box
     */
    public void setHighlighted(boolean highlighted)
    {
        this.highlighted=highlighted;
    }


    /**
     * sets the image given a path
     */
    public void setImage(String path)
    {
        try{
            img= ImageIO.read(new File(path));
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }


}
