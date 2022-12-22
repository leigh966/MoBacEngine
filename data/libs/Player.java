package data.libs;

public class Player
{

    private float[] position;
    public float rotation;
    public Player(float x, float y, float r)
    {
        setPosition(x, y);
        rotation = r;

    }

    public float[] getPosition()
    {
        return position.clone();
    }

    public void setPosition(float x, float y)
    {
        position = new float[]{x,y};
    }

    public void transform(float x, float y)
    {
        position[0] += x;
        position[1] += y;
    }


    public float[] getFacingVector()
    {
        double rotationInRadians = Math.toRadians(rotation);
        double x = Math.sin(rotationInRadians);
        double y = -Math.cos(rotationInRadians);
        return new float[] {(float)x,(float)y}; // Used in rendering so needs to not incur rounding issues
                                                //  associated with normalisation. Hence: no normalisation.
    }

    public float[] getRightVector()
    {
        rotation += 90;
        float[] vector = getFacingVector();
        rotation -= 90;
        return vector;
    }

}
