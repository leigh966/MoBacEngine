package data.libs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigReader {

    public static HashMap<String, String> readConfig(String name)
    {
        HashMap<String, String> configValues = new HashMap<>();
        try {
            String path = "data/"+name+".config";
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
        return configValues;
    }

}
