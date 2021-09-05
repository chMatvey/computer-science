package com.github.chudakov.app.model;

import com.github.chudakov.starter.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/tasks.csv")
public class Task {
    private String name;
    private String description;
    private int price;
    private long workerId;
}
