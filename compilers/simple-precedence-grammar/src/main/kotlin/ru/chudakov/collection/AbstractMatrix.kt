package ru.chudakov.collection

abstract class AbstractMatrix<T>(protected val table: Array<Array<T>>) : Matrix<T> {
    override val height = table.size
    override val width = table.first().size

    override fun get(row: Int, column: Int): T {
        return table[row][column]
    }

    override fun set(row: Int, column: Int, value: T) {
        table[row][column] = value
    }
}
