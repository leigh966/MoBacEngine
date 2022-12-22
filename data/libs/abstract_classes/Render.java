package data.libs.abstract_classes;

import data.libs.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static data.libs.ConfigReader.readConfig;

public abstract class Render extends JFrame {
    protected List<Float[]> lines;

    protected HashMap<String, String> configValues;
    public boolean showFps;
    protected JPanel panel;

    List<Runnable> actions;

    public List<Float[]> getLines()
    {
        return new LinkedList<>(lines);
    }


    public Render(String windowName, String levelName)
    {
        super(windowName);
        actions = new LinkedList<>();
        configValues = new HashMap<String, String>();
        configValues = readConfig("rendering");
        lines = new LinkedList<Float[]>();
        setShowFps();
        setupWindowSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadLevel(levelName);
    }

    private void setupWindowSize()
    {
        int[] windowSize = new int[2];
        try
        {
            windowSize[0] = Integer.parseInt(configValues.get("width"));
            windowSize[1] = Integer.parseInt(configValues.get("height"));
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Config values for window size are not interpretable as numbers! Defaulting to 720p.");
            windowSize[0] = 1280;
            windowSize[1] = 720;
        }
        setSize(windowSize[0], windowSize[1]);
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
        g2d.setColor(Color.BLACK);
        if(System.currentTimeMillis() - lastFpsUpdate > 1000L) { // update the counter every sec
            framerate = 1.0d / (((double) elapsedTime) / 1000.0d);
            lastFpsUpdate = System.currentTimeMillis();
        }
        g2d.drawString(String.format("%,.2f", framerate), 20, 50);
    }

    List<Integer[]> ovals = new LinkedList<>();
    public void drawOval(int x, int y, int w, int h)
    {
        ovals.add(new Integer[]{x,y,w,h});
    }

    protected void drawOvals(Graphics2D g2d)
    {
        for(Integer[] oval : ovals)
        {
            g2d.drawOval(oval[0],oval[1],oval[2],oval[3]);
        }
    }

    protected void draw(Graphics2D g2d)
    {
        for(Runnable r : actions)
        {
            r.run();
        }
        g2d.setBackground(Color.white);
        elapsedTime = System.currentTimeMillis() - frameStart;
        frameStart = System.currentTimeMillis();
        drawOvals(g2d);
        if(showFps) drawFps(g2d);
    }

    long elapsedTime;
    long frameStart = System.currentTimeMillis();


    public Player getPlayer()
    {
        return player;
    }

    public void paint(Graphics g)
    {
        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.white);
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0,this.getWidth(), this.getHeight());
        draw(g2d);
        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

        repaint();
    }

    public void addPerFrameAction(Runnable r)
    {
        actions.add(r);
    }

}
