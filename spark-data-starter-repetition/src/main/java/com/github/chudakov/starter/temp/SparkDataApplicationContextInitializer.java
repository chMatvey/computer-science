package com.github.chudakov.starter.temp;

import com.github.chudakov.starter.SparkRepository;
import com.github.chudakov.starter.unsafe.DataExtractorResolver;
import com.github.chudakov.starter.unsafe.SparkInvocationHandlerFactory;
import com.github.chudakov.starter.util.StringUtil;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

public class SparkDataApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        SparkInvocationHandlerFactory factory = createInvocationHandlerFactory(context);

        registerSparkBeans(context);
        Reflections scanner = new Reflections(context.getEnvironment().getProperty("spark.packages-to-scan"));

        scanner.getSubTypesOf(SparkRepository.class).forEach(sparkRepositoryInterface -> {
            Object crank = Proxy.newProxyInstance(
                    sparkRepositoryInterface.getClassLoader(),
                    new Class[]{sparkRepositoryInterface},
                    factory.create(sparkRepositoryInterface)
            );
            context.getBeanFactory().registerSingleton(
                    StringUtil.decapitalize(sparkRepositoryInterface.getSimpleName()),
                    crank
            );
        });
    }

    private void registerSparkBeans(ConfigurableApplicationContext context) {
        String appName = context.getEnvironment().getProperty("spark.app-name");
        SparkSession.Builder builder = SparkSession.builder().master("local[*]");
        SparkSession sparkSession = builder.appName(appName).getOrCreate();
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        context.getBeanFactory().registerSingleton("sparkSession", sparkSession);
        context.getBeanFactory().registerSingleton("sparkContext", sparkContext);
    }

    private SparkInvocationHandlerFactory createInvocationHandlerFactory(ConfigurableApplicationContext context) {
        AnnotationConfigApplicationContext tempContext = new AnnotationConfigApplicationContext(InternalConfiguration.class);
        SparkInvocationHandlerFactory factory = tempContext.getBean(SparkInvocationHandlerFactory.class);
        factory.setRealContext(context);
        registerExtractorResolverInRealContext(tempContext, context);
        tempContext.close();

        return factory;
    }

    private void registerExtractorResolverInRealContext(ConfigurableApplicationContext tempContext,
                                                        ConfigurableApplicationContext realContext) {
        DataExtractorResolver extractorResolver = tempContext.getBean(DataExtractorResolver.class);
        realContext.getBeanFactory().registerSingleton("extractorResolverForSpark", extractorResolver);
    }
}
