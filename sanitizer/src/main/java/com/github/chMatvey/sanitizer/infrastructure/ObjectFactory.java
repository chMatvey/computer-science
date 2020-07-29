package com.github.chMatvey.sanitizer.infrastructure;

import com.github.chMatvey.sanitizer.infrastructure.configurator.object.ObjectConfigurator;
import com.github.chMatvey.sanitizer.infrastructure.configurator.proxy.ProxyConfigurator;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;

    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            objectConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }

        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T object = create(implClass);

        configure(object);

        invokeInitMethod(object, implClass);

        object =  wrapWithProxyIfNeeded(object, implClass);

        return object;
    }

    private <T> T wrapWithProxyIfNeeded(T object, Class<T> implClass) {
        for (ProxyConfigurator configurator : proxyConfigurators) {
            object = (T) configurator.replaceWidthProxyIfNeeded(object, implClass);
        }
        return object;
    }

    @SneakyThrows
    private <T> T create(Class<? extends T> implClass) {
        return implClass.getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    private void configure(Object object) {
        for (ObjectConfigurator configurator : objectConfigurators) {
            configurator.configure(object, context);
        }
    }

    @SneakyThrows
    private <T> void invokeInitMethod(T object, Class<? extends T> implClass) {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(object);
            }
        }
    }
}
