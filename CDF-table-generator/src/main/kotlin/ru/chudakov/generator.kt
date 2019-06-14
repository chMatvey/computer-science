package ru.chudakov

fun generateCDF(randomVariables: List<Double>, intervals: Int): Pair<List<Double>, List<Double>>? {
    val max = randomVariables.max() ?: return null

    return generateCDF(randomVariables, intervals, max)
}

fun generateCDF(randomVariables: List<Double>, intervals: Int, max: Double): Pair<List<Double>, List<Double>>? {
    val min = randomVariables.min() ?: return null
    //val max = randomVariables.max() ?: return null

    val delta = (max - min).div(intervals)

    val intervalsList = mutableListOf<Double>()
    var lastInterval: Double = min
    intervalsList.add(lastInterval)

    for (i in 1..intervals) {
        lastInterval += delta
        intervalsList.add(lastInterval)
    }

    val frequency = Array(intervalsList.size) { 0 }

    randomVariables.forEach {
        for (i in 0 until intervalsList.size) {
            if (it < intervalsList[i]) {
                frequency[i] += 1
                break
            }
        }
    }

    val probabilities: List<Double> = frequency.map { it.toDouble().div(randomVariables.size) }

    val cdf = mutableListOf<Double>()

    var probability = 0.0

    probabilities.forEach {
        probability += it
        cdf.add(probability)
    }

    return Pair(cdf, intervalsList)
}
