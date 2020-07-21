package com.github.chMatvey.springConcepts.beanPostProcessor;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.MethodMetadata;

import java.lang.reflect.Method;

public class PostProxyContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();

        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String originalClassName = null;
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
                if (factoryMethodMetadata != null) {
                    originalClassName = factoryMethodMetadata.getReturnTypeName();
                }
            } else {
                originalClassName = beanDefinition.getBeanClassName();
            }

            if (originalClassName != null) {
                try {
                    Class<?> originalClass = Class.forName(originalClassName);
                    Method[] methods = originalClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(PostProxy.class)) {
                            Object bean = context.getBean(name);
                            Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                            currentMethod.invoke(bean, (Object[]) currentMethod.getParameters());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
