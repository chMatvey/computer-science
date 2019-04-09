package ru.chudakov

interface Grammar {
    fun getBeginSymbol(): Char

    fun getSymbols(): Array<Char>
}
