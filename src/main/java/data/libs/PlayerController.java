package data.libs;

import data.libs.abstract_classes.Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static data.libs.ConfigReader.readConfig;

/**
 * Class to represent the statndard player controller provide extended movement and interaction functionality to the player
 * @author Leigh Hurley (ItsTheNikolai)
 */
public class PlayerController {
    protected Player player;
    protected HashMap<String, String> configValues;
    /** The distance that the player moves per frame */
    public static float movementSpeed = 0.1f;
    /** The angle that the player rotates per frame */
    public static float rotationSpeed;
    protected String keysDown = "";

    /**
     *
     * @param frame The Render object currently rendering the scene
     */
    public PlayerController(Render frame)
    {
        player = frame.getPlayer();
        configValues = readConfig("controls");
        loadSensitivity();
        startListening(frame);
    }

    /**
     * Attempt to load the sensitivity (rotationSpeed) from controls.config. If it is not present in the config,
     * default to 0.2f
     */
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

    /**
     * Add key listeners to the scene for this object to respond to
     * @param frame The Render object currently rendering the scene
     */
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

    /**
     * Figure out whether a given key corresponds to a given action in controls.config
     * @param key The key to check against the action
     * @param action The action to check against the key
     * @return Boolean value representing whether or not the key corresponds to the action
     */
    private boolean isKeyFor(char key, String action)
    {
        String configKeyString = configValues.get(action);
        if(configKeyString==null) return false;
        return key == configKeyString.charAt(0);
    }

    /**
     * Adjust the players current angle by an angle delta
     * @param angle The delta angle for the player's rotation (how much it should change)
     */
    public void rotate(float angle)
    {
        player.rotation += angle;
    }

    /**
     * Move the player in the world in the direction of a given local-space vector but altered to have a magnitude of movementSpeed
     * @param vector A vector in the local space of the player to move
     */
    public void move(float[] vector)
    {
        float[] playerFacing = player.getFacingVector();
        float[] forwardVector = new float[]{playerFacing[0]*vector[1], playerFacing[1]*vector[1]};
        float[] playerRight = player.getRightVector();
        float[] rightVector = new float[]{playerRight[0]*vector[0], playerRight[1]*vector[0]};
        float[] newVector = new float[]{(forwardVector[0]+rightVector[0])*movementSpeed, (forwardVector[1]+rightVector[1])*movementSpeed};
        player.transform(newVector[0], newVector[1]);
    }


    /**
     * Add this method as a per-frame action to your Render object to use the standard controls of moving forwards,
     * backwards, right, left and rotating clockwise or anticlockwise
     */
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
