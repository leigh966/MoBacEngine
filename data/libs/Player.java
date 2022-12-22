package data.libs;

/** Class to represent the controllable player in the game world
 * @author Leigh Hurley (ItsTheNikolai) **/
public class Player
{

    private float[] position;

    /** The rotation of the player relative to the vector (0,-1) **/
    public float rotation;

    /**
     * @param x The starting x coordinate of the Player
     * @param y The starting y coordinate of the Player
     * @param r The starting rotation of the Player
     * **/
    public Player(float x, float y, float r)
    {
        setPosition(x, y);
        rotation = r;
    }

    /** Gets a copy of the player's position attribute
     * @return Float array where arr[0]=x & arr[1]=y **/
    public float[] getPosition()
    {
        return position.clone();
    }

    /** Sets the position of the player
     * @param x Desired x coordinate of the player
     * @param y Desired y coordinate of the player
     * **/
    public void setPosition(float x, float y)
    {
        position = new float[]{x,y};
    }

    /** Moves the Player by (dx, dy)
     * @param dx Delta x - The desired numerical change to the x coordinate of the player
     * @param dy Delta y - The desired numerical change to the y coordinate of the player
     */
    public void transform(float dx, float dy)
    {
        position[0] += dx;
        position[1] += dy;
    }

    /** Gets a 2d vector representing the direction that the player is facing
     * @return Float array (size=2) representing the forward pointing vector of the Player
     */
    public float[] getFacingVector()
    {
        double rotationInRadians = Math.toRadians(rotation);
        double x = Math.sin(rotationInRadians);
        double y = -Math.cos(rotationInRadians);
        return new float[] {(float)x,(float)y}; // Used in rendering so needs to not incur rounding issues
                                                //  associated with normalisation. Hence: no normalisation.
    }

    /** Gets a 2d vector representing the direction 90 degrees clockwise of the Player's facing vector
     * @return @return Float array (size=2) representing the right pointing vector of the Player
     * **/
    public float[] getRightVector()
    {
        rotation += 90;
        float[] vector = getFacingVector();
        rotation -= 90;
        return vector;
    }

}
