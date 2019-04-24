package ru.chudakov.lexer

import ru.chudakov.lexer.token.*
import kotlin.Error

class Lexer {
    private val words = hashMapOf<String, Word>()
    private val intRegex = Regex("[-+]?\\d+")
    private val doubleRegex = Regex("-?\\d+(\\.\\d+)?")
    private val variableRegex = Regex("[a-zA-Z]+")

    init {
        reserve(Word(Tag.KEY, "if"))
        reserve(Word(Tag.KEY, "else"))
        reserve(Word(Tag.KEY, "var"))

        reserve(Word(Tag.KEY, "begin"))
        reserve(Word(Tag.KEY, "end"))

        reserve(Word(Tag.SEPARATOR, "("))
        reserve(Word(Tag.SEPARATOR, ")"))
        reserve(Word(Tag.SEPARATOR, ","))

        reserve(Word(Tag.UNARY_OPERATION, "-"))

        reserve(Word(Tag.BINARY_OPERATION, "-"))
        reserve(Word(Tag.BINARY_OPERATION, "+"))
        reserve(Word(Tag.BINARY_OPERATION, "*"))
        reserve(Word(Tag.BINARY_OPERATION, "/"))
        reserve(Word(Tag.BINARY_OPERATION, ">>"))
        reserve(Word(Tag.BINARY_OPERATION, "<<"))
        reserve(Word(Tag.BINARY_OPERATION, ">"))
        reserve(Word(Tag.BINARY_OPERATION, "<"))
        reserve(Word(Tag.BINARY_OPERATION, "="))
        reserve(Word(Tag.BINARY_OPERATION, ":="))

        reserve(Word(Tag.SEPARATOR, " "))
        reserve(Word(Tag.SEPARATOR, "\t"))
        reserve(Word(Tag.SEPARATOR, "\n"))
        reserve(Word(Tag.SEPARATOR, "\r"))
    }

    private fun reserve(word: Word) {
        words[word.lexeme] = word
    }

    fun scan(expression: String): LexemeTable {
        val table = LexemeTable()

        val set = words.filter { it.value.tag != Tag.KEY }.keys
        val delimiters = set.toTypedArray()
        val tokens = expression.splitKeeping(*delimiters)

        var i = 0
        var line = 1

        while (i != tokens.size) {
            if (tokens[i] == "\n") {
                line++
            } else if (tokens[i] == "\t" || tokens[i] == "\r" || tokens[i] == " ") {
            } else if (tokens[i] == ":" && tokens[i + 1] == "=") {
                table.add(Lexeme(words[":="]!!, line))
                i++
            } else if (tokens[i].isInt()) {
                val num = IntNum(tokens[i].toInt())
                table.add(Lexeme(num, line))
            } else if (tokens[i].isDouble()) {
                val num = DoubleNum(tokens[i].toDouble())
                table.add(Lexeme(num, line))
            } else if (checkLeftShift(tokens, i)) {
                table.add(Lexeme(words["<<"]!!, line))
                i++
            } else if (checkRightShift(tokens, i)) {
                table.add(Lexeme(words[">>"]!!, line))
                i++
            } else if (words.containsKey(tokens[i])) {
                val token = words.getOrDefault(tokens[i], Word(Tag.ID, tokens[i]))
                table.add(Lexeme(token, line))
            } else if (tokens[i].isVariable()) {
                table.add(Lexeme(Word(Tag.ID, tokens[i]), line))
            } else {
                table.add(Lexeme(Error(tokens[i], "Недопустимое имя переменной"), line))
            }
            i++
        }

        return table
    }

    private fun String.isInt(): Boolean = intRegex.containsMatchIn(this)

    private fun String.isDouble(): Boolean = doubleRegex.containsMatchIn(this)

    private fun String.isVariable(): Boolean = variableRegex.containsMatchIn(this)

    private fun String.splitKeeping(str: String): List<String> {
        return this.split(str).flatMap { listOf(it, str) }.dropLast(1).filterNot { it.isEmpty() }
    }

    private fun String.splitKeeping(vararg strs: String): List<String> {
        var res = listOf(this)
        strs.forEach { str -> res = res.flatMap { it.splitKeeping(str) } }
        return res
    }

    private fun checkLeftShift(tokens: List<String>, index: Int): Boolean {
        if (index == tokens.size - 1) {
            return false
        } else if (tokens[index + 1] == "<" && tokens[index + 1] == "<") {
            return true
        }
        return false
    }

    private fun checkRightShift(tokens: List<String>, index: Int): Boolean {
        if (index == tokens.size - 1) {
            return false
        } else if (tokens[index + 1] == ">" && tokens[index + 1] == ">") {
            return true
        }
        return false
    }
}
