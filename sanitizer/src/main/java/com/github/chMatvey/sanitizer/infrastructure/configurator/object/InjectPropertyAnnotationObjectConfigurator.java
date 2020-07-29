package com.github.chMatvey.sanitizer.infrastructure.configurator.object;

import com.github.chMatvey.sanitizer.infrastructure.ApplicationContext;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.reflections.ReflectionUtils.getAllFields;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private final Map<String, String> properties;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, ApplicationContext context) {
        Class<?> implClass = object.getClass();
        for (Field field : getAllFields(implClass)) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            if (annotation != null) {
                String value = annotation.value().isEmpty() ? properties.get(field.getName()) : properties.get(annotation.value());
                field.setAccessible(true);
                field.set(object, value);
            }
        }
    }
}
