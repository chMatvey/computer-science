package com.github.chMatvey.algorithms.test.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSortData<T extends Comparable<T>> {
    private T[] givenArray;
    private List<T> expectedList;
}
