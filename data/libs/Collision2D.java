package data.libs;

import static java.lang.Math.pow;

public class Collision2D
{
    private boolean collisionOccurred;
    private float[] intersection;

    public boolean getCollisionOccurred()
    {
        return collisionOccurred;
    }

    public float[] getIntersection()
    {
        return intersection.clone();
    }

    Collision2D(boolean ocurred, float x, float y)
    {
        collisionOccurred = ocurred;
        intersection = new float[]{x,y};
    }

    Collision2D()
    {
        collisionOccurred = false;
        intersection = null;
    }


    public static Collision2D LineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        // calculate the distance to intersection point
        float denominator = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            float intersectionX = x1 + (uA * (x2-x1));
            float intersectionY = y1 + (uA * (y2-y1));
            return new Collision2D(true, intersectionX, intersectionY);
        }
        else return new Collision2D();
    }



    public static Collision2D LinePoint(float pointX, float pointY, float lineX1, float lineY1, float lineX2, float lineY2)
    {
        float lineLen = MoMath.calculatePointDistance(lineX1, lineY1, lineX2, lineY2);
        float d1 = MoMath.calculatePointDistance(pointX, pointY, lineX1, lineY1);
        float d2 = MoMath.calculatePointDistance(pointX, pointY, lineX2, lineY2);
        final float BUFFER = 0.1f; // floats aren't accurate so check within this distance
        if(d1+d2>=lineLen-BUFFER && d1+d2<=lineLen+BUFFER)
        {
            float[] vector = new float[]{(lineX2-lineX1)/lineLen, (lineY2-lineY1)/lineLen};
            return new Collision2D(true, vector[0]*d1, vector[1]*d1);
        }
        return new Collision2D();
    }


    public static Collision2D CircleLine(float circleX, float circleY, float circleRadius, float lineX1, float lineY1, float lineX2, float lineY2)
    {
        float len = MoMath.calculatePointDistance(lineX1, lineY1, lineX2, lineY2);
        float dot = ( ((circleX-lineX1)*(lineX2-lineX1)) + ((circleY-lineY1)*(lineY2-lineY1)) ) / (float)pow(len,2);
        float closestX = lineX1 + (dot * (lineX2-lineX1));
        float closestY = lineY1 + (dot * (lineY2-lineY1));
        Collision2D onSegmentCollision = LinePoint(closestX,closestY,lineX1,lineY1,lineX2,lineY2);
        boolean onSegment = onSegmentCollision.getCollisionOccurred();
        if (!onSegment) return onSegmentCollision;
        float distX = closestX - circleX;
        float distY = closestY - circleY;
        float squareDistance = (distX*distX) + (distY*distY);
        if(squareDistance <= circleRadius*circleRadius)
        {
            return new Collision2D(true, closestX, closestY);
        }
        return new Collision2D();
    }

}
