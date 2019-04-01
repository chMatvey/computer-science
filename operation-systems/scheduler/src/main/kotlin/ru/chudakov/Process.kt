package ru.chudakov

class Process(val name: String, var runDuration: Int, val timeAppearance: Int) : Comparable<Process> {
    override fun compareTo(other: Process): Int {
        return timeAppearance.compareTo(other.timeAppearance)
    }
}
