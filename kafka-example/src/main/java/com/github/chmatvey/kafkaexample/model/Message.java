package com.github.chmatvey.kafkaexample.model;

import java.time.LocalDateTime;

public record Message(String key,
                      String message,
                      LocalDateTime created) {
}
