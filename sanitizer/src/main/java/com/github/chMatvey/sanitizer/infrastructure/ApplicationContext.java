package com.github.chMatvey.sanitizer.infrastructure;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private ObjectFactory objectFactory;

    private Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<? extends T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T object = objectFactory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, object);
        }

        return object;
    }
}
