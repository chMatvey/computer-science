package com.github.chudakov.starter.structure;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.List;

@Data
public class LazySparkList<T> implements List<T> {

    @Delegate
    private List<T> content;

    private Long ownerId;

    private Class<T> modelClass;

    private String foreignKeyName;

    private String pathToSource;

    public boolean initialized() {
        return content != null;
    }
}
