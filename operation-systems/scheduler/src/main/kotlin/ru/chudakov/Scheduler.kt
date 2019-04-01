package ru.chudakov

import ru.chudakov.scheduling.SchedulingStrategy
import java.util.*
import kotlin.collections.ArrayList

class Scheduler(private val strategy: SchedulingStrategy) {
    private val processesQueue: LinkedList<Process> = LinkedList()

    fun addProcess(process: Process) {
        processesQueue.add(process)
    }

    fun addProcesses(processes: List<Process>) {
        processesQueue.addAll(processes)
    }

    fun run(): Double {
        return strategy.next(processesQueue)
    }
}
