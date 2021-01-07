package zhangc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader{
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        Class clazz = new CustomClassLoader().findClass("Hello");
        Object object = clazz.newInstance();
        Method hello = clazz.getDeclaredMethod("hello");
        hello.invoke(object);
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "hello.xlass";
        InputStream is = CustomClassLoader.class.getClassLoader().getResourceAsStream(path);
        byte[] bytes = null;
        try {
            bytes = new byte[is.available()];
            is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes.length; i ++){
            bytes[i] = (byte) (~bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
