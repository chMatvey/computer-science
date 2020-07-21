package com.github.chMatvey.springConcepts;

import com.github.chMatvey.springConcepts.beanPostProcessor.InjectRandomInt;
import com.github.chMatvey.springConcepts.beanPostProcessor.PostProxy;
import com.github.chMatvey.springConcepts.beanPostProcessor.Profiling;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {
    @InjectRandomInt(min = 2, max = 5)
    private int repeat;

    private String message = "Message";

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    @PostProxy
    @Override
    public void postProxy() {
        System.out.println("Phase 3");
        sayQuote();
    }

    @Override
    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
