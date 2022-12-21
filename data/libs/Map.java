package data.libs;

import data.libs.parents.Render;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JFrame;


public class Map extends Render {



    public Map(String levelName) {
        super("Lines Drawing Demo");
        lines = new LinkedList<Float[]>();
        int[] windowSize = new int[2];
        try
        {
            windowSize[0] = Integer.parseInt(configValues.get("width"));
            windowSize[1] = Integer.parseInt(configValues.get("height"));
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Config values for window size are not interpretable as numbers! Defaulting to 720p.");
            windowSize[0] = 1280;
            windowSize[1] = 720;
        }

        setSize(windowSize[0], windowSize[1]);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadLevel(levelName);
    }



    void drawLines(Graphics2D g2d) {

        // x y lx ly
        for(Float[] line : lines)
        {
            g2d.draw(new Line2D.Double(line[0], line[1], line[0]+line[2], line[1]+line[3]));
        }

    }

    protected void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        super.draw(g2d);
        if(player!=null) drawPlayer(g2d);
        drawLines(g2d);
    }

    @Override
    public void drawPlayer(Graphics2D g2d)
    {
        float[] pos = player.getPosition();
        float[] facing = player.getFacingVector();
        g2d.drawLine(Math.round(pos[0]), Math.round(pos[1]), Math.round(pos[0]+facing[0]*5), Math.round(pos[1]+facing[1]*5));
    }

    public void paint(Graphics g) {
        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.white);
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0,this.getWidth(), this.getHeight());
        draw(g2d);
        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

        repaint();
    }



}