package mobac;

import mobac.MyClassLoader;

public class MoBac
{
    public static void main(String[] args) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("data.Default");

        IMoBacPlugin object1 =
                (IMoBacPlugin) myObjectClass.newInstance();

        object1.Run();

    }
}
