package ru.chudakov

import ru.chudakov.lexical.Tokenizer
import ru.chudakov.syntax.SyntaxAnalyzer
import ru.chudakov.writer.XMLEncoder
import java.io.File
import kotlin.Exception

fun main() {
    val tokenizer = Tokenizer()
    val syntaxAnalyzer = SyntaxAnalyzer()
    val writer = XMLEncoder("result.xml")

    val expression = readFileDirectlyAsText("example.txt")

    val tokens = tokenizer.getTokens(expression)
    val tree = syntaxAnalyzer.parse(tokens) ?: throw Exception()

    writer.encode(tree)
}

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)
