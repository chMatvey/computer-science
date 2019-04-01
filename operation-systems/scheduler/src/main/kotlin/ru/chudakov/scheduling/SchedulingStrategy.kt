package ru.chudakov.scheduling

import ru.chudakov.Process
import java.util.*

interface SchedulingStrategy {
    fun next(processes: LinkedList<Process>): Int
}
