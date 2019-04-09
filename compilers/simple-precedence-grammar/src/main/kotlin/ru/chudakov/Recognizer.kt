package ru.chudakov

class Recognizer {

    fun shiftFoldAlgorithm(grammar: SimplePrecedenceGrammar, expression: String): Boolean {
        val chars = expression.toCharArray().toMutableList()

        chars.add('#')
        val stack = mutableListOf<Char>()

        stack.add('#')

        while (!chars.isEmpty() && !isBeginSymbol(stack, grammar)) {
            when (grammar.getRelation(stack.last(), chars.first())) {
                PrecedenceRelation.LESS, PrecedenceRelation.EQUALLY -> {
                    stack.add(chars.removeAt(0))
                }
                PrecedenceRelation.MORE -> {
                    if (!fold(stack, grammar)) {
                        return false
                    }
                }
                PrecedenceRelation.NO -> {
                    return false
                }
            }
        }

        stack.remove('#')

        return stack.size == 1 && stack.last() == grammar.getBeginSymbol()
    }

    private fun fold(stack: MutableList<Char>, grammar: SimplePrecedenceGrammar): Boolean {
        val basic = mutableListOf(stack.removeAt(stack.size - 1))

        while (!stack.isEmpty() && grammar.getRelation(stack.last(), basic.last()) != PrecedenceRelation.LESS) {
            basic.add(stack.removeAt(stack.size - 1))
        }

        val symbol = grammar.getSymbolByBasic(basic.reversed()) ?: return false

        stack.add(symbol as Char)
        return true
    }

    private fun isBeginSymbol(stack: List<Char>, grammar: SimplePrecedenceGrammar): Boolean {
        return stack.size == 2 && stack.last() == grammar.getBeginSymbol() && stack.first() == '#'
    }
}
