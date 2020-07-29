package com.github.chMatvey.sanitizer.infrastructure.configurator.object;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectProperty {
    String value() default "";
}
