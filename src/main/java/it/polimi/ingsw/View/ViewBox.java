package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewBox extends JButton {
    private  int x,y;
    private boolean highlighted;
    private int towerLevel;
    private String lastBlock;
    private JButton box;
    private Image img;

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

    public void setImage()
    {
        box.setIcon(new ImageIcon("resources/WorkerPurpleF_Cropped.png"));
    }
    public void setTower(int level,String lastBlock)
    {
        towerLevel=level;
        this.lastBlock=lastBlock;
    }
    public void setHighlighted(boolean highlighted)
    {
        this.highlighted=highlighted;
    }

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
