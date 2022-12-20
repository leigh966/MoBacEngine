package data.libs.parents;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Render extends JFrame {
    protected List<Float[]> lines;

    protected HashMap<String, String> configValues;

    public Render(String windowName)
    {
        super(windowName);
        configValues = new HashMap<String, String>();
        readConfig();
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

    protected void loadLevel(String levelName) // maybe have error checking and return bool to signify whether all was okay
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

}
