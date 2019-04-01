package ru.chudakov

import java.util.*

fun main() {
    System.out.println("Никитка")
    val list = arrayListOf(
            ru.chudakov.Process("1", 1, 1),
            ru.chudakov.Process("2", 2, 1)
    )
    changelist(list)
    System.out.println(list[0].runDuration)
}

fun changelist(list: List<Process>) {
    val element = list[0]
    element.runDuration = 44
}
