package com.river;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class hello = new CustomClassLoader().findClass("Hello");
        Object newInstance = hello.getDeclaredConstructor().newInstance();
        Method method = hello.getMethod("hello");
        method.invoke(newInstance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        return super.findClass(name);

        File f = new File(this.getClass().getClassLoader().getResource("Hello.xlass").getFile());
        byte[] bytes = null;
        try {
            bytes = new FileInputStream(f).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }
}
