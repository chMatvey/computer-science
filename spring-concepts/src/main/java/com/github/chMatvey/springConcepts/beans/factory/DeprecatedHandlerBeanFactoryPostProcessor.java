package com.github.chMatvey.springConcepts.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import static com.github.chMatvey.springConcepts.util.BeanDefinitionUtil.getBeanClassName;

public class DeprecatedHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String beanClassName = getBeanClassName(beanDefinition);

            if (beanClassName != null) {
                try {
                    Class<?> beanClass = Class.forName(beanClassName);
                    DeprecatedClass annotation = beanClass.getAnnotation(DeprecatedClass.class);
                    if (annotation != null) {
                        // todo for annotated bean
                        beanDefinition.setBeanClassName(annotation.newImpl().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}