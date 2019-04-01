package ru.chudakov

import ru.chudakov.scheduling.SchedulingStrategy
import java.util.*
import kotlin.collections.ArrayList

class Scheduler(private val strategy: SchedulingStrategy) {
    private val processesQueue: LinkedList<Process> = LinkedList()

    fun addProcess(process: Process) {
        processesQueue.add(process)
    }

    fun run() {
        val middleTimeSpeeds = LinkedList<Int>()
        var time = 0

        while (!processesQueue.isEmpty()) {
            val countProcesses = processesQueue.size
            time += strategy.next(processesQueue)

            if (countProcesses != processesQueue.size) {
                middleTimeSpeeds.add(time - )
            }
        }
    }
}
