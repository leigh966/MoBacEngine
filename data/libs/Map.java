package data.libs;

import data.libs.parents.Render;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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



    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // x y lx ly
        for(Float[] line : lines)
        {
            g2d.draw(new Line2D.Double(line[0], line[1], line[0]+line[2], line[1]+line[3]));
            System.out.println(String.format("%s %s %s %s", line[0], line[1], line[0]+line[2], line[1]+line[3]));
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

}