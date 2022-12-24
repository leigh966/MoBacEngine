package mobac;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MoBac
{
    private static ClassLoader parentClassLoader;
    private static MyClassLoader classLoader;

    private static boolean loadPlugin(String pluginName) throws IllegalAccessException, InstantiationException
    {
        try {
            Class myObjectClass = classLoader.loadClass("data."+pluginName);
            IMoBacPlugin object1 =
                    (IMoBacPlugin) myObjectClass.newInstance();

            object1.Run();
            return true;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Plugin not found. Skipping...\nSee below for more information:");
            e.printStackTrace();
            return false;
        }
    }

    private static boolean tryLoadPlugin(String name)
    {
        try
        {
            boolean loaded = loadPlugin(name);
            return loaded;
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong when loading plugin. Attempting to skip it...");
            e.printStackTrace();
            return false;
        }
    }

    public static void loadPlugins()
    {
        try {
            String path = String.format("load_order.txt");
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tryLoadPlugin(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        parentClassLoader = MyClassLoader.class.getClassLoader();
        classLoader = new MyClassLoader(parentClassLoader);

        loadPlugins();

    }
}
