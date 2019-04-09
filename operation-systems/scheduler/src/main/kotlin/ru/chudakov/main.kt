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
        System.out.println(processes.last())
        timeAppearance += Random.nextInt(2, 9)
    }

//    processes.add(Process("1", 7, 0))
//    processes.add(Process("2", 6, 5))
//    processes.add(Process("3", 10, 8))
//    processes.add(Process("4", 8, 16))

    roundRobinScheduler.addProcesses(LinkedList<Process>(processes))
    shortestRemainingTimeScheduler.addProcesses(LinkedList<Process>(processes))

    System.out.println()
    System.out.println("Среднее время оборота процессов для RR алгоритма: ${roundRobinScheduler.run()}")
    System.out.println("Среднее время нахождения в очереди: ${roundRobinScheduler.getMiddleTimeInQueue()}")
    System.out.println("Максимальное количество процессов в очереди: ${roundRobinScheduler.getMaxCountProcessesInQueue()}")

    System.out.println("Среднее время оборота процессов для SRT алгоритма: ${shortestRemainingTimeScheduler.run()}")
    System.out.println("Среднее время нахождения в очереди: ${shortestRemainingTimeScheduler.getMiddleTimeInQueue()}")
    System.out.println("Максимальное количество процессов в очереди: ${shortestRemainingTimeScheduler.getMaxCountProcessesInQueue()}")
}
