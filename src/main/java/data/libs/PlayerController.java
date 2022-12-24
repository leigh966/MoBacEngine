package data.libs;

import data.libs.abstract_classes.Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static data.libs.ConfigReader.readConfig;

public class PlayerController {
    protected Player player;
    protected HashMap<String, String> configValues;
    public static float movementSpeed = 0.1f;
    public static float rotationSpeed;
    String keysDown = "";

    public PlayerController(Render frame)
    {
        player = frame.getPlayer();
        configValues = readConfig("controls");
        loadSensitivity();
        startListening(frame);
    }

    private void loadSensitivity()
    {
        try {
            String readSensitivity = configValues.get("sensitivity");
            if(readSensitivity==null) throw new NumberFormatException("sensitivity not set in config");
            Float readRotationSpeed = Float.parseFloat(readSensitivity);
            rotationSpeed = readRotationSpeed;
        } catch(NumberFormatException nfe)
        {
            System.out.println("Failed to read sensitivity from config! Defaulting to 0.2f.");
            rotationSpeed = 0.2f;
        }
    }

    private void startListening(JFrame frame)
    {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if(keysDown.contains(""+key)) return;
                keysDown += key;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String[] keysSplit = keysDown.split(""+e.getKeyChar());
                if(keysSplit.length == 0) keysDown = "";
                else if(keysSplit.length == 2) keysDown = keysSplit[0]+keysSplit[1];
                else keysDown = keysSplit[0];
            }
        });
    }

    private boolean isKeyFor(char key, String action)
    {
        return key == configValues.get(action).charAt(0);
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

    public void useStandardEffect()
    {
        for(int i = 0; i < keysDown.length(); i++)
        {
            char key = keysDown.charAt(i);
            if(isKeyFor(key,"forward"))
            {
                move(new float[]{0,1});
            }
            else if(isKeyFor(key,"back"))
            {
                move(new float[]{0,-1});
            }
            else if(isKeyFor(key,"right"))
            {
                move(new float[]{1,0});
            }
            else if(isKeyFor(key,"left"))
            {
                move(new float[]{-1,0});
            }
            else if(isKeyFor(key, "clockwise"))
            {
                rotate(rotationSpeed);
            }
            else if(isKeyFor(key, "anticlockwise"))
            {
                rotate(-rotationSpeed);
            }
            else
            {
                System.out.println("pressing "+key);
            }
        }

    }

}
