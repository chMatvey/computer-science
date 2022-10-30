package com.github.chMatvey.react;

import lombok.RequiredArgsConstructor;
import org.openjdk.jmh.infra.Blackhole;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@RequiredArgsConstructor
public class PerformanceSubscriber implements Subscriber<Object> {
    final Blackhole bh;

    Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Object item) {
        bh.consume(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        bh.consume(throwable);
    }

    @Override
    public void onComplete() {
        bh.consume(true);
    }
}
