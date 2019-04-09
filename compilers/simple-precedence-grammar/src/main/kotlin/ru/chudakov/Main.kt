package ru.chudakov

fun main() {
    val expression = "ab"
    val recognizer = Recognizer()
    val factory = SimplePrecedenceGrammarFactory()
    val grammar = factory.create()

    System.out.println(recognizer.shiftFoldAlgorithm(grammar, expression))
}
