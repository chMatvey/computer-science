package com.github.chMatvey.sanitizer.infrastructure;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class<?>, Class<?>> implClasses) {
        JavaConfig config = new JavaConfig(packageToScan, implClasses);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        // todo init all singletons
        context.setObjectFactory(objectFactory);

        return context;
    }
}
