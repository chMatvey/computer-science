package com.github.chMatvey.sanitizer.infrastructure;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    @Getter
    private Reflections scanner;

    private Map<Class<?>, Class<?>> ifcToImplClass;

    public <T> JavaConfig(String packageToScan, Map<Class<?>, Class<?>> ifcToImplClass) {
        scanner = new Reflections(packageToScan);
        this.ifcToImplClass = ifcToImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        Class<?> result = ifcToImplClass.computeIfAbsent(type, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
            if (classes.size() != 1) {
                throw new RuntimeException(String.format("%s has zero or more than one implementation", type.toString()));
            }

            return classes.iterator().next();
        });

        return (Class<? extends T>) result;
    }
}
