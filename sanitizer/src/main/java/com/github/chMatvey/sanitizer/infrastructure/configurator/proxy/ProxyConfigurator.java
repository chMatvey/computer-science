package com.github.chMatvey.sanitizer.infrastructure.configurator.proxy;

public interface ProxyConfigurator {
    Object replaceWidthProxyIfNeeded(Object object, Class<?> implClass);
}
