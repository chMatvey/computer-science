package com.github.chudakov.app.model;

import com.github.chudakov.starter.ForeignKey;
import com.github.chudakov.starter.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/workers.csv")
public class Worker {
    private long id;
    private String name;
    private int number;

    @ForeignKey("workerId")
    private List<Task> tasks;
}
