package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class chooses the background image for the game, it is used only in the lobby window
 */

public class BackgroundPanel extends JPanel {
    Image img;

    /**
     * the constructor is used to set the path of the image
     */

    public BackgroundPanel(String path)
    {
        try{
            img= ImageIO.read(getClass().getResource(path));
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * sets the dimension of the image
     */

    @Override
    public Dimension getPreferredSize(){
        return img==null ? super.getPreferredSize():new Dimension(img.getWidth(null),img.getWidth(null));
    }

    /**
     * puts the image on the window
     */

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(img!=null) {
            int x=(getWidth()-img.getWidth(null))/2;
            int y=(getHeight()-img.getHeight(null))/2;
            g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
        }
    }
}
