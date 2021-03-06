package ru.chudakov

import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter

fun main() {
    val inputDataSkype = readInputData("skype.csv")
    val inputDataYoutube = readInputData("youtube.csv")

    val resultTimeSkype = generateCDF(inputDataSkype.first, 10000) ?: throw Exception()
    val resultSizeSkype = generateCDF(inputDataSkype.second.map { it.toDouble() }, 10000) ?: throw Exception()

    val resultTimeYoutube = generateCDF(inputDataYoutube.first, 10000) ?: throw Exception()
    val resultSizeYoutube = generateCDF(inputDataYoutube.second.map { it.toDouble() }, 10000) ?: throw Exception()

    writeResultData("skype_time.csv", resultTimeSkype)
    writeResultData("skype_size.csv", resultSizeSkype)

    writeResultData("youtube_time.csv", resultTimeYoutube)
    writeResultData("youtube_size.csv", resultSizeYoutube)
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
