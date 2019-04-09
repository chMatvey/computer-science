package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

class RoundRobinStrategy(private val quantum: Int) : AbstractSchedulingStrategy() {
    override fun schedule(processes: LinkedList<Process>): Double {
        resetMaxCountProcessesInQueue()

        var time = 0
        var speedTime = 0
        val runDurationTime = processes.sumBy { p -> p.runDuration }
        val countProcesses = processes.size

        val processesAndRunDuration = TreeMap<Process, Int>()
        processes.forEach { p -> processesAndRunDuration[p] = p.runDuration }

        while (!processes.isEmpty()) {
            val processesInQueue = processes
                    .filter { p -> p.timeAppearance <= time }

            if (processesInQueue.count() - 1 > maxCountProcessesInQueue) {
                maxCountProcessesInQueue = processesInQueue.size - 1
            }

            val process = processesInQueue.firstOrNull()

            if (process == null) {
                time = processes.first.timeAppearance
                continue
            }

            processes.removeFirst()
            if (processesAndRunDuration.getValue(process) <= quantum) {
                time += processesAndRunDuration.getValue(process)
                processesAndRunDuration[process] = 0
                speedTime += time - process.timeAppearance
            } else {
                time += quantum
                processesAndRunDuration[process] = processesAndRunDuration.getValue(process) - quantum
                val index = processes.indexOfLast { p -> p.timeAppearance <= time } + 1
                processes.add(index, process)
            }
        }

        middleTimeInQueue = (speedTime.toDouble() - runDurationTime) / countProcesses

        return speedTime.toDouble() / countProcesses
    }
}
