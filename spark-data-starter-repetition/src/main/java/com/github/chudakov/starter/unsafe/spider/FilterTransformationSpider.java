package com.github.chudakov.starter.unsafe.spider;

import com.github.chudakov.starter.unsafe.transformation.FilterTransformation;
import com.github.chudakov.starter.unsafe.transformation.SparkTransformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.chudakov.starter.util.WordsMatcher.findAndRemoveIfPiecesMatch;

@Component("findBy")
@RequiredArgsConstructor
public class FilterTransformationSpider implements TransformationSpider {

    private final Map<String, FilterTransformation> transformationMap;

    @Override
    public Tuple2<SparkTransformation, List<String>> createTransformation(List<String> remainingWords, Set<String> fieldsNames) {
        String fieldName = findAndRemoveIfPiecesMatch(fieldsNames, remainingWords);
        String filterName = findAndRemoveIfPiecesMatch(transformationMap.keySet(), remainingWords);
        return new Tuple2<>(transformationMap.get(filterName), List.of(fieldName));
    }
}
