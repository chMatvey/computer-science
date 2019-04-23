package ru.chudakov.lexer

import ru.chudakov.lexer.token.IntNum
import ru.chudakov.lexer.token.Tag
import ru.chudakov.lexer.token.Token
import ru.chudakov.lexer.token.Word
import java.util.*

class Lexer {
    private val words = hashMapOf<String, Word>()

    init {
        reserve(Word(Tag.KEY, "if"))
        reserve(Word(Tag.KEY, "else"))
        reserve(Word(Tag.KEY, "var"))

        reserve(Word(Tag.SEPARATOR, "begin"))
        reserve(Word(Tag.SEPARATOR, "end"))
        reserve(Word(Tag.SEPARATOR, "("))
        reserve(Word(Tag.SEPARATOR, ")"))

        reserve(Word(Tag.UNARY_OPERATION, "--"))

        reserve(Word(Tag.BINARY_OPERATION, "-"))
        reserve(Word(Tag.BINARY_OPERATION, "+"))
        reserve(Word(Tag.BINARY_OPERATION, "*"))
        reserve(Word(Tag.BINARY_OPERATION, "/"))
        reserve(Word(Tag.BINARY_OPERATION, ">>"))
        reserve(Word(Tag.BINARY_OPERATION, "<<"))
        reserve(Word(Tag.BINARY_OPERATION, ">"))
        reserve(Word(Tag.BINARY_OPERATION, "<"))
        reserve(Word(Tag.BINARY_OPERATION, "="))
    }

    private fun reserve(word: Word) {
        words[word.lexeme] = word
    }

    fun scan(expression: String) {
        val chars = expression.toCharArray()

        chars.forEach lit@{
            
        }
    }
}
