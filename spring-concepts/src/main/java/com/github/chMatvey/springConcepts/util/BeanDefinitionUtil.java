package com.github.chMatvey.springConcepts.util;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.MethodMetadata;

public class BeanDefinitionUtil {

    public static String getBeanClassName(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        if (beanClassName == null && beanDefinition instanceof AnnotatedBeanDefinition) {
            MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
            if (factoryMethodMetadata != null) {
                beanClassName = factoryMethodMetadata.getReturnTypeName();
            }
        }

        return beanClassName;
    }
}
