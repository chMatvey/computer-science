package ru.chudakov

import ru.chudakov.lexer.Lexer
import ru.chudakov.lexer.token.Error
import ru.chudakov.lexer.token.Tag
import java.io.File

fun main() {
    val lexer = Lexer()
    val expression = readFileDirectlyAsText("example.txt")

    val table = lexer.scan(expression)

    table.getLexemes().forEach {
        System.out.println("Лексема: ${it.getValue()}; + ${it.getTag()}; Строка ${it.line}; ${if (it.token is Error) it.token.description else ""}")
    }
}

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)
