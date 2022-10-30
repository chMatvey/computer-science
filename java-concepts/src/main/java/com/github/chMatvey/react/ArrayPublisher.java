package com.github.chMatvey.react;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

@RequiredArgsConstructor
public class ArrayPublisher<T> implements Publisher<T> {
    private final T[] array;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new ArraySubscription<>(array, subscriber));
    }

    @RequiredArgsConstructor
    private static class ArraySubscription<T> implements Subscription {
        private final T[] array;
        private final Subscriber<? super T> subscriber;
        private int index = 0;
        private volatile boolean canceled = false;
        private volatile long requested = 0;

        private static final AtomicLongFieldUpdater<ArraySubscription> REQUESTED =
                AtomicLongFieldUpdater.newUpdater(ArraySubscription.class, "requested");

        @Override
        public void request(long n) {
            if (n <= 0) {
                return;
            }

            long initialRequested;

            do {
                initialRequested = REQUESTED.get(this);
                if (initialRequested == Long.MAX_VALUE) {
                    return;
                }
                n = initialRequested + n;
                if (n <= 0) {
                    n = Long.MAX_VALUE;
                }
            } while (!REQUESTED.compareAndSet(this, initialRequested, n));

            if (initialRequested > 0) {
                return;
            }

            if (n > (array.length - index)) {
                fastPath();
            } else {
                slowPath(n);
            }
        }

        @Override
        public void cancel() {
            this.canceled = true;
        }

        private void fastPath() {
            int index = this.index;
            T[] array = this.array;
            int length = array.length;
            Subscriber<? super T> subscriber = this.subscriber;

            for (; index < length; index++) {
                if (canceled) {
                    return;
                }
                T element = array[index];
                if (element == null) {
                    subscriber.onError(new NullPointerException());
                    return;
                }
                subscriber.onNext(element);
            }

            if (canceled) {
                return;
            }

            subscriber.onComplete();
        }

        private void slowPath(long n) {
            int sent = 0;
            int index = this.index;
            T[] array = this.array;
            Subscriber<? super T> subscriber = this.subscriber;

            while (true) {
                for (; sent < n && index < array.length; sent++, index++) {
                    if (canceled) {
                        return;
                    }
                    T element = array[index];
                    if (element == null) {
                        subscriber.onError(new NullPointerException());
                        return;
                    }
                    subscriber.onNext(element);
                }

                if (canceled) {
                    return;
                }

                if (index == array.length) {
                    subscriber.onComplete();
                    return;
                }

                n = requested;

                if (n == sent) {
                    this.index = index;
                    n = REQUESTED.addAndGet(this, -sent);
                    if (n == 0) {
                        return;
                    }
                    sent = 0;
                }
            }
        }
    }
}
