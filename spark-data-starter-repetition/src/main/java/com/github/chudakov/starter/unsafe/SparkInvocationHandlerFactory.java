package com.github.chudakov.starter.unsafe;

import com.github.chudakov.starter.Source;
import com.github.chudakov.starter.SparkRepository;
import com.github.chudakov.starter.Transient;
import com.github.chudakov.starter.temp.LazyCollectionSupportPostFinalizer;
import com.github.chudakov.starter.temp.SparkInvocationHandler;
import com.github.chudakov.starter.temp.SparkInvocationHandlerImpl;
import com.github.chudakov.starter.unsafe.extractor.DataExtractor;
import com.github.chudakov.starter.unsafe.finalizer.Finalizer;
import com.github.chudakov.starter.unsafe.spider.TransformationSpider;
import com.github.chudakov.starter.unsafe.transformation.SparkTransformation;
import com.github.chudakov.starter.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.chudakov.starter.util.WordsMatcher.findAndRemoveIfPiecesMatch;
import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {

    private final DataExtractorResolver extractorResolver;
    private final Map<String, TransformationSpider> spiderMap;
    private final Map<String, Finalizer> finalizerMap;

    @Setter
    private ConfigurableApplicationContext realContext;

    public SparkInvocationHandler create(Class<? extends SparkRepository> repositoryInterface) {
        Class<?> modelClass = getModelClass(repositoryInterface);
        Set<String> fieldsNames = getFieldsNames(modelClass);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        DataExtractor dataExtractor = extractorResolver.resolve(pathToData);

        Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> methodToFinalizer = new HashMap<>();

        Method[] methods = repositoryInterface.getMethods();
        for (Method method : methods) {
            List<Tuple2<SparkTransformation, List<String>>> transformations = new ArrayList<>();
            ArrayList<String> methodWords = new ArrayList<>(asList(method.getName().split("(?=\\p{Upper})")));
            TransformationSpider currentSpider = null;
            while (methodWords.size() > 1) {
                String strategyName = findAndRemoveIfPiecesMatch(spiderMap.keySet(), methodWords);
                if (!strategyName.isEmpty()) {
                    currentSpider = spiderMap.get(strategyName);
                }
                Tuple2<SparkTransformation, List<String>> tuple = currentSpider.createTransformation(methodWords, fieldsNames);
                transformations.add(tuple);
            }
            String finalizerName = "collect";
            if (methodWords.size() == 1) {
                finalizerName = StringUtil.decapitalize(methodWords.remove(0));
            }
            transformationChain.put(method, transformations);
            methodToFinalizer.put(method, finalizerMap.get(finalizerName));

        }

        return SparkInvocationHandlerImpl.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(methodToFinalizer)
                .postFinalizer(new LazyCollectionSupportPostFinalizer(realContext))
                .context(realContext)
                .build();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> repositoryInterface) {
        ParameterizedType genericInterface = (ParameterizedType) repositoryInterface.getGenericInterfaces()[0];
        return (Class<?>) genericInterface.getActualTypeArguments()[0];
    }

    private Set<String> getFieldsNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
