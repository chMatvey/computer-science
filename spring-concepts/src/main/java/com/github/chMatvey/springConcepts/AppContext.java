package com.github.chMatvey.springConcepts;

import com.github.chMatvey.springConcepts.beanPostProcessor.InjectRandomIntAnnotationBeanPostProcessor;
import com.github.chMatvey.springConcepts.beanPostProcessor.PostProxyContextListener;
import com.github.chMatvey.springConcepts.beanPostProcessor.ProfilingAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
    @Bean
    public InjectRandomIntAnnotationBeanPostProcessor injectRandomIntAnnotationBeanPostProcessor() {
        return new InjectRandomIntAnnotationBeanPostProcessor();
    }

    @Bean
    public ProfilingAnnotationBeanPostProcessor profilingAnnotationPostProcessor() throws Exception {
        return new ProfilingAnnotationBeanPostProcessor();
    }

    @Bean
    public PostProxyContextListener postProxyContextListener() {
        return new PostProxyContextListener();
    }

    @Bean
    public TerminatorQuoter terminatorQuoter() {
        return new TerminatorQuoter();
    }
}
