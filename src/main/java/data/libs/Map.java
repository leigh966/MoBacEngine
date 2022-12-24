package data.libs;

import data.libs.abstract_classes.Render;

import java.awt.*;
import java.awt.geom.Line2D;


public class Map extends Render {



    public Map(String levelName) {
        super("Lines Drawing Demo", levelName);
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

    public void drawPlayer(Graphics2D g2d)
    {
        float[] pos = player.getPosition();
        float[] facing = player.getFacingVector();
        g2d.drawLine(Math.round(pos[0]), Math.round(pos[1]), Math.round(pos[0]+facing[0]*5), Math.round(pos[1]+facing[1]*5));
    }




}