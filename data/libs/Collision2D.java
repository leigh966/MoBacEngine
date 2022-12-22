package data.libs;

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
}
