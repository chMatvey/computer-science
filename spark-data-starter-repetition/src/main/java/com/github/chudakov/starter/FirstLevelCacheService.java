package com.github.chudakov.starter;

import com.github.chudakov.starter.unsafe.DataExtractorResolver;
import com.github.chudakov.starter.unsafe.extractor.DataExtractor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirstLevelCacheService {

    private final Map<Class<?>, Dataset<Row>> modelToDataset = new HashMap<>();

    @Autowired
    private DataExtractorResolver resolver;

    public <T> List<T> getDataFor(Long ownerId,
                              Class<T> modelClass,
                              String foreignKeyName,
                              String pathToData,
                              ConfigurableApplicationContext context) {
        if (!modelToDataset.containsKey(modelClass)) {
            DataExtractor dataExtractor = resolver.resolve(pathToData);
            Dataset<Row> dataset = dataExtractor.readData(pathToData, context).persist();
            modelToDataset.put(modelClass, dataset);
        }
        return modelToDataset.get(modelClass)
                .filter(functions.col(foreignKeyName).equalTo(ownerId))
                .as(Encoders.bean(modelClass))
                .collectAsList();
    }
}
