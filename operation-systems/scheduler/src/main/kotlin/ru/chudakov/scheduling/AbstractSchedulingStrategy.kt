package ru.chudakov.scheduling

abstract class AbstractSchedulingStrategy() : SchedulingStrategy {
    override var maxCountProcessesInQueue = 0
    override var middleTimeInQueue = 0.0

    protected fun resetMaxCountProcessesInQueue() {
        maxCountProcessesInQueue = 0
    }

    protected fun resetMiddleTimeInQueue() {
        middleTimeInQueue = 0.0
    }
}
