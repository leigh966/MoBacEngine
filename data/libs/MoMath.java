package data.libs;

import static java.lang.Math.sqrt;

public class MoMath {

    public static float pythagorasTheoremSquared(float a, float b)
    {
        return (a*a)+(b*b);
    }

    public static float pythagorasTheorem(float a, float b)
    {
        return (float) sqrt(pythagorasTheoremSquared(a,b));
    }


    public static float calculatePointDistance(float x1, float y1, float x2, float y2)
    {
        return (float)sqrt( calculatePointDistanceSquared(x1, y1, x2, y2) );
    }

    public static float calculatePointDistanceSquared(float x1, float y1, float x2, float y2)
    {
        float distX = x1 - x2;
        float distY = y1 - y2;
        return pythagorasTheoremSquared(distX, distY);
    }

    public static float[] normalize(float x, float y)
    {
        float size = pythagorasTheorem(x, y);
        return normalize(x, y, size);
    }

    public static float[] normalize(float x, float y, float size)
    {
        return new float[] {x/size, y/size};
    }

}
