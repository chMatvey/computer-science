package ru.chudakov

import ru.chudakov.lexer.Lexer

fun main() {
    val lexer = Lexer()
    foo()
}

fun foo() {
    listOf(1, 2, 3, 4, 5).forEach lit@{
        if (it == 3) return@lit // local return to the caller of the lambda, i.e. the forEach loop
        print(it)
    }
    print(" done with explicit label")
}
