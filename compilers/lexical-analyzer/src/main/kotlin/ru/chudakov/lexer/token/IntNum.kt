package ru.chudakov.lexer.token

class IntNum(val value: Int) : Token(Tag.INT_NUM) {
    override fun getValue(): String {
        return value.toString()
    }
}
