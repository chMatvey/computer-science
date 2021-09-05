package com.github.chudakov.starter.temp;

import com.github.chudakov.starter.ForeignKey;
import com.github.chudakov.starter.Source;
import com.github.chudakov.starter.structure.LazySparkList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class LazyCollectionSupportPostFinalizer implements PostFinalizer {

    private final ConfigurableApplicationContext context;

    @SneakyThrows
    @Override
    public <T> Object postFinalize(Object retVal) {
        if (Collection.class.isAssignableFrom(retVal.getClass())) {
            for (Object model : (List<?>) retVal) {
                Field idField = model.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                long ownerId = idField.getLong(model);

                Field[] fields = model.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (List.class.isAssignableFrom(field.getType())) {
                        LazySparkList<T> lazySparkList = context.getBean(LazySparkList.class);
                        lazySparkList.setOwnerId(ownerId);
                        String foreignKeyName = field.getAnnotation(ForeignKey.class).value();
                        lazySparkList.setForeignKeyName(foreignKeyName);
                        Class<T> embeddedModel = getEmbeddedModel(field);
                        lazySparkList.setModelClass(embeddedModel);
                        String pathToSource = embeddedModel.getAnnotation(Source.class).value();
                        lazySparkList.setPathToSource(pathToSource);

                        field.setAccessible(true);
                        field.set(model, lazySparkList);
                    }
                }
            }
        }
        return retVal;
    }

    private <T> Class<T> getEmbeddedModel(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Class<T> embeddedModel = (Class<T>) genericType.getActualTypeArguments()[0];
        return embeddedModel;
    }
}
