package com.github.chMatvey.springConcepts.afterInit;

import java.lang.reflect.Method;

public class ClassUtil {

    public static Method[] getMethodsByClassName(String name) {
        try {
            return Class.forName(name).getMethods();
        } catch (ClassNotFoundException e) {
            return new Method[0];
        }
    }
}
