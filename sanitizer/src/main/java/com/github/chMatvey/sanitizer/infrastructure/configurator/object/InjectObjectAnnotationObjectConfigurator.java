package com.github.chMatvey.sanitizer.infrastructure.configurator.object;

import com.github.chMatvey.sanitizer.infrastructure.ApplicationContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

import static org.reflections.ReflectionUtils.getAllFields;

public class InjectObjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object object, ApplicationContext context) {
        for (Field field : getAllFields(object.getClass())) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                field.setAccessible(true);
                Object dependency = context.getObject(field.getType());
                field.set(object, dependency);
            }
        }
    }
}
