package ru.chudakov

class Process(val name: String, val runDuration: Int, val timeAppearance: Int) : Comparable<Process> {
    override fun compareTo(other: Process): Int {
        return timeAppearance.compareTo(other.timeAppearance)
    }

    override fun toString(): String {
        return "Процесс $name, длительность выполнения: $runDuration, время появления: $timeAppearance"
    }
}
