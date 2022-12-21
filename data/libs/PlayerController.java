package data.libs;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static data.libs.ConfigReader.readConfig;

public class PlayerController {
    protected Player player;
    protected HashMap<String, String> configValues;
    public float movementSpeed;
    public PlayerController(Player playerToControl, JFrame frame)
    {
        player = playerToControl;
        configValues = readConfig("controls");
        movementSpeed = 1.0f;
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if(key == configValues.get("forward").charAt(0))
                {
                    move(new float[]{0,1});
                    System.out.println("moving");
                }
                else
                {
                    System.out.println("pressing "+key);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void rotate(float angle)
    {
        player.rotation += angle;
    }

    public void move(float[] vector)
    {
        float[] playerFacing = player.getFacingVector();
        float[] forwardVector = new float[]{playerFacing[0]*vector[1], playerFacing[1]*vector[1]};
        float[] playerRight = player.getRightVector();
        float[] rightVector = new float[]{playerRight[0]*vector[0], playerRight[1]*vector[0]};
        float[] newVector = new float[]{(forwardVector[0]+rightVector[0])*movementSpeed, (forwardVector[1]+rightVector[1])*movementSpeed};
        player.transform(newVector[0], newVector[1]);
    }



}
