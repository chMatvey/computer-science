package ru.chudakov

import ru.chudakov.scheduling.RoundRobinStrategy
import ru.chudakov.scheduling.ShortestRemainingTimeStrategy
import java.util.*
import kotlin.random.Random

fun main() {
    val roundRobinStrategy = RoundRobinStrategy(2)
    val shortestRemainingTimeStrategy = ShortestRemainingTimeStrategy()

    val roundRobinScheduler = Scheduler(roundRobinStrategy)
    val shortestRemainingTimeScheduler = Scheduler(shortestRemainingTimeStrategy)

    val processes = TreeSet<Process>()
    var timeAppearance = 0

    for (i in 1..40) {
        processes.add(Process(
                i.toString(),
                Random.nextInt(4, 11),
                timeAppearance
        ))
        timeAppearance += Random.nextInt(2, 9)
    }
//    processes.add(Process("1", 7, 0))
//    processes.add(Process("2", 6, 5))
//    processes.add(Process("3", 10, 8))
//    processes.add(Process("4", 8, 16))

    roundRobinScheduler.addProcesses(LinkedList<Process>(processes))
    shortestRemainingTimeScheduler.addProcesses(LinkedList<Process>(processes))

    System.out.println("Среднее время оборота процессов для RR алгоритма")
    System.out.println(roundRobinScheduler.run())
    System.out.println("Среднее время оборота процессов для SRT алгоритма")
    System.out.println(shortestRemainingTimeScheduler.run())
}

fun test() {
    System.out.println("Никитка")
    val list = mutableListOf<Process>(
            ru.chudakov.Process("1", 1, 1),
            ru.chudakov.Process("3", 3, 3)
    )
    changelist(list)
    list.add(1, ru.chudakov.Process("2", 2, 2))
    list.forEach { p -> System.out.println(p.name + " " + p.runDuration) }
}

fun changelist(list: List<Process>) {
    val element = list[0]
    element.runDuration = 44
}
