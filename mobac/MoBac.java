public class MoBac
{
    public static void main(String[] args) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("reflection.MyObject");

        AnInterface2       object1 =
                (AnInterface2) myObjectClass.newInstance();

        MyObjectSuperClass object2 =
                (MyObjectSuperClass) myObjectClass.newInstance();

        //create new class loader so classes can be reloaded.
        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass("reflection.MyObject");

        object1 = (AnInterface2)       myObjectClass.newInstance();
        object2 = (MyObjectSuperClass) myObjectClass.newInstance();

    }
}
