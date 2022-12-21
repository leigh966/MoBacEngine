package data.libs;

public class Player
{
    public float movementSpeed;
    private float[] position;
    public float rotation;
    public Player(float x, float y, float r)
    {
        setPosition(x, y);
        rotation = r;
        movementSpeed = 0.01f;
    }

    public float[] getPosition()
    {
        return position;
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


}
