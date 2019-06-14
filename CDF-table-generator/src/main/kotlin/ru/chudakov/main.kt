package ru.chudakov

import java.io.BufferedReader
import java.io.FileReader

fun main() {
    val times = mutableListOf<Double>()
    val lengths = mutableListOf<Int>()

    BufferedReader(FileReader("skype.csv")).use {
        var line: String?

        line = it.readLine()

        val headers = line.split(",")

        val timeIndex = headers.indexOf("\"Time\"")
        val lengthIndex = headers.indexOf("\"Length\"")

        line = it.readLine()

        var lastTime: Double? = null

        while (line != null) {
            val tokens = line.split(",").map { t -> t.substring(1, t.length - 1) }

            lengths.add(tokens[lengthIndex].toInt())

            val time = tokens[timeIndex].toDouble()
            times.add(time - (lastTime ?: time))
            lastTime = time

            line = it.readLine()
        }
    }

    for(i in 0..10) {
        println("time: ${times[i]}")
        println("length: ${lengths[i]}")
    }
}
