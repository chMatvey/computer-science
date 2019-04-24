package ru.chudakov.lexer.token

class Error(val lexeme: String, val description: String) : Token(Tag.ERROR) {
    override fun getValue(): String {
        return lexeme
    }
}
