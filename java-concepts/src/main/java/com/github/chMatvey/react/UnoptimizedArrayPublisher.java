package com.github.chMatvey.react;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class UnoptimizedArrayPublisher<T> implements Publisher<T> {
    private final T[] array;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            final AtomicInteger index = new AtomicInteger(0);
            final AtomicLong requested = new AtomicLong(0);
            final AtomicBoolean canceled = new AtomicBoolean(false);

            @Override
            public void request(long n) {
                if (n <= 0) {
                    return;
                }

                long initialRequested;

                for (;;) {
                    initialRequested = requested.get();

                    if (initialRequested == Long.MAX_VALUE) {
                        return;
                    }

                    n = initialRequested + n;

                    if (n <= 0) {
                        n = Long.MAX_VALUE;
                    }

                    if (requested.compareAndSet(initialRequested, n)) {
                        break;
                    }
                }

                if (initialRequested > 0) {
                    return;
                }

                int sent = 0;

                while (true) {
                    for (; sent < requested.get() && index.get() < array.length; sent++, index.incrementAndGet()) {
                        if (canceled.get()) {
                            return;
                        }

                        T element = array[index.get()];
                        if (element == null) {
                            subscriber.onError(new NullPointerException());
                            return;
                        }
                        subscriber.onNext(element);
                    }

                    if (canceled.get()) {
                        return;
                    }

                    if (index.get() == array.length) {
                        subscriber.onComplete();
                        return;
                    }

                    if (requested.addAndGet(-sent) == 0) {
                        return;
                    }

                    sent = 0;
                }
            }

            @Override
            public void cancel() {
                this.canceled.set(true);
            }
        });
    }
}
