package data.libs;

import static java.lang.Math.sqrt;

public class MoMath {

    public static float calculatePointDistance(float x1, float y1, float x2, float y2)
    {
        return (float)sqrt( calculatePointDistanceSquared(x1, y1, x2, y2) );
    }

    public static float calculatePointDistanceSquared(float x1, float y1, float x2, float y2)
    {
        float distX = x1 - x2;
        float distY = y1 - y2;
        return (distX*distX) + (distY*distY);
    }

}
