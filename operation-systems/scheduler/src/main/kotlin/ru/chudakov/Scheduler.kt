package ru.chudakov

import ru.chudakov.scheduling.SchedulingStrategy
import java.util.*

class Scheduler(private val strategy: SchedulingStrategy) {
    private val processesQueue: LinkedList<Process> = LinkedList()

    fun addProcess(process: Process) {
        processesQueue.add(process)
    }

    fun addProcesses(processes: List<Process>) {
        processesQueue.addAll(processes)
    }

    fun run(): Double {
        return strategy.schedule(processesQueue)
    }

    fun getMaxCountProcessesInQueue(): Int {
        return strategy.maxCountProcessesInQueue
    }

    fun getMiddleTimeInQueue(): Double {
        return strategy.middleTimeInQueue
    }
}
