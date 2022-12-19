package mobac;

import mobac.MyClassLoader;

public class MoBac
{
    private static ClassLoader parentClassLoader;
    private static MyClassLoader classLoader;

    private static void loadPlugin(String pluginName) throws IllegalAccessException, InstantiationException
    {
        try {
            Class myObjectClass = classLoader.loadClass("data."+pluginName);
            IMoBacPlugin object1 =
                    (IMoBacPlugin) myObjectClass.newInstance();

            object1.Run();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Plugin not found. Skipping...\nSee below for more information:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws
            IllegalAccessException,
            InstantiationException {

        parentClassLoader = MyClassLoader.class.getClassLoader();
        classLoader = new MyClassLoader(parentClassLoader);
        loadPlugin("Default");
    }
}
