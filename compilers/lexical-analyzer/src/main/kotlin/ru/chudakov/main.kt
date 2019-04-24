package ru.chudakov

import ru.chudakov.lexer.Lexer
import java.io.File

fun main() {
    val lexer = Lexer()
    val expression = readFileDirectlyAsText("E:\\project\\computer-science\\compilers\\lexical-analyzer\\example.txt")

    val table = lexer.scan(expression)

    table.getLexemes().forEach {
        System.out.println("Лексема: " + it.getValue() + "; " + it.getTag() + "; Строка " + it.line)
    }
}

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)
