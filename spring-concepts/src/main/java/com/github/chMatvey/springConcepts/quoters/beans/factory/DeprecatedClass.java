package com.github.chMatvey.springConcepts.quoters.beans.factory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DeprecatedClass {
    Class<?> newImpl();
}
