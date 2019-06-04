package ru.chudakov.lexer

import ru.chudakov.lexer.token.Tag
import ru.chudakov.lexer.token.Token

class Lexeme(val token: Token, val line: Int) {
    val lines = mutableListOf<Int>()

    fun getValue(): String {
        return token.getValue()
    }

    fun getTag(): Tag {
        return token.tag
    }
}
