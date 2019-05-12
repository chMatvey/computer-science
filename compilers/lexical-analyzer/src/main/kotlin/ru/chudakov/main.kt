package ru.chudakov

import ru.chudakov.lexer.Lexer
import ru.chudakov.lexer.token.Error
import ru.chudakov.lexer.token.Tag
import java.io.File

fun main() {
    val lexer = Lexer()
    val expression = readFileDirectlyAsText("E:\\project\\computer-science\\compilers\\lexical-analyzer\\example.txt")

    val table = lexer.scan(expression)

    table.getLexemes().forEach {
        var errorMessage = ""
        if (it.token is Error) errorMessage = it.token.description
        System.out.println("Лексема: " + it.getValue() + "; " + it.getTag() + "; Строка " + it.line +  "; " + errorMessage)
    }
}

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)
