package com.github.chMatvey.react;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

import static java.util.concurrent.ForkJoinPool.commonPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

class ArrayPublisherTest extends PublisherVerification<Long> {

    public ArrayPublisherTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long elements) {
        return new ArrayPublisher<>(generate(elements));
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }

    @Test
    void signalsMustBeEmittedInTheRightOrder() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Long> collected = new ArrayList<>();
        List<Integer> order = new ArrayList<>();
        long toRequest = 5L;
        Long[] array = generate(toRequest);
        Publisher<Long> publisher = new ArrayPublisher<>(array);

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                order.add(1);
                subscription.request(toRequest);
            }

            @Override
            public void onNext(Long aLong) {
                collected.add(aLong);
                if (!order.contains(2)) {
                    order.add(2);
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                order.add(3);
                latch.countDown();
            }
        });

        latch.await(1, SECONDS);

        assertEquals(List.of(1, 2, 3), order);
        assertEquals(Arrays.asList(array), collected);
    }

    @Test
    void mustSupportBackpressureControl() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Long> collected = new ArrayList<>();
        long toRequest = 5L;
        Long[] array = generate(toRequest);
        Publisher<Long> publisher = new ArrayPublisher<>(array);
        Subscription[] subscriptions = new Subscription[1];

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscriptions[0] = subscription;
            }

            @Override
            public void onNext(Long aLong) {
                collected.add(aLong);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        assertEquals(collected, Collections.emptyList());

        subscriptions[0].request(1);
        assertEquals(List.of(0L), collected);

        subscriptions[0].request(1);
        assertEquals(List.of(0L, 1L), collected);

        subscriptions[0].request(2);
        assertEquals(List.of(0L, 1L, 2L, 3L), collected);

        subscriptions[0].request(20);

        latch.await(1, SECONDS);

        assertEquals(Arrays.asList(array), collected);
    }

    @Test
    void mustSendNPENormally() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Long[] array = new Long[] {null};
        AtomicReference<Throwable> error = new AtomicReference<>();
        Publisher<Long> publisher = new ArrayPublisher<>(array);

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(4);
            }

            @Override
            public void onNext(Long aLong) {
            }

            @Override
            public void onError(Throwable throwable) {
                error.set(throwable);
                latch.countDown();
            }

            @Override
            public void onComplete() {
            }
        });

        latch.await(1, SECONDS);

        assertTrue(error.get() instanceof NullPointerException);
    }

    @Test
    void mustNotDieInStackOverflow() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Long> collected = new ArrayList<>();
        long toRequest = 5L;
        Long[] array = generate(toRequest);
        Publisher<Long> publisher = new ArrayPublisher<>(array);

        publisher.subscribe(new Subscriber<Long>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Long aLong) {
                collected.add(aLong);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await(5, SECONDS);

        assertEquals(Arrays.asList(array), collected);
    }

    @Test
    void mustBePossibleToCancelSubscription() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Long> collected = new ArrayList<>();
        long toRequest = 1000L;
        Long[] array = generate(toRequest);
        Publisher<Long> publisher = new ArrayPublisher<>(array);


        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.cancel();
                subscription.request(toRequest);
            }

            @Override
            public void onNext(Long aLong) {
                collected.add(aLong);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await(1, SECONDS);

        assertEquals(Collections.emptyList(), collected);
    }

    @Test
    void multithreadingTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Long> collected = new ArrayList<>();
        long n = 5000L;
        Long[] array = generate(n);
        Publisher<Long> publisher = new ArrayPublisher<>(array);

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                for (int i = 0; i < n; i++) {
                    commonPool().execute(() -> subscription.request(1));
                }
            }

            @Override
            public void onNext(Long aLong) {
                collected.add(aLong);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });

        latch.await(5, SECONDS);

        assertEquals(array.length, collected.size());
        assertEquals(Arrays.asList(array), collected);
    }

    static Long[] generate(long num) {
        return LongStream.range(0, num >= Integer.MAX_VALUE ? 1_000_000 : num)
                .boxed()
                .toArray(Long[]::new);
    }
}