package ru.chudakov

import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter

fun main() {
    val inputData = readInputData("skype.csv")

    val resultTime = generateCDF(inputData.first, 1000, 0.3) ?: throw Exception()

    writeResultData("result.csv", resultTime)
}

fun readInputData(fileName: String): Pair<List<Double>, List<Int>> {
    val times = mutableListOf<Double>()
    val lengths = mutableListOf<Int>()

    BufferedReader(FileReader(fileName)).use {
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

    return Pair(times, lengths)
}

fun writeResultData(fileName: String, data: Pair<List<Double>, List<Double>>) {
    val csvHeader = "Значение функции распределения, Значение случайно велечины"

    FileWriter(fileName).use {
        //it.append("$csvHeader\n")

        for (i in 0 until data.first.size){
            it.append("${data.first[i]},${data.second[i]}\n")
        }
    }
}
