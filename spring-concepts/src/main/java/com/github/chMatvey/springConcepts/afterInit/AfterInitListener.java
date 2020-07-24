package com.github.chMatvey.springConcepts.afterInit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.github.chMatvey.springConcepts.afterInit.ClassUtil.getMethodsByClassName;
import static org.springframework.util.ReflectionUtils.invokeMethod;

@Component
public class AfterInitListener {
    private ConfigurableListableBeanFactory beanFactory;

    public AfterInitListener(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();

        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String beanClassName = beanDefinition.getBeanClassName();

            if (beanClassName != null) {
                Method[] methods = getMethodsByClassName(beanClassName);
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AfterInit.class)) {
                        invokeMethod(method, context.getBean(name));
                    }
                }
            }
        }
    }
}
