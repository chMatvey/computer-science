package com.github.chMatvey.algorithms.module4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloatingArithmeticTest {
    @Test
    void equalsFirstCase() {
        double a = 0.0;
        double b = -0.0;

        Double x = a;
        Double y = b;

        assertTrue(a == b);
        assertFalse(x.equals(y));
    }

    @Test
    void equalsSecondCase() {
        double a = Double.NaN;
        double b = Double.NaN;

        Double x = a;
        Double y = b;

        assertFalse(a == b);
        assertTrue(x.equals(y));
    }
}
