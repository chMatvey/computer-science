package ru.chudakov.lexer.token

abstract class Token(val tag: Tag) {
    abstract fun getValue(): String
}
