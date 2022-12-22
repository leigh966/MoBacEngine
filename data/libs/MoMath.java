package data.libs;

import static java.lang.Math.sqrt;

/** A library of maths operations vital in game development
 * @author Leigh Hurley (ItsTheNikolai)
 */
public class MoMath {

    /**
     * Calculate the hypotenuse^2 of a right-angled triangle given the length of the opposite and adjacent
     * @param a
     * @param b
     * @return a^2 + b^2
     */
    public static float pythagorasTheoremSquared(float a, float b)
    {
        return (a*a)+(b*b);
    }

    /**
     * Calculate the hypotenuse^2 of a right-angled triangle given the length of the opposite and adjacent
     * @param a
     * @param b
     * @return sqrt(a^2 + b^2)
     */
    public static float pythagorasTheorem(float a, float b)
    {
        return (float) sqrt(pythagorasTheoremSquared(a,b));
    }

    /**
     * Calculate the length of the line segment such that (x1,y1) is one end and (x2,y2) is the other
     * @param x1 The x coordinate of point 1 of the line segment
     * @param y1 The y coordinate of point 1 of the line segment
     * @param x2 The x coordinate of point 2 of the line segment
     * @param y2 The y coordinate of point 2 of the line segment
     * @return sqrt( (x1-x2)^2 + (y1-y2)^2 )
     */
    public static float calculatePointDistance(float x1, float y1, float x2, float y2)
    {
        return (float)sqrt( calculatePointDistanceSquared(x1, y1, x2, y2) );
    }

    /**
     * Calculate the length^2 of the line segment such that (x1,y1) is one end and (x2,y2) is the other
     * @param x1 The x coordinate of point 1 of the line segment
     * @param y1 The y coordinate of point 1 of the line segment
     * @param x2 The x coordinate of point 2 of the line segment
     * @param y2 The y coordinate of point 2 of the line segment
     * @return (x1-x2)^2 + (y1-y2)^2
     */
    public static float calculatePointDistanceSquared(float x1, float y1, float x2, float y2)
    {
        float distX = x1 - x2;
        float distY = y1 - y2;
        return pythagorasTheoremSquared(distX, distY);
    }

    /**
     * Get a normalized version of the given vector, dividing it such that its magnitude is 1
     * @param x The x component of the vector
     * @param y The y component of the vector
     * @return Float array such that arr[0] is the x component of the normalized vector and arr[1] is its y component
     */
    public static float[] normalize(float x, float y)
    {
        float size = pythagorasTheorem(x, y);
        return normalize(x, y, size);
    }

    /**
     * Get a normalized version of the given vector, dividing it such that its magnitude is 1
     * @param x The x component of the vector
     * @param y The y component of the vector
     * @param size The magnitude of the input vector
     * @return Float array such that arr[0] is the x component of the normalized vector and arr[1] is its y component
     */
    public static float[] normalize(float x, float y, float size)
    {
        return new float[] {x/size, y/size};
    }

}
