package com.github.chudakov.starter.unsafe.spider;

import com.github.chudakov.starter.unsafe.transformation.SparkTransformation;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

public interface TransformationSpider {

    Tuple2<SparkTransformation, List<String>> createTransformation(List<String> remainingWords, Set<String> fieldsNames);
}
