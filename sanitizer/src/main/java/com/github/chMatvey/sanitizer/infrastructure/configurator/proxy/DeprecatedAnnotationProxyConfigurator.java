package com.github.chMatvey.sanitizer.infrastructure.configurator.proxy;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedAnnotationProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWidthProxyIfNeeded(Object object, Class<?> implClass) {
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
            }
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
        } else {
            return object;
        }
    }

    @SneakyThrows
    private Object getInvocationHandlerLogic(Object object, Method method, Object[] args) {
        System.out.println("*** You use deprecated method ***");
        return method.invoke(object, args);
    }
}
