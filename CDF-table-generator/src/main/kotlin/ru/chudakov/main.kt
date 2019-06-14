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
        val timeIndex = headers.indexOf("Time")
        val lengthIndex = headers.indexOf("Length")
        line = it.readLine()

        while (line != null) {
            val tokens = line.split(",")

            if (tokens.isNotEmpty()) {
                lengths.add(tokens[lengthIndex].toInt())
                times.add(times.lastOrNull() ?: tokens[timeIndex].toDouble() - tokens[timeIndex].toDouble())
            }

            line = it.readLine()
        }
    }

    for(i in 0..10) {
        println("time: $times[i]")
        println("length: $lengths[i]")
    }
}
