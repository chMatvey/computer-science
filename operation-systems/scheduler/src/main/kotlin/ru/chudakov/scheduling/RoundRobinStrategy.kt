package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

class RoundRobinStrategy(quantum: Int) : AbstractSchedulingStrategy(quantum) {
    override fun next(processes: LinkedList<Process>): Int {
        val process = processes.first()

        return if (process.runDuration <= quantum) {
            processes.removeFirst()
            process.runDuration
        } else {
            process.runDuration -= quantum
            quantum
        }
    }
}
