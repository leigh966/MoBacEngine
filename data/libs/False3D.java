package data.libs;

import data.libs.abstract_classes.Render;

import java.awt.*;

public class False3D extends Render {

    public int fov;
    float viewDistance = 200f;
    public False3D(String levelName)
    {
        super("First Person Mode", levelName);
        loadFov();
    }

    private void loadFov()
    {
        try {
            fov = Integer.parseInt(configValues.get("fov"));
        }catch(NumberFormatException nfe)
        {
            System.out.println("Could not read fov from config! Defaulting to 100.");
            fov = 100;
        }
    }

    private float thetaGradient;
    private float getPixelTheta(int x)
    {
        float relativeToCenter = x-(getWidth()/2);
        float theta = relativeToCenter*thetaGradient;
        return theta;
    }

    private float[] getVector(float theta)
    {
        player.rotation+=theta;
        float[] vector = player.getFacingVector();
        player.rotation-=theta;
        return vector;
    }

    private float[][] getRay(float[] vector)
    {
        float[][] line = new float[][]{player.getPosition(), player.getPosition()};
        line[1][0] += vector[0]*viewDistance;
        line[1][1] += vector[1]*viewDistance;
        return line;
    }



    private float calculateSquareDistance(int x, Color[] c)
    {
        float theta = getPixelTheta(x);
        float[] vector = getVector(theta);
        float[][] ray = getRay(vector);
        float outputSquareDistance = 20000f;
        for(Float[] line : lines)
        {
            float[][] p2pLine = new float[][]{new float[]{line[0], line[1]}, new float[]{line[0], line[1]}};
            p2pLine[1][0]+=line[2];
            p2pLine[1][1]+=line[3];
            Collision2D col = Collision2D.LineLine(p2pLine[0][0], p2pLine[0][1],
                    p2pLine[1][0], p2pLine[1][1], ray[0][0], ray[0][1],
                    ray[1][0],ray[1][1]);
            if(!col.getCollisionOccurred()) continue;
            float[] intersect = col.getIntersection();
            float thisDistanceSquared = (intersect[0]-player.getPosition()[0])*(intersect[0]-player.getPosition()[0])
                    +(intersect[1]-player.getPosition()[1])*(intersect[1]-player.getPosition()[1]);
            if(thisDistanceSquared < outputSquareDistance) {
                outputSquareDistance = thisDistanceSquared;
                try {
                    c[0] = Color.getHSBColor(line[4], line[5], line[6]);
                }catch (ArrayIndexOutOfBoundsException npe)
                {
                    System.out.println("Line color not set! defaulting to red");
                    c[0] = Color.RED;
                }
            }

        }
        return outputSquareDistance;
    }

    protected void draw(Graphics2D g2d)
    {
        super.draw(g2d);
        thetaGradient = (float)fov/(float)getWidth(); // Update the gradient incase the fov has been changed
        for(int i = 0; i < getWidth(); i++)
        {
            Color[] drawColor = new Color[]{Color.black};
            float squareDistance = calculateSquareDistance(i, drawColor);
            g2d.setColor(drawColor[0]);
            double distance = Math.sqrt(squareDistance);
            double size = getHeight()/distance;
            int mid = getHeight()/2;
            g2d.drawLine(i, mid-(int)size/2, i, mid+(int)size/2);
        }
    }

}
