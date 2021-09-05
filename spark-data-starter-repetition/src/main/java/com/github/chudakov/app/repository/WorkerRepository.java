package com.github.chudakov.app.repository;

import com.github.chudakov.app.model.Worker;
import com.github.chudakov.starter.SparkRepository;

import java.util.List;


public interface WorkerRepository extends SparkRepository<Worker> {

    List<Worker> findByNumberBetween(int min, int max);
}
