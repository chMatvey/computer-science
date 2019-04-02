package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*
import kotlin.collections.ArrayList

class ShortestRemainingTimeStrategy : AbstractSchedulingStrategy() {
    private val timeUnit = 1

    override fun schedule(processes: LinkedList<Process>): Double {
        resetMaxCountProcessesInQueue()

        var time = 0
        var speedTime = 0
        val countProcesses = processes.size

        val processesAndRunDuration = TreeMap<Process, Int>()
        processes.forEach { p -> processesAndRunDuration[p] = p.runDuration }

        while (!processes.isEmpty()) {
            val processesInQueue = processes
                    .filter { p -> p.timeAppearance <= time }
                    .sortedBy { p -> processesAndRunDuration[p] }

            if (processesInQueue.count() - 1 > maxCountProcessesInQueue) {
                maxCountProcessesInQueue = processesInQueue.size - 1
            }

            val process = processesInQueue.firstOrNull()

            if (process == null) {
                time = processes.first.timeAppearance
                continue
            }

            time += timeUnit
            processesAndRunDuration[process] = processesAndRunDuration.getValue(process) - timeUnit

            if (processesAndRunDuration[process] == 0) {
                processes.remove(process)
                speedTime += time - process.timeAppearance
            }
        }

        return speedTime.toDouble() / countProcesses
    }
}
