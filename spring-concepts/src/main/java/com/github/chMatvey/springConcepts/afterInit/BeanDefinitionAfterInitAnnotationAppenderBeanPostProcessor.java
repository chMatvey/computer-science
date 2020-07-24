package com.github.chMatvey.springConcepts.afterInit;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanDefinitionAfterInitAnnotationAppenderBeanPostProcessor extends AbstractBeanDefinitionAppenderBeanPostProcessor {

    public BeanDefinitionAfterInitAnnotationAppenderBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected boolean neededSetBeanClassName(Object bean) {
        return Arrays.stream(bean.getClass().getMethods()).anyMatch(method -> method.isAnnotationPresent(AfterInit.class));
    }
}
