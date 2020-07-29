package com.github.chMatvey.sanitizer.infrastructure.configurator.object;

import com.github.chMatvey.sanitizer.infrastructure.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object object, ApplicationContext context);
}
