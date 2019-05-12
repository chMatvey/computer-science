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

    override fun equals(other: Any?): Boolean {
        var result = super.equals(other)
        if (other is Lexeme) {
            result = this.token.getValue() == other.token.getValue()
        }
        return result
    }
}
