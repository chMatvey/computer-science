package ru.chudakov.scheduling

abstract class AbstractSchedulingStrategy() : SchedulingStrategy {
    override var maxCountProcessesInQueue: Int = 0

    protected fun resetMaxCountProcessesInQueue() {
        maxCountProcessesInQueue = 0
    }
}
