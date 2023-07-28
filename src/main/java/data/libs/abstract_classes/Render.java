package data.libs.abstract_classes;

import data.libs.IPostProcessing;
import data.libs.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static data.libs.ConfigReader.readConfig;


/** Abstract class to build rendering classes such as {@link data.libs.Map Map} and {@link data.libs.False3D False3D}
 * @author Leigh Hurley (ItsTheNikolai)
 */
public abstract class Render extends JFrame {
    protected List<Float[]> lines;

    protected HashMap<String, String> configValues;

    /** Boolean value representing whether the render should draw a fps counter **/
    public boolean showFps;


    protected JPanel panel;

    List<Runnable> actions;

    public RenderingHints hints =new RenderingHints(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);


    public String getConfigValue(String key)
    {
        return configValues.get(key);
    }

    /**
     *
     * @param windowName The name that the window should display in its top bar
     * @param levelName The name of the level such that levels/{levelName}.modat is a readable file of level data
     */
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
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    /** Get a copy of the list of lines (representing walls) that have been read in from the level data
     * @return List of line data in the level
     */
    public List<Float[]> getLines()
    {
        return new LinkedList<>(lines);
    }

    /** Attempts to read the window size from the read config data and set the window size accordingly. If this fails,
     *  it will default to 720p
     */
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

    /** Attempts to read whether to show an fps counter from the read config data and set {@code showFps} accordingly.
     *  If this fails, it will default to false (disabled)
     */
    private void setShowFps()
    {
        try
        {
            int fpsEnabled = Integer.parseInt(configValues.get("fpscounter"));
            showFps = fpsEnabled > 0;
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Could not read fpsCounter value from rendering.config! Defaulting to 0 (disabled).");
            showFps = false;
        }
    }


    protected Player player;

    /** Read in the lines and Player from the level data (.modat) file
     *
     * @param levelName The name of the level such that levels/{levelName}.modat is a readable file of level data
     */
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

    private long lastFpsUpdate = System.currentTimeMillis();
    private double frameRate;

    /** Draw the fps counter in the top left of the screen. Update its value if it has been >1 second since it was last updated
     *
     * @param g2d The 2d graphics object to draw onto
     */
    private void drawFps(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        if(System.currentTimeMillis() - lastFpsUpdate > 1000L) { // update the counter every sec
            frameRate = 1.0d / (((double) elapsedTime) / 1000.0d);
            lastFpsUpdate = System.currentTimeMillis();
        }
        g2d.drawString(String.format("%,.2f", frameRate), 20, 50);
    }

    private List<Integer[]> ovals = new LinkedList<>();

    /** Adds an oval to the list of ovals so that it will be drawn every frame
     *
     * @param x The x coordinate to draw the new oval at on screen
     * @param y The y coordinate to draw the new oval at on screen
     * @param w The width to draw the oval
     * @param h The height to draw the oval
     */
    public void drawOval(int x, int y, int w, int h)
    {
        ovals.add(new Integer[]{x,y,w,h});
    }

    /** Draw all the ovals in the ovals list. Called every frame by Render.draw
     *
     * @param g2d The 2d graphics object to draw onto
     */
    protected void drawOvals(Graphics2D g2d)
    {
        for(Integer[] oval : ovals)
        {
            g2d.drawOval(oval[0],oval[1],oval[2],oval[3]);
        }
    }

    /** Draw the background, any ovals and the fpsCounter. Called every frame by paint and, if overridden, should be
     * called by the overriding method
     * @param g2d The 2d graphics object to draw onto
     */
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

    /** The time taken to render the last frame **/
    private long elapsedTime;

    private long frameStart = System.currentTimeMillis();

    /** Get the player object held by this Render (read in from level data)
     *
     * @return The controllable Player object in the scene
     */
    public Player getPlayer()
    {
        return player;
    }

    /** Refresh Method - calls itself repeatedly to act as a game loop. Called automatically on creation of a Render object
     *
     * @param g Graphics object passed in by the automated call
     */
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
        ((Graphics2D) g).setRenderingHints(hints);
    }

    /** Add an action to be run every frame
     *
     * @param r A runnable object that should have its Run() method called every frame
     */
    public void addPerFrameAction(Runnable r)
    {
        actions.add(r);
    }

    protected List<IPostProcessing> postProcesses = new ArrayList<>();
    public void addPostProcessing(IPostProcessing pp) { postProcesses.add(pp); }

}
