package ru.chudakov.lexer.token

class DoubleNum(val value: Double) : Token(Tag.DOUBLE_NUM) {
    override fun getValue(): String {
        return value.toString()
    }
}
