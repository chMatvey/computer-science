package com.github.chudakov.starter.unsafe.transformation;

import com.github.chudakov.starter.structure.OrderedBag;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface SparkTransformation {
    Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldsNames, OrderedBag<Object> args);
}
