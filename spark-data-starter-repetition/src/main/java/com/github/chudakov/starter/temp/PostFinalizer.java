package com.github.chudakov.starter.temp;

public interface PostFinalizer {

    <T> Object postFinalize(Object retVal);
}
