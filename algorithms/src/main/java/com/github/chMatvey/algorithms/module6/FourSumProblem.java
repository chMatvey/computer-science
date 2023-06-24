package com.github.chMatvey.algorithms.module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourSumProblem {
    /**
     * Determine if there exist distinct indices i, j, k, l such that
     * array[i] + array[j] = array[k] + array[l]
     *
     * @param array array of integers
     * @return true if there exist such indices, false otherwise
     */
    public static boolean hasQuadruplet(int[] array) {
        Map<Integer, List<Pair>> sumToPair = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j];

                if (sumToPair.containsKey(sum)) {
                    List<Pair> pairList = sumToPair.get(sum);

                    for (Pair pair : pairList)
                        if (pair.first != i && pair.first != j && pair.second != i && pair.second != j) {
                            System.out.printf("[%d, %d] = [%d, %d]%n", pair.first, pair.second, i, j);
                            System.out.printf("%d + %d = %d + %d%n", array[pair.first], array[pair.second], array[i], array[j]);
                            return true;
                        }
                } else {
                    sumToPair.putIfAbsent(sum, new ArrayList<>());
                    sumToPair.get(sum).add(new Pair(i, j));
                }
            }
        }

        return false;
    }

    record Pair(int first, int second) {
    }
}
