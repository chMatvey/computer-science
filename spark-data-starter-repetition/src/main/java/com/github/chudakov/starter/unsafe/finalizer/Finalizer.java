package com.github.chudakov.starter.unsafe.finalizer;

import com.github.chudakov.starter.structure.OrderedBag;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface Finalizer {
    Object doAction(Dataset<Row> dataset, Class<?> modelClass, OrderedBag<?> orderedBag);
}
