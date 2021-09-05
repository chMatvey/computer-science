package com.github.chudakov.starter;

import com.github.chudakov.starter.structure.LazySparkList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@Aspect
public class LazyListSupportAspect {

    @Autowired
    private FirstLevelCacheService cacheService;

    @Autowired
    private ConfigurableApplicationContext context;

    @Before("execution(* com.github.chudakov.starter.structure.LazySparkList.*(..)) && execution(* java.util.List.*(..))")
    public <T> void beforeEachMethodInvocationCheckAndFillContent(JoinPoint jp) {
        LazySparkList<T> lazyList = (LazySparkList<T>) jp.getTarget();
        if (!lazyList.initialized()) {
            List<T> list = cacheService.getDataFor(
                    lazyList.getOwnerId(),
                    lazyList.getModelClass(),
                    lazyList.getForeignKeyName(),
                    lazyList.getPathToSource(),
                    context
            );
            lazyList.setContent(list);
        }
    }
}
