package ru.chudakov.collection

import ru.chudakov.PrecedenceRelation

class PrecedenceMatrix(val symbols: Array<Char>, table: Array<Array<PrecedenceRelation>>) : AbstractMatrix<PrecedenceRelation>(table) {

    fun get(left: Char, right: Char): PrecedenceRelation {
        val row = symbols.indexOf(left)
        val column = symbols.indexOf(right)
        return table[row][column]
    }
}
