package com.github.chMatvey.springConcepts.infra;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(InfraConfig.class)
public @interface EnableAfterInit {
}
