package data.libs;

import data.libs.parents.Render;

import java.awt.*;

public class False3D extends Render {

    int fov;
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

    private float getPixelTheta(int x)
    {
        float relativeToCenter = x-(getWidth()/2);
        float gradient = (float)fov/(float)getWidth();
        float theta = relativeToCenter*gradient;
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

    private float[] getCollisionPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        // calculate the distance to intersection point
        float denominator = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            float intersectionX = x1 + (uA * (x2-x1));
            float intersectionY = y1 + (uA * (y2-y1));
            return new float[]{intersectionX, intersectionY};
        }
        else return null;
    }

    private float calculateSquareDistance(int x, Color[] c)
    {

        float theta = getPixelTheta(x);
        float[] vector = getVector(theta);
        float[][] ray = getRay(vector);
        float outputSquareDistance = 1000000f;
        for(Float[] line : lines)
        {
            float[][] p2pLine = new float[][]{new float[]{line[0], line[1]}, new float[]{line[0], line[1]}};
            p2pLine[1][0]+=line[2];
            p2pLine[1][1]+=line[3];
            float[] intersect = getCollisionPoint(p2pLine[0][0], p2pLine[0][1],
                    p2pLine[1][0], p2pLine[1][1], ray[0][0], ray[0][1],
                    ray[1][0],ray[1][1]);
            if(intersect==null) continue;
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
