package com.github.chMatvey.react;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2)
@Measurement(iterations = 3, time = 3, timeUnit = SECONDS)
@OutputTimeUnit(SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class ArrayPublisherPerformanceTest {
    @Param({"1000000"})
    public int times;

    UnoptimizedArrayPublisher<Integer> unoptimizedArrayPublisher;
    ArrayPublisher<Integer> arrayPublisher;

    @Setup
    public void setup() {
        Integer[] array = new Integer[times];
        Arrays.fill(array, 777);
        unoptimizedArrayPublisher = new UnoptimizedArrayPublisher<>(array);
        arrayPublisher = new ArrayPublisher<>(array);
    }

    @Benchmark
    public Object unoptimizedPublishPerformance(Blackhole bh) {
        PerformanceSubscriber subscriber = new PerformanceSubscriber(bh);
        unoptimizedArrayPublisher.subscribe(subscriber);

        return subscriber;
    }

    @Benchmark
    public Object publishPerformance(Blackhole bh) {
        PerformanceSubscriber subscriber = new PerformanceSubscriber(bh);
        arrayPublisher.subscribe(subscriber);

        return subscriber;
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
