package ru.chudakov.syntax

import ru.chudakov.lexical.token.Tag
import ru.chudakov.lexical.token.Token
import ru.chudakov.syntax.tree.Leaf
import ru.chudakov.syntax.tree.Node
import ru.chudakov.syntax.tree.Symbol

class SyntaxAnalyzer {

    fun parse(tokens: List<Token>): Symbol? {
        return getProgram(tokens)
    }

    private fun getProgram(tokens: List<Token>): Symbol? {
        val result = Node("program")

        val varToken = tokens.firstOrNull { it.name == Tag.VAR } ?: return null
        val beginToken = tokens.firstOrNull { it.attribute == "begin" } ?: return null
        val endToken = tokens.lastOrNull { it.attribute == "end" } ?: return null

        val declaration = getDeclarationOfVariables(tokens.subList(tokens.indexOf(varToken), tokens.indexOf(beginToken)))
                ?: return null
        val computing = getComputing(tokens.subList(tokens.indexOf(beginToken), tokens.lastIndexOf(endToken) + 1))
                ?: return null

        result.nodes.add(declaration)
        result.nodes.add(computing)

        return result
    }

    private fun getDeclarationOfVariables(tokens: List<Token>): Symbol? {
        val result = Node("declaration of variables")

        val varToken = tokens.firstOrNull { it.name == Tag.VAR } ?: return null

        val variables = getVariables(tokens.takeLastWhile { it != varToken }) ?: return null

        result.nodes.add(Leaf(varToken, "key word"))
        result.nodes.add(variables)

        return result
    }

    private fun getVariables(tokens: List<Token>): Symbol? {
        val result = Node("list of variables")

        tokens.forEach {
            when {
                it.name == Tag.ID -> {
                    val variable = getVariable(it) ?: return null
                    result.nodes.add(variable)
                }
                it.name == Tag.SEPARATOR && it.attribute == "," -> {
                }
                else -> return null
            }
        }

        return result
    }

    private fun getVariable(token: Token): Symbol? {
        return if (token.name == Tag.ID) {
            Leaf(token, "variable")
        } else {
            null
        }
    }

    private fun getComputing(tokens: List<Token>): Symbol? {
        val result = Node("description of computing")

        val beginToken = tokens.firstOrNull { it.attribute == "begin" } ?: return null
        val endToken = tokens.lastOrNull { it.attribute == "end" } ?: return null

        val operators = getListOperators(tokens.subList(tokens.indexOf(beginToken) + 1, tokens.lastIndexOf(endToken)))
                ?: return null

        result.nodes.add(Leaf(beginToken, "key word"))
        result.nodes.add(operators)
        result.nodes.add(Leaf(endToken, "key word"))

        return result
    }

    private fun getListOperators(tokens: List<Token>): Symbol? {
        val result = Node("list of operators")

        var index = 0;
        val operators: MutableList<MutableList<Token>> = mutableListOf()

        if (tokens[index].name == Tag.ID) {
            val assigning = mutableListOf<Token>()

            while (index < tokens.size) {
                assigning.add(tokens[index])

                index++
                if (tokens[index].isIdOrNumber() && assigning.last().isIdOrNumber()) break
            }
            operators.add(assigning)
        } else if (tokens[index].name == Tag.IF) {
            val difficultOperator = mutableListOf<Token>()

            var wasElse = false

            while (index < tokens.size) {
                difficultOperator.add(tokens[index])

                if (difficultOperator.last().name == Tag.ELSE) wasElse = true
                if (wasElse && difficultOperator.last().attribute == "end") break

                index++
                if (tokens[index].isIdOrNumber() && difficultOperator.last().isIdOrNumber() && wasElse) break
            }
            operators.add(difficultOperator)
        } else {
            return null
        }

        operators.forEach { result.nodes.add(getOperator(it) ?: return null) }

        return result
    }

    private fun getOperator(tokens: List<Token>): Symbol? {
        return if (tokens.contains(Token(Tag.IF, "if"))) {
            getDifficultOperator(tokens)
        } else {
            getAssignment(tokens)
        }
    }

    private fun getAssignment(tokens: List<Token>): Symbol? {
        val result = Node("assigment")

        val id = getId(tokens.firstOrNull { it.name == Tag.ID } ?: return null) ?: return null
        val assigment = tokens.firstOrNull { it.attribute == ":=" } ?: return null
        val expression = getExpression(tokens.takeLastWhile { it != assigment }) ?: return null

        result.nodes.add(id)
        result.nodes.add(Leaf(assigment, "key word"))
        result.nodes.add(expression)

        return result
    }

    private fun getExpression(tokens: List<Token>): Symbol? {
        val result = Node("Выражение")

        val unaryOperation = if (tokens.first().attribute == ":=") tokens.first() else null
        val restTokens = if (unaryOperation != null) tokens.subList(1, tokens.size) else tokens
        val subexpression = getSubexpression(restTokens) ?: return null

        unaryOperation?.let {
            result.nodes.add(Leaf(it, "Unary operation"))
            result.nodes.add(subexpression)
        } ?: return subexpression

        return result
    }

    private fun getSubexpression(tokens: List<Token>): Symbol? {
        val result = Node("subexpression")

        if (tokens.first().attribute == "(" && tokens.last().attribute == ")") {
            val openBracket = tokens.first()
            val closeBracket = tokens.last()
            val expression = getExpression(tokens.subList(1, tokens.size - 1)) ?: return null

            result.nodes.add(Leaf(openBracket, "("))
            result.nodes.add(expression)
            result.nodes.add(Leaf(closeBracket, ")"))
        } else if (tokens.size == 1) {
            return getOperand(tokens.first())
        } else {

        }

        return result
    }

    private fun getOperand(token: Token): Symbol? {
        return null
    }

    private fun getDifficultOperator(tokens: List<Token>): Symbol? {
        return null
    }

    private fun getId(token: Token): Symbol? {
        return if (token.name == Tag.ID) {
            Leaf(token, "variable")
        } else {
            null
        }
    }

    private fun Token.isIdOrNumber(): Boolean = this.name == Tag.ID || this.name == Tag.NUMBER
}
