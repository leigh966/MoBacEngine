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


        //create new class loader so classes can be reloaded.
        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass("data.Default");

        object1 = (IMoBacPlugin) myObjectClass.newInstance();

        object1.Run();

    }
}
