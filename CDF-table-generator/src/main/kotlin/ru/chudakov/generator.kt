package ru.chudakov

fun generateCDF(randomVariables: List<Double>, intervals: Int): Pair<List<Double>, List<Double>>? {
    val max = randomVariables.max() ?: return null

    return generateCDF(randomVariables, intervals, max)
}

fun generateCDF(randomVariables: List<Double>, intervals: Int, max: Double): Pair<List<Double>, List<Double>>? {
    var min = randomVariables.min() ?: return null

    if (min == 0.0) {
        min = randomVariables.filter { it != 0.0 }.min() ?: return null
    }

    val delta = (max - min).div(intervals)

    val intervalsList = mutableListOf<Double>()
    var lastInterval: Double = min
    intervalsList.add(lastInterval)

    for (i in 1..intervals) {
        lastInterval += delta
        if (lastInterval == intervalsList.last()) {
            continue
        }
        intervalsList.add(lastInterval)
    }

    intervalsList.add(max + delta)

    val frequency = Array(intervalsList.size) { 0 }

    randomVariables.forEach {
        for (i in 0 until intervalsList.size) {
            if (it <= intervalsList[i]) {
                frequency[i] += 1
                break
            }
        }
    }

    val probabilities: List<Double> = frequency.map { it.toDouble().div(randomVariables.size) }

    val cdf = mutableListOf<Double>()
    val resultInterval = mutableListOf<Double>()

    cdf.add(0.0)
    val first = if (min - delta >= 0) {
        min - delta
    } else {
        0.0
    }
    resultInterval.add(first)
    var probability = 0.0

    for (i in 0 until probabilities.size) {
        probability += probabilities[i]

        if (probability >= 1.0) {
            break
        }

        if (cdf.lastOrNull() ?: -1 != probability) {
            cdf.add(probability)
            resultInterval.add(intervalsList[i])
        }
    }

    cdf.add(1.0)
    resultInterval.add(intervalsList.last() + delta)

    return Pair(cdf, resultInterval)
}
