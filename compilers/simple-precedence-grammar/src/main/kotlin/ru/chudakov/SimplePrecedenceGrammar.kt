package ru.chudakov

import ru.chudakov.collection.PrecedenceMatrix

class SimplePrecedenceGrammar(private val rules: HashMap<Char, List<List<Char>>>,  private val matrix: PrecedenceMatrix) : Grammar {

    fun getRelation(left: Char, right: Char): PrecedenceRelation {
        if (!matrix.symbols.contains(left) || !matrix.symbols.contains(right)) {
            return PrecedenceRelation.NO
        } else if (left == '#') {
            return PrecedenceRelation.LESS
        } else if (right == '#') {
            return PrecedenceRelation.MORE
        }

        return matrix.get(left, right)
    }

    fun getSymbolByBasic(basic: List<Char>): Char? {
        if (basic.size == 1 && basic.last() == getBeginSymbol()) {
            return basic.last()
        }

        for (rule: Map.Entry<Char, List<List<Char>>> in rules) {
            if (rule.value.contains(basic)) {
                return rule.key
            }
        }

        return null
    }

    override fun getBeginSymbol(): Char {
        return matrix.symbols.first()
    }

    override fun getSymbols(): Array<Char> {
        return matrix.symbols
    }
}
