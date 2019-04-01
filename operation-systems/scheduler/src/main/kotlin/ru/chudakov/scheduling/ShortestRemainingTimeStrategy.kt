package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

class ShortestRemainingTimeStrategy : AbstractSchedulingStrategy() {
    private val timeUnit = 1

    override fun next(processes: LinkedList<Process>): Double {
        var time = 0
        var speedTime = 0
        val countProcesses = processes.size

        while (!processes.isEmpty()) {
            val process = processes
                    .filter { p -> p.timeAppearance <= time }
                    .sortedBy { p -> p.runDuration }
                    .firstOrNull()

            if (process == null) {
                time = processes.first.timeAppearance
                continue
            }

            time += timeUnit
            process.runDuration -= timeUnit

            if (process.runDuration == 0) {
                processes.remove(process)
                speedTime += time - process.timeAppearance
            }
        }

        return (speedTime / countProcesses).toDouble()
    }
}
