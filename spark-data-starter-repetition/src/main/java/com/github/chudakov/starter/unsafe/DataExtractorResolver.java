package com.github.chudakov.starter.unsafe;

import com.github.chudakov.starter.unsafe.extractor.DataExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataExtractorResolver {

    private final Map<String, DataExtractor> extractorMap;

    public DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return extractorMap.get(fileExtension);
    }
}
