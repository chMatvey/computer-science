package com.github.chMatvey.springConcepts.afterInit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;

public abstract class AbstractBeanDefinitionAppenderBeanPostProcessor implements BeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    public AbstractBeanDefinitionAppenderBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(beanFactory.getBeanDefinitionNames()).parallel().forEach(name -> {
            if (neededSetBeanClassName(bean)) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                if (beanDefinition.getBeanClassName() == null) {
                    beanDefinition.setBeanClassName(bean.getClass().getCanonicalName());
                }
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected abstract boolean neededSetBeanClassName(Object bean);
}
