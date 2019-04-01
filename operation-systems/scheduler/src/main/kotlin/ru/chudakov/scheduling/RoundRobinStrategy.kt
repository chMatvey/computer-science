package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

class RoundRobinStrategy(private val quantum: Int) : AbstractSchedulingStrategy() {
    override fun next(processes: LinkedList<Process>): Double {
        var time = 0
        var speedTime = 0
        val countProcesses = processes.size

        val processesAndRunDuration = TreeMap<Process, Int>()
        processes.forEach { p -> processesAndRunDuration.put(p, p.runDuration) }

        while (!processes.isEmpty()) {
            val process = processes.first

            if (process.timeAppearance > time) {
                time = process.timeAppearance
            }

            processes.removeFirst()
            if (processesAndRunDuration.getValue(process) <= quantum) {
                time += processesAndRunDuration.getValue(process)
                processesAndRunDuration[process] = 0
                speedTime += time - process.timeAppearance
            } else {
                time += quantum
                processesAndRunDuration[process] = processesAndRunDuration.getValue(process) - quantum
                //process.runDuration -= quantum
                val index = processes.indexOfLast { p -> p.timeAppearance <= time } + 1
                processes.add(index, process)
            }
        }

        return (speedTime / countProcesses).toDouble()
    }
}
