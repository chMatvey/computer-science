package ru.chudakov.lexer.token

class Word(tag: Tag, val lexeme: String) : Token(tag) {
    override fun getValue(): String {
        return lexeme
    }
}
