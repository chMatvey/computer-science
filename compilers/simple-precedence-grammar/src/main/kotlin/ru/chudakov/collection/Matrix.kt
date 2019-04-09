package ru.chudakov.collection

interface Matrix<T> {
    val height: Int
    val width: Int

    operator fun get(row: Int, column: Int): T

    operator fun set(row: Int, column: Int, value: T)

}
