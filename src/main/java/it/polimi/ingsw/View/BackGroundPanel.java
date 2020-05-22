package it.polimi.ingsw.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackGroundPanel extends JPanel {
    Image img;

    public BackGroundPanel(String path)
    {
        try{
            img= ImageIO.read(new File(path));
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return img==null ? super.getPreferredSize():new Dimension(img.getWidth(null),img.getWidth(null));
    }
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
