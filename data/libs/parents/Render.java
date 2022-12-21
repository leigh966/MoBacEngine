package data.libs.parents;

import data.libs.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public abstract class Render extends JFrame {
    protected List<Float[]> lines;

    protected HashMap<String, String> configValues;
    public boolean showFps;
    protected JPanel panel;

    public Render(String windowName)
    {
        super(windowName);
        configValues = new HashMap<String, String>();
        readConfig();
        setShowFps();
    }

    private void setShowFps()
    {
        try
        {
            int fpsEnabled = Integer.parseInt(configValues.get("fpscounter"));
            showFps = fpsEnabled > 0;
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Could not read fpsCounter enabled value from rendering.config! Defaulting to 0 (disabled).");
        }
    }

    private void readConfig()
    {
        try {
            String path = "data/rendering.config";
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("=");
                configValues.put(data[0], data[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
    }

    protected Player player;
    protected void loadLevel(String levelName) // maybe have error checking and return bool to signify whether all was okay
    {
        try {
            String path = String.format("data/levels/%s.modat", levelName);
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                String name = data[0];
                Float[] newData = Arrays.stream(Arrays.copyOfRange(data, 1, data.length)).map(Float::valueOf).toArray(Float[]::new);
                if(name.equals("line")) lines.add(newData);
                else if(name.equals("player")) player = new Player(newData[0], newData[1], newData[2]);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
        if(player==null) System.out.println("No player in scene!");
    }

    long lastFpsUpdate = System.currentTimeMillis();
    double framerate;
    private void drawFps(Graphics2D g2d)
    {
        if(System.currentTimeMillis() - lastFpsUpdate > 1000L) { // update the counter every sec
            framerate = 1.0d / (((double) elapsedTime) / 1000.0d);
            lastFpsUpdate = System.currentTimeMillis();
        }
        g2d.drawString(String.format("%,.2f", framerate), 20, 50);
    }


    protected void draw(Graphics2D g2d)
    {
        g2d.setBackground(Color.white);
        elapsedTime = System.currentTimeMillis() - frameStart;
        frameStart = System.currentTimeMillis();
        if(showFps) drawFps(g2d);
    }

    long elapsedTime;
    long frameStart = System.currentTimeMillis();
    public void paint(Graphics g) {
        super.paint(g);
    }


    public abstract void drawPlayer(Graphics2D g2d);

}
