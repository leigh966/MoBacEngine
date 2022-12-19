package data.libs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Map extends JFrame {

    private List<Float[]> lines;

    public Map(String levelName) {
        super("Lines Drawing Demo");

        lines = new LinkedList<Float[]>();

        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadLevel(levelName);
    }

    private void loadLevel(String levelName) // maybe have error checking and return bool to signify whether all was okay
    {
        try {
            String path = String.format("data/levels/%s.modat", levelName);
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(Arrays.stream(data.split(" ")).map(Float::valueOf).toArray(Float[]::new));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }

    }

    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // x y lx ly
        for(Float[] line : lines)
        {
            g2d.draw(new Line2D.Double(line[0], line[1], line[0]+line[2], line[1]+line[3]));
            System.out.println(String.format("%s %s %s %s", line[0], line[1], line[0]+line[2], line[1]+line[3]));
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

}