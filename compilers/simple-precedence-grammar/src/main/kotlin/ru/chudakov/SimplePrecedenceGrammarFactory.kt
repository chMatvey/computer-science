package ru.chudakov

import ru.chudakov.collection.PrecedenceMatrix

class SimplePrecedenceGrammarFactory {

    fun create(): SimplePrecedenceGrammar {
        val rules = HashMap<Char, List<List<Char>>>(6)

        rules['S'] = listOf(
                listOf('a', 'A'),
                listOf('C'),
                listOf('C', 'X')
        )
        rules['C'] = listOf(
                listOf('c', 'a')
        )
        rules['A'] = listOf(
                listOf('b', 'A'),
                listOf('c', 'B'),
                listOf('b')
        )
        rules['B'] = listOf(
                listOf('b', 'S')
        )
        rules['X'] = listOf(
                listOf('x', 'D')
        )
        rules['D'] = listOf(
                listOf('x'),
                listOf('x', 'X')
        )

        val symbols = arrayOf('S', 'A', 'C', 'X', 'B', 'D', 'a', 'c', 'b', 'x', '#')

        val table: Array<Array<PrecedenceRelation>> = Array(10) {Array(10) {PrecedenceRelation.NO}}

        table[2][3] = PrecedenceRelation.EQUALLY
        table[2][9] = PrecedenceRelation.LESS

        table[6][1] = PrecedenceRelation.EQUALLY
        table[6][3] = PrecedenceRelation.MORE
        table[6][7] = PrecedenceRelation.LESS
        table[6][8] = PrecedenceRelation.LESS
        table[6][9] = PrecedenceRelation.MORE

        table[7][4] = PrecedenceRelation.EQUALLY
        table[7][6] = PrecedenceRelation.EQUALLY
        table[7][8] = PrecedenceRelation.LESS

        table[8][0] = PrecedenceRelation.EQUALLY
        table[8][1] = PrecedenceRelation.EQUALLY
        table[8][2] = PrecedenceRelation.LESS
        table[8][6] = PrecedenceRelation.LESS
        table[8][7] = PrecedenceRelation.LESS
        table[8][8] = PrecedenceRelation.LESS

        table[9][3] = PrecedenceRelation.EQUALLY
        table[9][5] = PrecedenceRelation.EQUALLY
        table[9][9] = PrecedenceRelation.LESS

        return SimplePrecedenceGrammar(rules, PrecedenceMatrix(symbols, table))
    }
}
