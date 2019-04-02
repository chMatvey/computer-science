package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

interface SchedulingStrategy {
    fun schedule(processes: LinkedList<Process>): Double

    public var maxCountProcessesInQueue: Int
}
